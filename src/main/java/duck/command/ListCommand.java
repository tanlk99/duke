package duck.command;

import duck.util.Buffer;
import duck.util.Storage;
import duck.util.TaskList;
import duck.util.ConfigLoader;

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
     * @param storage     A {@link Storage} object to cache task list
     * @param buffer      A {@link Buffer} object to buffer Duck's output
     * @param taskList    A {@link TaskList} object which stores the task list
     * @param configLoader  A {@link ConfigLoader} object to write changes to configuration
     */
    public void execute(Storage storage, Buffer buffer, TaskList taskList,
                        ConfigLoader configLoader) {
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
