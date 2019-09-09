package duke.command;

import duke.util.Buffer;
import duke.util.Storage;
import duke.util.TaskList;

/**
 * Represents a command to quit the system.
 */
public class ExitCommand extends Command {
    /**
     * Indicates whether Duke needs to exit after this command.
     * ExitCommand instances will always return true.
     *
     * @return True if Duke needs to terminate
     */
    public boolean shouldTerminate() {
        return true;
    }

    /**
     * Does nothing. Command occurs by checking terminate().
     *
     * @param   storage     A {@link Storage} object to cache task list
     * @param   buffer      A {@link Buffer} object to buffer Duke's output
     * @param   taskList    A {@link TaskList} object which stores the task list
     */
    public void execute(Storage storage, Buffer buffer, TaskList taskList) {
    }
}
