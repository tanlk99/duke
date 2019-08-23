import java.util.ArrayList;

class DeleteCommand extends Command {
    private int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    public boolean terminate() {
        return false;
    }

    public void execute(Storage storage, Ui ui, TaskList taskList) throws DukeException {
        if (index <= 0 || index > taskList.getSize()) {
            throw new DukeException("That is not a valid task number.");
        }

        Task toRemove = taskList.getTask(index);
        taskList.deleteTask(index);

        ui.printHorizontalLine();
        ui.formatLine("Noted. I've removed this task.");
        ui.formatLine("  " + toRemove);
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
