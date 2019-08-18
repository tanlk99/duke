import java.util.ArrayList;

class AddCommand extends Command {
    Task toAdd;

    public AddCommand(Task toAdd) {
        this.toAdd = toAdd;
    }

    public boolean terminate() {
        return false;
    }

    public void execute(ArrayList<Task> taskList) {
        taskList.add(toAdd);

        Formatter.printHorizontalLine();
        Formatter.formatLine("Got it. I've added this task:");
        Formatter.formatLine("  " + toAdd);
        Formatter.formatLine("Now you have " + taskList.size() + " task" +
            (taskList.size() == 1 ? "" : "s") + " in the list.");
        Formatter.printHorizontalLine();
    }
}
