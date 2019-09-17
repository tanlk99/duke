package duck.command;

import duck.util.Buffer;
import duck.util.StorageHandler;
import duck.util.ConfigLoader;
import duck.util.TaskList;

/**
 * Represents a command to quit the system.
 */
public class ExitCommand extends Command {
    /**
     * Indicates whether Duck needs to exit after this command.
     * ExitCommand instances will always return true.
     *
     * @return True if Duck needs to terminate
     */
    public boolean shouldTerminate() {
        return true;
    }

    /**
     * Does nothing. Command occurs by checking terminate().
     *
     * @param cacheHandler     A {@link StorageHandler} object to cache task list
     * @param archiveHandler   A {@link StorageHandler} object to archive tasks
     * @param buffer           A {@link Buffer} object to buffer Duck's output
     * @param taskList         A {@link TaskList} object which stores the task list
     * @param configLoader     A {@link ConfigLoader} object to write changes to configuration
     */
    public void execute(StorageHandler cacheHandler, StorageHandler archiveHandler, Buffer buffer, TaskList taskList,
                        ConfigLoader configLoader) {
    }
}
