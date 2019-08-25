package duke;

import java.util.Scanner;
import duke.util.Parser;
import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;
import duke.command.Command;
import duke.exception.DukeException;

public class Duke {
    private Storage storage;
    private Parser parser;
    private Ui ui;
    private TaskList taskList;

    public Duke(String filePath) {
        ui = new Ui();
        parser = new Parser();

        try {
            storage = new Storage(filePath);
            taskList = new TaskList(storage.readCache());
        } catch (DukeException e) {
            ui.showLoadingError();
            taskList = new TaskList();
        }
    }

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

    public static void main(String[] args) {
        new Duke("data/duke-cache.txt").init();
    }
}
