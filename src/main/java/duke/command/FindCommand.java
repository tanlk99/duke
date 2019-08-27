package duke.command;

import java.util.ArrayList;
import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;

/**
 * Represents a command to search for all tasks in the task list with descriptions containing a string.
 */
public class FindCommand extends Command {
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
     * Indicates whether Duke needs to exit after this command.
     * FindCommand instances will always return false.
     *
     * @return True if Duke needs to terminate
     */
    public boolean terminate() {
        return false;
    }

    /**
     * Finds all tasks with descriptions containing the filter and lists them.
     *
     * @param   storage     A {@link Storage} object to cache task list
     * @param   ui          A {@link Ui} object to display Duke's output
     * @param   taskList    A {@link TaskList} object which stores the task list
     */
    public void execute(Storage storage, Ui ui, TaskList taskList) {
        ArrayList<Integer> matchIndices = new ArrayList<>();

        for (int index = 1; index <= taskList.getSize(); index++) {
            if (taskList.taskDescriptionContains(index, filter)) {
                matchIndices.add(index);
            }
        }

        ui.printHorizontalLine();
        if (matchIndices.size() == 0) {
            ui.formatLine("There were no tasks in the list that matched your search term.");
        } else {
            ui.formatLine("Here are the matching tasks in your list:");
            for (int index : matchIndices) {
                ui.formatLine(index + "." + taskList.getTask(index));
            }
        }

        ui.printHorizontalLine();
    }
}
