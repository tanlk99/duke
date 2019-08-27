package duke.stubs;

import duke.util.Storage;
import duke.util.TaskList;
import duke.exception.DukeException;

/**
 * Simplified version of {@link duke.util.Storage Storage} that does not do any file I/O.
 * Can be set whether or not to throw a exception (to simulate file I/O erros).
 */
public class StorageStub extends Storage {
    private boolean willThrowException = false;

    public StorageStub() {
        super("nullity");
    }

    /**
     * Sets if {@link #writeCache(TaskList) writeCache} will throw a {@link duke.exception.DukeException DukeException}.
     *
     * @param willThrowException    true if {@link #writeCache(TaskList) writeCache} is to throw exception
     */
    public void setWillThrowException(boolean willThrowException) {
        this.willThrowException = willThrowException;
    }

    @Override
    public void writeCache(TaskList taskList) throws DukeException {
        if (willThrowException) {
            throw new DukeException("");
        }
    }
}
