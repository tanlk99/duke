package duke.command;

import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;
import duke.task.Task;
import duke.exception.DukeException;

/**
 * Represents a command to delete a task in the task list.
 */
public class DeleteCommand extends Command {
    private int index;

    /**
     * Creates a new DeleteCommand.
     *
     * @param   index   the index of the task to delete
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Indicates whether Duke needs to exit after this command.
     * DeleteCommand instances will always return false.
     *
     * @return true if Duke needs to terminate
     */
    public boolean terminate() {
        return false;
    }

    /**
     * Deletes the task given by <i>index</i>.
     *
     * @param   storage     a Storage object to cache task list
     * @param   ui          a Ui object to display Duke's output
     * @param   taskList    a TaskList object which stores the task list
     */
    public void execute(Storage storage, Ui ui, TaskList taskList) throws DukeException {
        if (index <= 0 || index > taskList.getSize()) {
            throw new DukeException("That is not a valid task number.");
        }

        Task toRemove = taskList.getTask(index);
        taskList.deleteTask(index);

        ui.printHorizontalLine();
        ui.formatLine("Noted. I've removed this task.");
        ui.formatLine("  " + toRemove);
        ui.formatLine("Now you have " + taskList.getSize() + " task" +
            (taskList.getSize() == 1 ? "" : "s") + " in the list.");

        try {
            storage.writeCache(taskList);
        } catch (DukeException e) {
            ui.formatLine("Sorry! I was unable to save this update in storage. I'll try again next time.");
        }

        ui.printHorizontalLine();
    }
}
