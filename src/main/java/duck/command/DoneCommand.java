package duck.command;

import duck.util.Buffer;
import duck.util.Storage;
import duck.util.TaskList;
import duck.exception.DuckException;
import duck.util.ConfigLoader;

/**
 * Represents a command to mark a task in the task list as done.
 */
public class DoneCommand extends Command {
    private static final String DONE_COMMAND_INVALID_INDEX = "That is not a valid task number.";
    private static final String DONE_COMMAND_SUCCESS_1 = "Nice! I've marked this task as done:";
    private static final String DONE_COMMAND_SUCCESS_2 = "  %1$s";
    private static final String STORAGE_UPDATE_SAVE_FAILED = "Sorry! I was unable to save this update "
            + "in storage. I'll try again next time.";

    private int index;

    /**
     * Creates a new DoneCommand.
     *
     * @param   index   Index of task to mark as done
     */
    public DoneCommand(int index) {
        this.index = index;
    }

    /**
     * Returns the index of the task to mark as done.
     *
     * @return  Index of task to mark as done
     */
    public int getIndex() {
        return index;
    }

    /**
     * Marks the task given by <i>index</i> as done.
     *
     * @param storage     A {@link Storage} object to cache task list
     * @param buffer      A {@link Buffer} object to buffer Duck's output
     * @param taskList    A {@link TaskList} object which stores the task list
     * @param configLoader  A {@link ConfigLoader} object to write changes to configuration
     * @throws  DuckException   If index is invalid
     */
    public void execute(Storage storage, Buffer buffer, TaskList taskList, ConfigLoader configLoader) throws DuckException {
        if (index <= 0 || index > taskList.getSize()) {
            throw new DuckException(DONE_COMMAND_INVALID_INDEX);
        }

        taskList.markTaskAsDone(index);

        buffer.formatLine(DONE_COMMAND_SUCCESS_1);
        buffer.formatLine(String.format(DONE_COMMAND_SUCCESS_2, taskList.getTask(index)));

        try {
            storage.writeCache(taskList);
        } catch (DuckException e) {
            buffer.formatLine(""); //Insert empty line for readability
            buffer.formatLine(STORAGE_UPDATE_SAVE_FAILED);
        }
    }
}
