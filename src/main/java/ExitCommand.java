import java.util.ArrayList;

class ExitCommand extends Command {
    public boolean terminate() {
        return true;
    }

    public void execute(ArrayList<Task> taskList) {
        Formatter.printHorizontalLine();
        Formatter.formatLine("Bye. Hope to see you again soon!");
        Formatter.printHorizontalLine();
    }
}
