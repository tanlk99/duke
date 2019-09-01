package duke.application;

import duke.util.Parser;
import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;
import duke.command.Command;
import duke.exception.DukeException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Duke is an interactive task scheduler. Users can interact with Duke through its command-line interface.
 * Duke can add tasks, delete tasks and mark tasks as complete. Duke caches its task list at default location
 * "[project root]/data/duke-cache.txt".
 */
public class Duke extends Application {
    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;

    private Image user = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image duke = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    private Storage storage;
    private Parser parser;
    private Ui ui;
    private TaskList taskList;

    /**
     * Creates a new Duke instance and initializes Duke's utility classes.
     * Uses the default filepath "data/duke-cache.txt".
     */
    public Duke() {
        this("data/duke-cache.txt");
    }

    /**
     * Creates a new Duke instance and initializes Duke's utility classes.
     *
     * @param   filePath    Location of the cache file
     */
    public Duke(String filePath) {
        ui = new Ui();
        parser = new Parser();

        try {
            storage = new Storage(filePath);
            storage.createCacheIfNotExists();
            taskList = new TaskList(storage.readCache());
        } catch (DukeException e) {
            ui.showLoadingError();
            taskList = new TaskList();
        }
    }

    /**
     * Initializes the command-line interface.
     */
    private void greetUser() {
        ui.showGreetings();
        String greetings = ui.getOutput();
        Label dukeText = new Label(greetings);

        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(dukeText, new ImageView(duke))
        );
    }

    /**
     * Returns Duke's response to a single line of user input.
     *
     * @param rawInput input from the user
     */
    private String getResponse(String rawInput) {
        try {
            if (rawInput.length() == 0) {
                return "";
            }

            Command parsedCommand = parser.parseInput(rawInput);
            parsedCommand.execute(storage, ui, taskList);

            if (parsedCommand.terminate()) {
                Platform.exit();
            }
        } catch (DukeException e) {
            ui.formatLine("Sorry! " + e);
        }

        return ui.getOutput();
    }

    @Override
    public void start(Stage stage) {
        Platform.setImplicitExit(true);

        //Step 1. Setting up required components

        //The container for the content of the chat to scroll.
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);

        stage.setScene(scene);
        stage.show();

        //Step 2. Formatting the window to look as expected
        stage.setTitle("Duke");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);
        //Scroll down to the end every time dialogContainer's height changes.
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));

        userInput.setPrefWidth(325.0);

        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);

        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        //Step 3. Add functionality to handle user input.
        sendButton.setOnMouseClicked((event) -> {
            handleUserInput();
        });

        userInput.setOnAction((event) -> {
            handleUserInput();
        });

        //Step 4: Display Duke's greetings
        greetUser();
    }

    /**
     * Iteration 2:
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    private void handleUserInput() {
        Label userText = new Label(userInput.getText());
        Label dukeText = new Label(getResponse(userInput.getText()));
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, new ImageView(user)),
                DialogBox.getDukeDialog(dukeText, new ImageView(duke))
        );
        userInput.clear();
    }
}
