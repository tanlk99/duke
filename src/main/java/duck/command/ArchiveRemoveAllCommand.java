package duck.command;

import duck.exception.DuckException;
import duck.util.Buffer;
import duck.util.ConfigLoader;
import duck.util.StorageHandler;
import duck.util.TaskList;

public class ArchiveRemoveAllCommand extends ArchiveRemoveCommand {
    private static final String ARCHIVE_REMOVE_COMMAND_READ_FAILED = "I was unable to retrieve your task(s) "
            + "from the archive location.";
    private static final String ARCHIVE_REMOVE_ALL_COMMAND_EMPTY_ARCHIVE =
            "You have no tasks in your archive right now.";

    /**
     * Archives all tasks. If Duck is unable to write to the archive file,
     * the tasks are not deleted from the task list (to prevent loss of data).
     *
     * @param cacheHandler     A {@link StorageHandler} object to cache task list
     * @param archiveHandler   A {@link StorageHandler} object to archive tasks
     * @param buffer           A {@link Buffer} object to buffer Duck's output
     * @param taskList         A {@link TaskList} object which stores the task list
     * @param configLoader     A {@link ConfigLoader} object to write changes to configuration
     */
    public void execute(StorageHandler cacheHandler, StorageHandler archiveHandler, Buffer buffer, TaskList taskList,
                        ConfigLoader configLoader) throws DuckException {
        int size;

        try {
            size = archiveHandler.readCache().size();
        } catch (DuckException e) {
            throw new DuckException(ARCHIVE_REMOVE_COMMAND_READ_FAILED);
        }

        if (size == 0) {
            buffer.formatLine(ARCHIVE_REMOVE_ALL_COMMAND_EMPTY_ARCHIVE);
            return;
        }

        for (int i = 1; i <= size; i++) {
            super.indexesToUnarchive.add(i);
        }
        super.execute(cacheHandler, archiveHandler, buffer, taskList, configLoader);
    }
}