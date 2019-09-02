package duke.command;

import duke.util.Buffer;
import duke.util.Storage;
import duke.util.TaskList;

/**
 * Represents a command to list all tasks in the task list.
 */
public class ListCommand extends Command {
    /**
     * Lists all tasks in the task list.
     *
     * @param   storage     A {@link Storage} object to cache task list
     * @param   buffer      A {@link Buffer} object to buffer Duke's output
     * @param   taskList    A {@link TaskList} object which stores the task list
     */
    public void execute(Storage storage, Buffer buffer, TaskList taskList) {
        if (taskList.getSize() == 0) {
            buffer.formatLine("You have no tasks right now.");
        } else {
            buffer.formatLine("Here are the tasks in your list:");
            for (int i = 1; i <= taskList.getSize(); i++) {
                buffer.formatLine(i + "." + taskList.getTask(i));
            }
        }
    }
}
