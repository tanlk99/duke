import java.util.ArrayList;

/**
 * Represents a command to list all tasks in the task list.
 */
class ListCommand extends Command {
    public boolean terminate() {
        return false;
    }

    public void execute(Storage storage, Ui ui, TaskList taskList) {
        ui.printHorizontalLine();
        if (taskList.getSize() == 0) {
            ui.formatLine("You have no tasks right now.");
        } else {
            ui.formatLine("Here are the tasks in your list:");
            for (int i = 1; i <= taskList.getSize(); i++) {
                ui.formatLine(i + "." + taskList.getTask(i));
            }
        }
        ui.printHorizontalLine();
    }
}
