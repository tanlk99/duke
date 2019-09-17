package duck.command;

import java.util.ArrayList;
import duck.exception.DuckException;
import duck.task.Task;
import duck.util.Buffer;
import duck.util.ConfigLoader;
import duck.util.StorageHandler;
import duck.util.TaskList;

public class ArchiveListCommand extends Command {
    private static final String ARCHIVE_LIST_COMMAND_NO_TASKS = "Your archive is empty.";
    private static final String ARCHIVE_LIST_COMMAND_SUCCESS = "Here are the tasks in your archive:";
    private static final String ARCHIVE_LIST_COMMAND_LIST = "%1$d.%2$s";

    /**
     * Archives all tasks. If Duck is unable to write to the archive file,
     * the tasks are not deleted from the task list (to prevent loss of data).
     * Archive location is [project-root]/archive/duck-archive.txt by default.
     *
     * @param cacheHandler     A {@link StorageHandler} object to cache task list
     * @param archiveHandler   A {@link StorageHandler} object to archive tasks
     * @param buffer           A {@link Buffer} object to buffer Duck's output
     * @param taskList         A {@link TaskList} object which stores the task list
     * @param configLoader     A {@link ConfigLoader} object to write changes to configuration
     */
    public void execute(StorageHandler cacheHandler, StorageHandler archiveHandler, Buffer buffer, TaskList taskList,
                        ConfigLoader configLoader) throws DuckException {
        ArrayList<Task> archiveList = archiveHandler.readCache();

        if (archiveList.size() == 0) {
            buffer.formatLine(ARCHIVE_LIST_COMMAND_NO_TASKS);
        } else {
            buffer.formatLine(ARCHIVE_LIST_COMMAND_SUCCESS);
            for (int i = 1; i <= archiveList.size(); i++) {
                buffer.formatLine(String.format(ARCHIVE_LIST_COMMAND_LIST,
                        i, archiveList.get(i - 1).toString()));
            }
        }
    }
}
