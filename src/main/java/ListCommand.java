import java.util.ArrayList;

class ListCommand extends Command {
    public boolean terminate() {
        return false;
    }

    public void execute(Storage storage) {
        ArrayList<Task> taskList = storage.getTaskList();

        Formatter.printHorizontalLine();
        if (taskList.size() == 0) {
            Formatter.formatLine("You have no tasks right now.");
        } else {
            Formatter.formatLine("Here are the tasks in your list:");
            for (int i = 0; i < taskList.size(); i++) {
                Formatter.formatLine((i + 1) + "." + taskList.get(i));
            }
        }
        Formatter.printHorizontalLine();
    }
}
