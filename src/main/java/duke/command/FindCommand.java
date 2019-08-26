package duke.command;

import java.util.ArrayList;
import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;

public class FindCommand extends Command {
    private String filter;

    /**
     * Creates a new FindCommand.
     *
     * @param filter    the string to search for
     */
    public FindCommand(String filter) {
        this.filter = filter;
    }

    public boolean terminate() {
        return false;
    }

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
