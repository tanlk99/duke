package duke.command;

import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;

/**
 * Represents a command to quit the system.
 */
public class ExitCommand extends Command {
    /**
     * Indicates whether Duke needs to exit after this command.
     * ExitCommand instances will always return true.
     *
     * @return true if Duke needs to terminate
     */
    public boolean terminate() {
        return true;
    }

    /**
     * Prints Duke's goodbye message.
     *
     * @param   storage     a Storage object to cache task list
     * @param   ui          a Ui object to display Duke's output
     * @param   taskList    a TaskList object which stores the task list
     */
    public void execute(Storage storage, Ui ui, TaskList taskList) {
        ui.printHorizontalLine();
        ui.formatLine("Bye. Hope to see you again soon!");
        ui.printHorizontalLine();
    }
}
