import java.util.ArrayList;

class DoneCommand extends Command {
    int index;

    public DoneCommand(int index) {
        this.index = index;
    }

    public boolean terminate() {
        return false;
    }

    public void execute(Storage storage) throws DukeException {
        ArrayList<Task> taskList = storage.getTaskList();

        if (index < 0 || index >= taskList.size()) {
            throw new DukeException("That is not a valid task number.");
        }

        taskList.get(index).markAsDone();

        Formatter.printHorizontalLine();
        Formatter.formatLine("Nice! I've marked this task as done:");
        Formatter.formatLine("  " + taskList.get(index));
        Formatter.printHorizontalLine();

        storage.writeCache();
    }
}
