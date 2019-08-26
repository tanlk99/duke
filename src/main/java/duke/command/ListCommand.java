package duke.command;

import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;

/**
 * Represents a command to list all tasks in the task list.
 */
public class ListCommand extends Command {
    /**
     * Indicates whether Duke needs to exit after this command.
     * ListCommand instances will always return false.
     *
     * @return true if Duke needs to terminate
     */
    public boolean terminate() {
        return false;
    }

    /**
     * Lists all tasks in the task list.
     *
     * @param   storage     a Storage object to cache task list
     * @param   ui          a Ui object to display Duke's output
     * @param   taskList    a TaskList object which stores the task list
     */
    public void execute(Storage storage, Ui ui, TaskList taskList) {
        ui.printHorizontalLine();
        if (taskList.getSize() == 0) {
            ui.formatLine("You have no tasks right now.");
        } else {
            ui.formatLine("Here are the tasks in your list:");
            for (int i = 1; i <= taskList.getSize(); i++) {
                ui.formatLine(i + "." + taskList.getTask(i));
            }
        }
        ui.printHorizontalLine();
    }
}
