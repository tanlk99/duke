package duke.application;

import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;
import duke.util.parser.Parser;
import duke.command.Command;
import duke.exception.DukeException;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

/**
 * Duke is an interactive task scheduler. Users can interact with Duke through its command-line interface.
 * Duke can add tasks, delete tasks and mark tasks as complete. Duke caches its task list at default location
 * "[project root]/data/duke-cache.txt".
 */
public class Duke {
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
    Duke() {
        this("data/duke-cache.txt");
    }

    /**
     * Creates a new Duke instance and initializes Duke's utility classes.
     *
     * @param   filePath    Location of the cache file
     */
    Duke(String filePath) {
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
     * Returns Duke's greeting message.
     *
     * @return Duke's greeting message
     */
    String getGreetings() {
        ui.showGreetings();
        return ui.getOutput();
    }

    /**
     * Returns Duke's response to a single line of user input, assumed to be non-empty.
     *
     * @param rawInput input from the user
     */
    String getResponse(String rawInput) {
        try {
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
}
