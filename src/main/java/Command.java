abstract class Command {
    abstract public boolean terminate();

    abstract public void execute(Storage storage, Ui ui, TaskList taskList) throws DukeException;
}
