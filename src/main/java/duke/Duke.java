package duke;

import java.util.Scanner;
import duke.util.Parser;
import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;
import duke.command.Command;
import duke.exception.DukeException;

/**
 * Duke is an interactive task scheduler. Users can interact with Duke through its command-line interface.
 * Duke can add tasks, delete tasks and mark tasks as complete. Duke caches its task list at default location
 * "[project root]/data/duke-cache.txt".
 */
public class Duke {
    private Storage storage;
    private Parser parser;
    private Ui ui;
    private TaskList taskList;

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
    private void init() {
        ui.showGreetings();

        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            try {
                String rawInput = sc.nextLine();
                Command parsedCommand = parser.parseInput(rawInput);
                parsedCommand.execute(storage, ui, taskList);

                if (parsedCommand.terminate()) {
                    return;
                }
            } catch (DukeException e) {
                ui.printHorizontalLine();
                ui.formatLine("Sorry! " + e);
                ui.printHorizontalLine();
            }
        }
    }

    /**
     * Starts up a new Duke instance.
     */
    public static void main(String[] args) {
        new Duke("data/duke-cache.txt").init();
    }
}
