package duck.command;

import java.util.ArrayList;
import duck.util.Storage;
import duck.util.TaskList;
import duck.util.Buffer;

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
     * @param   storage     A {@link Storage} object to cache task list
     * @param   buffer      A {@link Buffer} object to buffer Duck's output
     * @param   taskList    A {@link TaskList} object which stores the task list
     */
    public void execute(Storage storage, Buffer buffer, TaskList taskList) {
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
