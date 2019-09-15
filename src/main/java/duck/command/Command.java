package duck.command;

import duck.util.Buffer;
import duck.util.Storage;
import duck.util.TaskList;
import duck.exception.DuckException;

/**
 * Represents a command given by the user. Can have a variety of functions,
 * including exiting, adding tasks and deleting tasks.
 */
public abstract class Command {
    /**
     * Indicates whether Duck needs to exit after this command.
     * This method should only return true for {@link duck.command.ExitCommand ExitCommand} instances.
     *
     * @return True if Duck needs to terminate
     */
    public boolean shouldTerminate() {
        return false;
    }

    /**
     * Executes the command.
     *
     * @param   storage     A {@link Storage} object to cache task list
     * @param   buffer      A {@link Buffer} object to buffer Duck's output
     * @param   taskList    A {@link TaskList} object which stores the task list
     */
    public abstract void execute(Storage storage, Buffer buffer, TaskList taskList) throws DuckException;
}
