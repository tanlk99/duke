package duck.command;

import duck.exception.DuckException;
import duck.task.Task;
import duck.util.Buffer;
import duck.util.StorageHandler;
import duck.util.ConfigLoader;
import duck.util.TaskList;

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
     * @param cacheHandler     A {@link StorageHandler} object to cache task list
     * @param archiveHandler   A {@link StorageHandler} object to archive tasks
     * @param buffer           A {@link Buffer} object to buffer Duck's output
     * @param taskList         A {@link TaskList} object which stores the task list
     * @param configLoader     A {@link ConfigLoader} object to write changes to configuration
     */
    public void execute(StorageHandler cacheHandler, StorageHandler archiveHandler, Buffer buffer, TaskList taskList,
                        ConfigLoader configLoader) throws DuckException {
        if (index <= 0 || index > taskList.getSize()) {
            throw new DuckException(DELETE_COMMAND_INVALID_INDEX);
        }

        buffer.formatLine(DELETE_COMMAND_SUCCESS_1);

        Task toRemove = taskList.getTask(index);
        taskList.deleteTask(index);

        buffer.formatLine(String.format(DELETE_COMMAND_SUCCESS_2, toRemove.toString()));
        buffer.formatLine(String.format(DELETE_COMMAND_SUCCESS_3,
                taskList.getSize(),
                taskList.getSize() == 1 ? "" : "s"));

        try {
            cacheHandler.writeCache(taskList);
        } catch (DuckException e) {
            buffer.formatLine(""); //Insert empty line for readability
            buffer.formatLine(STORAGE_UPDATE_SAVE_FAILED);
        }
    }
}
