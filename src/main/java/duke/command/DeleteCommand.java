package duke.command;

import duke.util.Buffer;
import duke.util.Storage;
import duke.util.TaskList;
import duke.task.Task;
import duke.exception.DukeException;

/**
 * Represents a command to delete a task in the task list.
 */
public class DeleteCommand extends Command {
    private static final String DELETE_COMMAND_INVALID_INDEX = "That is not a valid task number.";
    private static final String DELETE_COMMAND_SUCCESS_1 = "Noted. I've removed this task.";
    private static final String DELETE_COMMAND_SUCCESS_2 = "  %1$s";
    private static final String DELETE_COMMAND_SUCCESS_3 = "Now you have %1$d task%2$s in the list.";
    private static final String STORAGE_UPDATE_SAVE_FAILED = "Sorry! I was unable to save this update "
            + "in storage. I'll try again next time.";

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
     * Deletes the task given by <i>index</i>.
     *
     * @param   storage     A {@link Storage} object to cache task list
     * @param   buffer      A {@link Buffer} object to buffer Duke's output
     * @param   taskList    A {@link TaskList} object which stores the task list
     * @throws  DukeException   If index is invalid
     */
    public void execute(Storage storage, Buffer buffer, TaskList taskList) throws DukeException {
        if (index <= 0 || index > taskList.getSize()) {
            throw new DukeException(DELETE_COMMAND_INVALID_INDEX);
        }

        buffer.formatLine(DELETE_COMMAND_SUCCESS_1);

        Task toRemove = taskList.getTask(index);
        taskList.deleteTask(index);

        buffer.formatLine(String.format(DELETE_COMMAND_SUCCESS_2, toRemove.toString()));
        buffer.formatLine(String.format(DELETE_COMMAND_SUCCESS_3,
                taskList.getSize(),
                taskList.getSize() == 1 ? "" : "s"));

        try {
            storage.writeCache(taskList);
        } catch (DukeException e) {
            buffer.formatLine(""); //Insert empty line for readability
            buffer.formatLine(STORAGE_UPDATE_SAVE_FAILED);
        }
    }
}
