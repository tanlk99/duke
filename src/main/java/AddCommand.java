import java.util.ArrayList;

class AddCommand extends Command {
    private Task toAdd;

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
