import java.util.ArrayList;

class DeleteCommand extends Command {
    int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    public boolean terminate() {
        return false;
    }

    public void execute(ArrayList<Task> taskList) throws DukeException {
        if (index < 0 || index >= taskList.size()) {
            throw new DukeException("That is not a valid task number.");
        }

        Task toRemove = taskList.get(index);
        taskList.remove(index);

        Formatter.printHorizontalLine();
        Formatter.formatLine("Noted. I've removed this task.");
        Formatter.formatLine("  " + toRemove);
        Formatter.formatLine("Now you have " + taskList.size() + " task" +
            (taskList.size() == 1 ? "" : "s") + " in the list.");
        Formatter.printHorizontalLine();
    }
}
