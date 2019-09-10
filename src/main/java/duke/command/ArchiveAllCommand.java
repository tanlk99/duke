package duke.command;

import duke.util.Buffer;
import duke.util.Storage;
import duke.util.TaskList;
import duke.exception.DukeException;

/**
 * Represents a command to archive all tasks in the task list.
 */
public class ArchiveAllCommand extends ArchiveCommand {
    private static final String ARCHIVE_ALL_COMMAND_EMPTY_TASK_LIST = "You have no tasks in your task list right now.";

    /**
     * Archives all tasks. If Duke is unable to write to the archive file,
     * the tasks are not deleted from the task list (to prevent loss of data).
     * Archive location is [project-root]/archive/duke-archive.txt by default.
     *
     * @param   storage     A {@link Storage} object to cache task list
     * @param   buffer      A {@link Buffer} object to buffer Duke's output
     * @param   taskList    A {@link TaskList} object which stores the task list
     */
    public void execute(Storage storage, Buffer buffer, TaskList taskList) throws DukeException {
        int size = taskList.getSize();
        if (size == 0) {
            buffer.formatLine(ARCHIVE_ALL_COMMAND_EMPTY_TASK_LIST);
            return;
        }

        for (int i = 1; i <= size; i++) {
            super.indexesToArchive.add(i);
        }
        super.execute(storage, buffer, taskList);
    }
}