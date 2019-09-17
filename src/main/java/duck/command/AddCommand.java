package duck.command;

import duck.exception.DuckException;
import duck.task.Task;
import duck.util.Buffer;
import duck.util.StorageHandler;
import duck.util.ConfigLoader;
import duck.util.TaskList;

/**
 * Represents a command to add a task to the task list.
 */
public class AddCommand extends Command {
    private static final String ADD_COMMAND_SUCCESS_1 = "Got it. I've added this task:";
    private static final String ADD_COMMAND_SUCCESS_2 = "  %1$s";
    private static final String ADD_COMMAND_SUCCESS_3 = "Now you have %1$d task%2$s in the list.";
    private static final String STORAGE_UPDATE_SAVE_FAILED = "Sorry! I was unable to save this update "
            + "in storage. I'll try again next time.";

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
     * Returns the task to add to the task list.
     *
     * @return  Task object to add to task list
     */
    public Task getTaskToAdd() {
        return toAdd;
    }

    /**
     * Adds the task to the task list.
     *
     * @param cacheHandler     A {@link StorageHandler} object to cache task list
     * @param archiveHandler   A {@link StorageHandler} object to archive tasks
     * @param buffer           A {@link Buffer} object to buffer Duck's output
     * @param taskList         A {@link TaskList} object which stores the task list
     * @param configLoader     A {@link ConfigLoader} object to write changes to configuration
     */
    public void execute(StorageHandler cacheHandler, StorageHandler archiveHandler, Buffer buffer, TaskList taskList,
                        ConfigLoader configLoader) {
        taskList.addNewTask(toAdd);

        buffer.formatLine(ADD_COMMAND_SUCCESS_1);
        buffer.formatLine(String.format(ADD_COMMAND_SUCCESS_2, toAdd.toString()));
        buffer.formatLine(String.format(ADD_COMMAND_SUCCESS_3,
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
