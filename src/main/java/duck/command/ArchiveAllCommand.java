package duck.command;

import duck.util.Buffer;
import duck.util.Storage;
import duck.util.TaskList;
import duck.exception.DuckException;
import duck.util.ConfigLoader;

/**
 * Represents a command to archive all tasks in the task list.
 */
public class ArchiveAllCommand extends ArchiveCommand {
    private static final String ARCHIVE_ALL_COMMAND_EMPTY_TASK_LIST = "You have no tasks in your task list right now.";

    /**
     * Archives all tasks. If Duck is unable to write to the archive file,
     * the tasks are not deleted from the task list (to prevent loss of data).
     * Archive location is [project-root]/archive/duck-archive.txt by default.
     *
     * @param storage     A {@link Storage} object to cache task list
     * @param buffer      A {@link Buffer} object to buffer Duck's output
     * @param taskList    A {@link TaskList} object which stores the task list
     * @param configLoader  A {@link ConfigLoader} object to write changes to configuration
     */
    public void execute(Storage storage, Buffer buffer, TaskList taskList, ConfigLoader configLoader) throws DuckException {
        int size = taskList.getSize();
        if (size == 0) {
            buffer.formatLine(ARCHIVE_ALL_COMMAND_EMPTY_TASK_LIST);
            return;
        }

        for (int i = 1; i <= size; i++) {
            super.indexesToArchive.add(i);
        }
        super.execute(storage, buffer, taskList, configLoader);
    }
}