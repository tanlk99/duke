package duke.command;

import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;

/**
 * Represents a command to quit the system.
 */
public class ExitCommand extends Command {
    public boolean terminate() {
        return true;
    }

    public void execute(Storage storage, Ui ui, TaskList taskList) {
        ui.printHorizontalLine();
        ui.formatLine("Bye. Hope to see you again soon!");
        ui.printHorizontalLine();
    }
}
