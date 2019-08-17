import java.util.ArrayList;

abstract class Command {
    abstract public boolean terminate();

    abstract public void execute(ArrayList<Task> taskList) throws DukeException;
}
