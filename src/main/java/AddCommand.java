import java.util.ArrayList;

class AddCommand extends Command {
    String rawDesc;

    public AddCommand(String rawDesc) {
        this.rawDesc = rawDesc;
    }

    public boolean terminate() {
        return false;
    }

    public void execute(ArrayList<Task> taskList) {
        Task newTask = Parser.parseTask(rawDesc);
        taskList.add(newTask);

        Formatter.printHorizontalLine();
        Formatter.formatLine("Got it. I've added this task:");
        Formatter.formatLine("  " + newTask);
        Formatter.formatLine("Now you have " + taskList.size() + " tasks in the list.");
        Formatter.printHorizontalLine();
    }
}
