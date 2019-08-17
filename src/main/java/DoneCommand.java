import java.util.ArrayList;
import java.lang.ArrayIndexOutOfBoundsException;

class DoneCommand extends Command {
    int index;

    public DoneCommand(int index) {
        this.index = index;
    }

    public boolean terminate() {
        return false;
    }

    public void execute(ArrayList<Task> taskList) throws DukeException {
        if (index < 0 || index >= taskList.size()) {
            throw new DukeException("That is not a valid task number.");
        }

        taskList.get(index).markAsDone();

        Formatter.printHorizontalLine();
        Formatter.formatLine("Nice! I've marked this task as done:");
        Formatter.formatLine("  " + taskList.get(index));
        Formatter.printHorizontalLine();
    }
}
