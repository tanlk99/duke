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
     * @param   index   Index of the task to delete
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Returns the index of the task to delete.
     *
     * @return  Index of task to delete
     */
    public int getIndex() {
        return index;
    }

    /**
     * Indicates whether Duke needs to exit after this command.
     * DeleteCommand instances will always return false.
     *
     * @return True if Duke needs to terminate
     */
    public boolean terminate() {
        return false;
    }

    /**
     * Deletes the task given by <i>index</i>.
     *
     * @param   storage     A {@link Storage} object to cache task list
     * @param   ui          A {@link Ui} object to display Duke's output
     * @param   taskList    A {@link TaskList} object which stores the task list
     * @throws  DukeException   If index is invalid
     */
    public void execute(Storage storage, Ui ui, TaskList taskList) throws DukeException {
        if (index <= 0 || index > taskList.getSize()) {
            throw new DukeException("That is not a valid task number.");
        }

        ui.printHorizontalLine();
        ui.formatLine("Noted. I've removed this task.");

        Task toRemove = taskList.getTask(index);
        taskList.deleteTask(index);

        ui.formatLine("  " + toRemove);
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
