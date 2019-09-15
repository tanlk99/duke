package duck.stubs;

import java.util.ArrayList;
import duck.util.Storage;
import duck.util.TaskList;
import duck.task.Task;
import duck.exception.DuckException;

/**
 * Simplified version of {@link duck.util.Storage Storage} that does not do any file I/O.
 * Can be set whether or not to throw a exception (to simulate file I/O erros).
 */
public class StorageStub extends Storage {
    private boolean willThrowStorageException = false;
    private boolean willThrowArchiveException = false;

    public StorageStub() {
        super("nullity", "nullity");
    }

    /**
     * Sets if {@link #writeCache} will throw a {@link DuckException}.
     *
     * @param willThrowStorageException    True if {@link #writeCache} is to throw exception
     */
    public void setWillThrowStorageException(boolean willThrowStorageException) {
        this.willThrowStorageException = willThrowStorageException;
    }

    /**
     * Sets if {@link #writeArchive} will throw a {@link DuckException}.
     *
     * @param willThrowArchiveException    True if {@link #writeArchive} is to throw exception
     */
    public void setWillThrowArchiveException(boolean willThrowArchiveException) {
        this.willThrowArchiveException = willThrowArchiveException;
    }

    @Override
    public void writeCache(TaskList taskList) throws DuckException {
        if (willThrowStorageException) {
            throw new DuckException("");
        }
    }

    @Override
    public void writeArchive(ArrayList<Task> taskList) throws DuckException {
        if (willThrowArchiveException) {
            throw new DuckException("");
        }
    }
}
