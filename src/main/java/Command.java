abstract class Command {
    abstract public boolean terminate();

    abstract public void execute(Storage storage) throws DukeException;
}
