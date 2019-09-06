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

        buffer.formatLine("Got it. I've added this task:");
        buffer.formatLine("  " + toAdd);
        buffer.formatLine("Now you have " + taskList.getSize() + " task"
                + (taskList.getSize() == 1 ? "" : "s") + " in the list.");

        try {
            storage.writeCache(taskList);
        } catch (DukeException e) {
            buffer.formatLine(""); //insert empty line for readability
            buffer.formatLine("Sorry! I was unable to save this update in storage. I'll try again next time.");
        }
    }
}
