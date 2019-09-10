package duke.command;

import duke.util.Buffer;
import duke.util.Storage;
import duke.util.TaskList;

/**
 * Represents a command to list all tasks in the task list.
 */
public class ListCommand extends Command {
    private static final String LIST_COMMAND_NO_TASKS = "You have no tasks right now.";
    private static final String LIST_COMMAND_SUCCESS = "Here are the tasks in your list:";
    private static final String LIST_COMMAND_LIST = "%1$d.%2$s";

    /**
     * Lists all tasks in the task list.
     *
     * @param   storage     A {@link Storage} object to cache task list
     * @param   buffer      A {@link Buffer} object to buffer Duke's output
     * @param   taskList    A {@link TaskList} object which stores the task list
     */
    public void execute(Storage storage, Buffer buffer, TaskList taskList) {
        if (taskList.getSize() == 0) {
            buffer.formatLine(LIST_COMMAND_NO_TASKS);
        } else {
            buffer.formatLine(LIST_COMMAND_SUCCESS);
            for (int i = 1; i <= taskList.getSize(); i++) {
                buffer.formatLine(String.format(LIST_COMMAND_LIST,
                        i, taskList.getTask(i).toString()));
            }
        }
    }
}
