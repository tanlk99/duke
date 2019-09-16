package duck.application;

import duck.util.Buffer;
import duck.util.Storage;
import duck.util.TaskList;
import duck.util.ConfigLoader;
import duck.util.parser.InputParser;
import duck.command.Command;
import duck.exception.DuckException;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

/**
 * Duck is an interactive task scheduler. Users can interact with Duck through its command-line interface.
 * Duck can add tasks, delete tasks and mark tasks as complete. Duck caches its task list at default location
 * "[project root]/data/duck-cache.txt".
 */
public class Duck {
    private static final String DEFAULT_CONFIG_PATH = "duck-config.txt";

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;

    private Image user = new Image(getClass().getResourceAsStream("/images/DaUser.png"));
    private Image duck = new Image(getClass().getResourceAsStream("/images/DaDuck.png"));

    private ConfigLoader configLoader;
    private Storage storage;
    private InputParser parser;
    private Buffer buffer;
    private TaskList taskList;

    /**
     * Creates a new Duck instance and initializes Duck's utility classes.
     * Uses the default filepath "data/duck-cache.txt".
     */
    Duck() {
        this("data/duck-cache.txt", "archive/duck-archive.txt");
    }

    /**
     * Creates a new Duck instance and initializes Duck's utility classes.
     *
     * @param   cachePath    Location of the cache file
     */
    private Duck(String cachePath, String archivePath) {
        buffer = new Buffer();
        parser = new InputParser();
        configLoader = new ConfigLoader(DEFAULT_CONFIG_PATH);

        try {
            configLoader.loadConfig();
            storage = new Storage(configLoader.getConfig("cachePath"),
                    configLoader.getConfig("archivePath"));

            storage.initializeCacheIfNotExists();
            taskList = new TaskList(storage.readCache());
        } catch (DuckException e) {
            buffer.addLoadingError();
            taskList = new TaskList();
        }
    }

    /**
     * Returns Duck's greeting message.
     *
     * @return Duck's greeting message
     */
    String getGreetings() {
        buffer.addGreetings();
        return buffer.getOutput();
    }

    /**
     * Returns Duck's response to a single line of user input, assumed to be non-empty.
     *
     * @param rawInput Input from the user
     */
    String getResponse(String rawInput) {
        try {
            Command parsedCommand = parser.parseInput(rawInput);
            parsedCommand.execute(storage, buffer, taskList, configLoader);

            if (parsedCommand.shouldTerminate()) {
                Platform.exit();
            }
        } catch (DuckException e) {
            buffer.formatLine("Sorry! " + e);
        }

        return buffer.getOutput();
    }
}
