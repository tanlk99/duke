package duke.command;

import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;
import duke.exception.DukeException;

/**
 * Represents a command to mark a task in the task list as done.
 */
public class DoneCommand extends Command {
    private int index;

    /**
     * Creates a new DoneCommand.
     *
     * @param   index   index of task to mark as done
     */
    public DoneCommand(int index) {
        this.index = index;
    }

    /**
     * Indicates whether Duke needs to exit after this command.
     * DoneCommand instances will always return false.
     *
     * @return true if Duke needs to terminate
     */
    public boolean terminate() {
        return false;
    }

    /**
     * Marks the task given by <i>index</i> as done.
     *
     * @param   storage     a Storage object to cache task list
     * @param   ui          a Ui object to display Duke's output
     * @param   taskList    a TaskList object which stores the task list
     */
    public void execute(Storage storage, Ui ui, TaskList taskList) throws DukeException {
        if (index <= 0 || index > taskList.getSize()) {
            throw new DukeException("That is not a valid task number.");
        }

        taskList.getTask(index).markAsDone();

        ui.printHorizontalLine();
        ui.formatLine("Nice! I've marked this task as done:");
        ui.formatLine("  " + taskList.getTask(index));

        try {
            storage.writeCache(taskList);
        } catch (DukeException e) {
            ui.formatLine("Sorry! I was unable to save this update in storage. I'll try again next time.");
        }

        ui.printHorizontalLine();
    }
}
