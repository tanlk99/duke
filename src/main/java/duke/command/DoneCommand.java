package duke.command;

import duke.util.Buffer;
import duke.util.Storage;
import duke.util.TaskList;
import duke.exception.DukeException;

/**
 * Represents a command to mark a task in the task list as done.
 */
public class DoneCommand extends Command {
    private int index;

    /**
     * Creates a new DoneCommand.
     *
     * @param   index   Index of task to mark as done
     */
    public DoneCommand(int index) {
        this.index = index;
    }

    /**
     * Returns the index of the task to mark as done.
     *
     * @return  Index of task to mark as done
     */
    public int getIndex() {
        return index;
    }

    /**
     * Marks the task given by <i>index</i> as done.
     *
     * @param   storage     A {@link Storage} object to cache task list
     * @param   buffer      A {@link Buffer} object to buffer Duke's output
     * @param   taskList    A {@link TaskList} object which stores the task list
     * @throws  DukeException   If index is invalid
     */
    public void execute(Storage storage, Buffer buffer, TaskList taskList) throws DukeException {
        if (index <= 0 || index > taskList.getSize()) {
            throw new DukeException("That is not a valid task number.");
        }

        taskList.markTaskAsDone(index);

        buffer.formatLine("Nice! I've marked this task as done:");
        buffer.formatLine("  " + taskList.getTask(index));

        try {
            storage.writeCache(taskList);
        } catch (DukeException e) {
            buffer.formatLine("Sorry! I was unable to save this update in storage. I'll try again next time.");
        }
    }
}
