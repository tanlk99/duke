/**
 * Represents a command given by the user. Can have a variety of functions,
 * including exiting, adding tasks and deleting tasks.
 */
abstract class Command {
    /**
     * Indicates whether Duke needs to exit after this command.
     *
     * @returns true if Duke needs to terminate
     */
    abstract public boolean terminate();

    /**
     * Executes the command.
     *
     * @param   storage     a Storage object to cache task list
     * @param   ui          a Ui object to display Duke's output
     * @param   taskList    a TaskList object which stores the task list
     */
    abstract public void execute(Storage storage, Ui ui, TaskList taskList) throws DukeException;
}
