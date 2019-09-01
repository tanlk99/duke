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
     * @param   index   Index of task to mark as done
     */
    public DoneCommand(int index) {
        this.index = index;
    }

    /**
     * Returns the index of the task to mark as done.
     *
     * @return  Index of task to mark as done
     */
    public int getIndex() {
        return index;
    }

    /**
     * Indicates whether Duke needs to exit after this command.
     * DoneCommand instances will always return false.
     *
     * @return True if Duke needs to terminate
     */
    public boolean terminate() {
        return false;
    }

    /**
     * Marks the task given by <i>index</i> as done.
     *
     * @param   storage     A {@link Storage} object to cache task list
     * @param   ui          A {@link Ui} object to display Duke's output
     * @param   taskList    A {@link TaskList} object which stores the task list
     * @throws  DukeException   If index is invalid
     */
    public void execute(Storage storage, Ui ui, TaskList taskList) throws DukeException {
        if (index <= 0 || index > taskList.getSize()) {
            throw new DukeException("That is not a valid task number.");
        }

        taskList.markTaskAsDone(index);

        ui.formatLine("Nice! I've marked this task as done:");
        ui.formatLine("  " + taskList.getTask(index));

        try {
            storage.writeCache(taskList);
        } catch (DukeException e) {
            ui.formatLine("Sorry! I was unable to save this update in storage. I'll try again next time.");
        }
    }
}
