package duck.command;

import java.util.ArrayList;
import java.util.Collections;
import duck.exception.DuckException;
import duck.task.Task;
import duck.util.Buffer;
import duck.util.ConfigLoader;
import duck.util.StorageHandler;
import duck.util.TaskList;

/**
 * Represents a command to unarchive a list of tasks in the archive.
 */
public class ArchiveRemoveCommand extends Command {
    private static final String ARCHIVE_REMOVE_COMMAND_INVALID_INDEX = "%1$d is not a valid task number.";
    private static final String ARCHIVE_REMOVE_COMMAND_SUCCESS = "I moved the following files from"
            + "the archive to the task list:";
    private static final String ARCHIVE_REMOVE_COMMAND_LIST = "  %1$s";
    private static final String ARCHIVE_REMOVE_COMMAND_READ_FAILED = "I was unable to retrieve your task(s) "
            + "from the archive location.";
    private static final String ARCHIVE_REMOVE_COMMAND_WRITE_FAILED = "I was unable to remove your task(s) "
            + "from the archive location.";
    private static final String STORAGE_UPDATE_SAVE_FAILED = "I was unable to save this update "
            + "in storage. Your archived tasks will not be removed.";

    protected ArrayList<Integer> indexesToUnarchive = new ArrayList<>();

    /**
     * Protected constructor for ArchiveRemoveAllCommand.
     */
    protected ArchiveRemoveCommand() {

    }

    /**
     * Creates an new ArchiveRemoveCommand with a list of indexes to unarchive.
     *
     * @param indexesToUnarchive List of indexes to unarchive
     */
    public ArchiveRemoveCommand(ArrayList<Integer> indexesToUnarchive) {
        this.indexesToUnarchive = indexesToUnarchive;
    }

    /**
     * Un-archives a list of tasks given by index. If Duck is unable to write to the cache file,
     * the tasks are not deleted from the archive (to prevent loss of data).
     *
     * @param cacheHandler   A {@link StorageHandler} object to cache task list
     * @param archiveHandler A {@link StorageHandler} object to archive tasks
     * @param buffer         A {@link Buffer} object to buffer Duck's output
     * @param taskList       A {@link TaskList} object which stores the task list
     * @param configLoader   A {@link ConfigLoader} object to write changes to configuration
     */
    public void execute(StorageHandler cacheHandler, StorageHandler archiveHandler, Buffer buffer, TaskList taskList,
                        ConfigLoader configLoader) throws DuckException {
        ArrayList<Task> archivedTasks;

        try {
            archivedTasks = archiveHandler.readCache();
        } catch (DuckException e) {
            throw new DuckException(ARCHIVE_REMOVE_COMMAND_READ_FAILED);
        }

        ArrayList<Task> tasksFromArchive = new ArrayList<>();

        //Validate indexes
        for (int index : indexesToUnarchive) {
            if (index <= 0 || index > archivedTasks.size()) {
                throw new DuckException(String.format(ARCHIVE_REMOVE_COMMAND_INVALID_INDEX, index));
            } else {
                tasksFromArchive.add(archivedTasks.get(index - 1));
            }
        }

        for (Task task : tasksFromArchive) {
            taskList.addNewTask(task);
        }

        try {
            cacheHandler.writeCache(taskList);
        } catch (DuckException e) {
            throw new DuckException(STORAGE_UPDATE_SAVE_FAILED);
        }

        Collections.sort(indexesToUnarchive);
        for (int i = indexesToUnarchive.size() - 1; i >= 0; i--) { //remove from the back
            archivedTasks.remove(indexesToUnarchive.get(i) - 1);
        }

        try {
            archiveHandler.writeCache(archivedTasks);
        } catch (DuckException e) {
            throw new DuckException(ARCHIVE_REMOVE_COMMAND_WRITE_FAILED);
        }

        buffer.formatLine(ARCHIVE_REMOVE_COMMAND_SUCCESS);
        for (Task task : tasksFromArchive) {
            buffer.formatLine(String.format(ARCHIVE_REMOVE_COMMAND_LIST, task.toString()));
        }
    }
}