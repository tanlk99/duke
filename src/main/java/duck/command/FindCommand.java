package duck.command;

import java.util.ArrayList;
import duck.util.Buffer;
import duck.util.StorageHandler;
import duck.util.ConfigLoader;
import duck.util.TaskList;

/**
 * Represents a command to search for all tasks in the task list with descriptions containing a string.
 */
public class FindCommand extends Command {
    private static final String FIND_COMMAND_NO_MATCH = "There were no tasks in the list "
            + "that matched your search term.";
    private static final String FIND_COMMAND_SUCCESS = "Here are the matching tasks in your list:";
    private static final String FIND_COMMAND_LIST = "%1$d.%2$s";

    private String filter;

    /**
     * Creates a new FindCommand.
     *
     * @param filter    String to search for
     */
    public FindCommand(String filter) {
        this.filter = filter;
    }

    /**
     * Returns the string to search for.
     *
     * @return  String to search for
     */
    public String getFilter() {
        return filter;
    }

    /**
     * Finds all tasks with descriptions containing the filter and lists them.
     *
     * @param cacheHandler     A {@link StorageHandler} object to cache task list
     * @param archiveHandler   A {@link StorageHandler} object to archive tasks
     * @param buffer           A {@link Buffer} object to buffer Duck's output
     * @param taskList         A {@link TaskList} object which stores the task list
     * @param configLoader     A {@link ConfigLoader} object to write changes to configuration
     */
    public void execute(StorageHandler cacheHandler, StorageHandler archiveHandler, Buffer buffer, TaskList taskList,
                        ConfigLoader configLoader) {
        ArrayList<Integer> matchIndices = new ArrayList<>();

        for (int index = 1; index <= taskList.getSize(); index++) {
            if (taskList.taskDescriptionContains(index, filter)) {
                matchIndices.add(index);
            }
        }

        if (matchIndices.size() == 0) {
            buffer.formatLine(FIND_COMMAND_NO_MATCH);
        } else {
            buffer.formatLine(FIND_COMMAND_SUCCESS);
            for (int index : matchIndices) {
                buffer.formatLine(String.format(FIND_COMMAND_LIST,
                        index, taskList.getTask(index).toString()));
            }
        }
    }
}
