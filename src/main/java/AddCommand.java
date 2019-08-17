import java.util.ArrayList;

class AddCommand extends Command {
    String toAdd;

    public AddCommand(String toAdd) {
        this.toAdd = toAdd;
    }

    public boolean terminate() {
        return false;
    }

    public void execute(ArrayList<Task> taskList) {
        Formatter.printHorizontalLine();
        Formatter.formatLine("added: " + toAdd);
        Formatter.printHorizontalLine();

        taskList.add(new Task(toAdd));
    }
}
