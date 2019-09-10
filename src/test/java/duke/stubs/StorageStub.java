package duke.stubs;

import java.util.ArrayList;
import duke.util.Storage;
import duke.util.TaskList;
import duke.task.Task;
import duke.exception.DukeException;

/**
 * Simplified version of {@link duke.util.Storage Storage} that does not do any file I/O.
 * Can be set whether or not to throw a exception (to simulate file I/O erros).
 */
public class StorageStub extends Storage {
    private boolean willThrowStorageException = false;
    private boolean willThrowArchiveException = false;

    public StorageStub() {
        super("nullity");
    }

    /**
     * Sets if {@link #writeCache} will throw a {@link DukeException}.
     *
     * @param willThrowStorageException    True if {@link #writeCache} is to throw exception
     */
    public void setWillThrowStorageException(boolean willThrowStorageException) {
        this.willThrowStorageException = willThrowStorageException;
    }

    /**
     * Sets if {@link #writeArchive} will throw a {@link DukeException}.
     *
     * @param willThrowArchiveException    True if {@link #writeArchive} is to throw exception
     */
    public void setWillThrowArchiveException(boolean willThrowArchiveException) {
        this.willThrowArchiveException = willThrowArchiveException;
    }

    @Override
    public void writeCache(TaskList taskList) throws DukeException {
        if (willThrowStorageException) {
            throw new DukeException("");
        }
    }

    @Override
    public void writeArchive(ArrayList<Task> taskList) throws DukeException {
        if (willThrowArchiveException) {
            throw new DukeException("");
        }
    }
}
