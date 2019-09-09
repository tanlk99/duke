package duke.command;

import duke.util.Buffer;
import duke.util.Storage;
import duke.util.TaskList;
import duke.exception.DukeException;

/**
 * Represents a command to archive all tasks in the task list.
 */
public class ArchiveAllCommand extends Command {
    /**
     * Archives all tasks. If Duke is unable to write to the archive file,
     * the tasks are not deleted from the task list (to prevent loss of data).
     * Archive location is [project-root]/archive/duke-archive.txt by default.
     *
     * @param   storage     A {@link Storage} object to cache task list
     * @param   buffer      A {@link Buffer} object to buffer Duke's output
     * @param   taskList    A {@link TaskList} object which stores the task list
     */
    public void execute(Storage storage, Buffer buffer, TaskList taskList) {
        try {
            storage.writeArchive(taskList);
            buffer.formatLine("Your task list has been saved to the archive file.");
        } catch (DukeException e) {
            buffer.formatLine("I was unable to save your tasks to the archive location.");
            buffer.formatLine("Your tasks will not be deleted.");
            return;
        }

        taskList.deleteAllTasks();

        try {
            storage.writeCache(taskList);
        } catch (DukeException e) {
            buffer.formatLine(""); //insert empty line for readability
            buffer.formatLine("Sorry! I was unable to save this update in storage. I'll try again next time.");
        }
    }
}