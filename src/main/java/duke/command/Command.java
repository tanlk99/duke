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
     * @return True if Duke needs to terminate
     */
    public abstract boolean terminate();

    /**
     * Executes the command.
     *
     * @param   storage     A {@link Storage} object to cache task list
     * @param   ui          A {@link Ui} object to display Duke's output
     * @param   taskList    A {@link TaskList} object which stores the task list
     */
    public abstract void execute(Storage storage, Ui ui, TaskList taskList) throws DukeException;
}
