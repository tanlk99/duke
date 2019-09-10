package duke.command;

import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Buffer;
import duke.task.Task;
import duke.exception.DukeException;

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
     * @param   storage     A {@link Storage} object to cache task list
     * @param   buffer      A {@link Buffer} object to buffer Duke's output
     * @param   taskList    A {@link TaskList} object which stores the task list
     */
    public void execute(Storage storage, Buffer buffer, TaskList taskList) {
        taskList.addNewTask(toAdd);

        buffer.formatLine(ADD_COMMAND_SUCCESS_1);
        buffer.formatLine(String.format(ADD_COMMAND_SUCCESS_2, toAdd.toString()));
        buffer.formatLine(String.format(ADD_COMMAND_SUCCESS_3,
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
