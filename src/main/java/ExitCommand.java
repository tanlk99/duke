import java.util.ArrayList;

class ExitCommand extends Command {
    public boolean terminate() {
        return true;
    }

    public void execute(Storage storage, Ui ui, TaskList taskList) {
        ui.printHorizontalLine();
        ui.formatLine("Bye. Hope to see you again soon!");
        ui.printHorizontalLine();
    }
}
