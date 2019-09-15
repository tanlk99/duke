package duck.application;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends Stage {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;

    private Duck duck;

    private Image userImage = new Image(getClass().getResourceAsStream("/images/DaUser.png"));
    private Image duckImage = new Image(getClass().getResourceAsStream("/images/DaDuck.png"));

    /**
     * Initializes the main window.
     */
    @FXML
    private void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Sets the Duck to bind the MainWindow to.
     * @param d Duck object to bind
     */
    void setDuck(Duck d) {
        duck = d;
    }

    /**
     * Displays Duck's greeting message.
     * Should only be called after {@link #setDuck}.
     */
    void displayGreetings() {
        String greetings = duck.getGreetings();
        dialogContainer.getChildren().addAll(
                DialogBox.getDuckDialog(greetings, duckImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duck's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input.length() == 0) {
            return; //ignore empty input
        }

        String response = duck.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDuckDialog(response, duckImage)
        );
        userInput.clear();
    }
}