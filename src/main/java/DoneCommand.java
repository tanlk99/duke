import java.util.ArrayList;

class DoneCommand extends Command {
    int index;

    public DoneCommand(int index) {
        this.index = index;
    }

    public boolean terminate() {
        return false;
    }

    public void execute(ArrayList<Task> taskList) {
        taskList.get(index).markAsDone();

        Formatter.printHorizontalLine();
        Formatter.formatLine("Nice! I've marked this task as done:");
        Formatter.formatLine("  " + taskList.get(index));
        Formatter.printHorizontalLine();
    }
}
