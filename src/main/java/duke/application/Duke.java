package duke.application;

import duke.util.Buffer;
import duke.util.Storage;
import duke.util.TaskList;
import duke.util.parser.InputParser;
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

    private Image user = new Image(getClass().getResourceAsStream("/images/DaUser.png"));
    private Image duke = new Image(getClass().getResourceAsStream("/images/DaDuke.png"));

    private Storage storage;
    private InputParser parser;
    private Buffer buffer;
    private TaskList taskList;

    /**
     * Creates a new Duke instance and initializes Duke's utility classes.
     * Uses the default filepath "data/duke-cache.txt".
     */
    Duke() {
        this("data/duke-cache.txt", "archive/duke-archive.txt");
    }

    /**
     * Creates a new Duke instance and initializes Duke's utility classes.
     *
     * @param   cachePath    Location of the cache file
     */
    private Duke(String cachePath, String archivePath) {
        buffer = new Buffer();
        parser = new InputParser();

        try {
            storage = new Storage(cachePath, archivePath);
            storage.initializeCacheIfNotExists();
            taskList = new TaskList(storage.readCache());
        } catch (DukeException e) {
            buffer.addLoadingError();
            taskList = new TaskList();
        }
    }

    /**
     * Returns Duke's greeting message.
     *
     * @return Duke's greeting message
     */
    String getGreetings() {
        buffer.addGreetings();
        return buffer.getOutput();
    }

    /**
     * Returns Duke's response to a single line of user input, assumed to be non-empty.
     *
     * @param rawInput Input from the user
     */
    String getResponse(String rawInput) {
        try {
            Command parsedCommand = parser.parseInput(rawInput);
            parsedCommand.execute(storage, buffer, taskList);

            if (parsedCommand.shouldTerminate()) {
                Platform.exit();
            }
        } catch (DukeException e) {
            buffer.formatLine("Sorry! " + e);
        }

        return buffer.getOutput();
    }
}
