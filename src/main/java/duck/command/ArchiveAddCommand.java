package duck.command;

import java.util.ArrayList;
import duck.exception.DuckException;
import duck.task.Task;
import duck.util.Buffer;
import duck.util.StorageHandler;
import duck.util.TaskList;
import duck.util.ConfigLoader;

/**
 * Represents a command to archive a list of tasks in the task list.
 */
public class ArchiveAddCommand extends Command {
    private static final String ARCHIVE_ADD_COMMAND_INVALID_INDEX = "%1$d is not a valid task number.";
    private static final String ARCHIVE_ADD_COMMAND_SUCCESS = "I saved the following tasks to the archive file:";
    private static final String ARCHIVE_ADD_COMMAND_LIST = "  %1$s";
    private static final String ARCHIVE_ADD_COMMAND_FAILED = "I was unable to save your task(s) "
            + "to the archive location. Your task(s) will not be deleted.";
    private static final String STORAGE_UPDATE_SAVE_FAILED = "Sorry! I was unable to save this update "
            + "in storage. I'll try again next time.";

    protected ArrayList<Integer> indexesToArchive = new ArrayList<>();

    /**
     * Protected constructor for ArchiveAddAllCommand.
     */
    protected ArchiveAddCommand() {

    }

    /**
     * Creates an new ArchiveAddCommand with a list of indexes to archive.
     *
     * @param indexesToArchive  List of indexes to archive
     */
    public ArchiveAddCommand(ArrayList<Integer> indexesToArchive) {
        this.indexesToArchive = indexesToArchive;
    }

    /**
     * Archives a list of tasks given by index. If Duck is unable to write to the archive file,
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
        //Validate indexes
        for (int index : indexesToArchive) {
            if (index <= 0 || index > taskList.getSize()) {
                throw new DuckException(String.format(ARCHIVE_ADD_COMMAND_INVALID_INDEX, index));
            }
        }

        try {
            ArrayList<Task> tasksToArchive = taskList.getTasks(indexesToArchive);
            archiveHandler.appendCache(tasksToArchive);

            buffer.formatLine(ARCHIVE_ADD_COMMAND_SUCCESS);
            for (Task task : tasksToArchive) {
                buffer.formatLine(String.format(ARCHIVE_ADD_COMMAND_LIST, task.toString()));
            }
        } catch (DuckException e) {
            throw new DuckException(ARCHIVE_ADD_COMMAND_FAILED);
        }

        taskList.deleteTasks(indexesToArchive);

        try {
            cacheHandler.writeCache(taskList);
        } catch (DuckException e) {
            buffer.formatLine(""); //Insert empty line for readability
            buffer.formatLine(STORAGE_UPDATE_SAVE_FAILED);
        }
    }
}
