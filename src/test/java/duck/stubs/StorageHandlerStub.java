package duck.stubs;

import java.util.ArrayList;
import duck.exception.DuckException;
import duck.task.Task;
import duck.util.StorageHandler;
import duck.util.TaskList;

/**
 * Simplified version of {@link StorageHandler StorageHandler} that does not do any file I/O.
 * Can be set whether or not to throw a exception (to simulate file I/O erros).
 */
public class StorageHandlerStub extends StorageHandler {
    private boolean willThrowStorageException = false;

    public StorageHandlerStub() {
        super("nullity");
    }

    /**
     * Sets if {@link #writeCache} will throw a {@link DuckException}.
     *
     * @param willThrowStorageException    True if {@link #writeCache} is to throw exception
     */
    public void setWillThrowStorageException(boolean willThrowStorageException) {
        this.willThrowStorageException = willThrowStorageException;
    }

    @Override
    public void writeCache(TaskList taskList) throws DuckException {
        if (willThrowStorageException) {
            throw new DuckException("");
        }
    }

    @Override
    public void appendCache(ArrayList<Task> taskList) throws DuckException {
        if (willThrowStorageException) {
            throw new DuckException("");
        }
    }
}
