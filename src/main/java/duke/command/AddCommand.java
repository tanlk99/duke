package duke.command;

import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;
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
     * @param   toAdd   the Task object to add to task list
     */
    public AddCommand(Task toAdd) {
        this.toAdd = toAdd;
    }

    public boolean terminate() {
        return false;
    }

    public void execute(Storage storage, Ui ui, TaskList taskList) {
        taskList.addNewTask(toAdd);

        ui.printHorizontalLine();
        ui.formatLine("Got it. I've added this task:");
        ui.formatLine("  " + toAdd);
        ui.formatLine("Now you have " + taskList.getSize() + " task" +
            (taskList.getSize() == 1 ? "" : "s") + " in the list.");

        try {
            storage.writeCache(taskList);
        } catch (DukeException e) {
            ui.formatLine("Sorry! I was unable to save this update in storage. I'll try again next time.");
        }

        ui.printHorizontalLine();
    }
}