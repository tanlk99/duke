package duke.command;

import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;
import duke.exception.DukeException;

/**
 * Represents a command given by the user. Can have a variety of functions,
 * including exiting, adding tasks and deleting tasks.
 */
public abstract class Command {
    /**
     * Indicates whether Duke needs to exit after this command.
     *
     * @return true if Duke needs to terminate
     */
    public abstract boolean terminate();

    /**
     * Executes the command.
     *
     * @param   storage     a Storage object to cache the task list
     * @param   ui          a Ui object to display Duke's output
     * @param   taskList    a TaskList object which stores the task list
     */
    public abstract void execute(Storage storage, Ui ui, TaskList taskList) throws DukeException;
}
