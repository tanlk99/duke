import java.util.ArrayList;

/**
 * Represents a command to mark a task in the task list as done.
 */
class DoneCommand extends Command {
    private int index;

    /**
     * Creates a new DoneCommand.
     *
     * @param   index   index of task to mark as done
     */
    public DoneCommand(int index) {
        this.index = index;
    }

    public boolean terminate() {
        return false;
    }

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
