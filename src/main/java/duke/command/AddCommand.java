package duke.command;

import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;
import duke.task.Task;
import duke.exception.DukeException;

/**
 * Represents a command to add a task to the task list.
 */
public class AddCommand extends Command {
    private Task toAdd;

    /**
     * Creates a new AddCommand with a task to add.
     *
     * @param   toAdd   Task object to add to task list
     */
    public AddCommand(Task toAdd) {
        this.toAdd = toAdd;
    }

    /**
     * Indicates whether Duke needs to exit after this command.
     * AddCommand instances will always return false.
     *
     * @return True if Duke needs to terminate
     */
    public boolean terminate() {
        return false;
    }

    /**
     * Adds the task to the task list.
     *
     * @param   storage     A {@link Storage} object to cache task list
     * @param   ui          A {@link Ui} object to display Duke's output
     * @param   taskList    A {@link TaskList} object which stores the task list
     */
    public void execute(Storage storage, Ui ui, TaskList taskList) {
        taskList.addNewTask(toAdd);

        ui.printHorizontalLine();
        ui.formatLine("Got it. I've added this task:");
        ui.formatLine("  " + toAdd);
        ui.formatLine("Now you have " + taskList.getSize() + " task"
                + (taskList.getSize() == 1 ? "" : "s") + " in the list.");

        try {
            storage.writeCache(taskList);
        } catch (DukeException e) {
            ui.formatLine("Sorry! I was unable to save this update in storage. I'll try again next time.");
        }

        ui.printHorizontalLine();
    }
}
