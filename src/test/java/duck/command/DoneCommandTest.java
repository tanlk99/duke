package duck.command;

import duck.exception.DuckException;
import duck.stubs.StorageHandlerStub;
import duck.stubs.TaskListStub;
import duck.stubs.BufferStub;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DoneCommandTest {
    private StorageHandlerStub cacheHandlerStub;
    private BufferStub bufferStub;

    @BeforeEach
    void initTests() {
        cacheHandlerStub = new StorageHandlerStub();
        bufferStub = new BufferStub();
    }

    @Test
    void execute_validIndex_successful() {
        TaskListStub taskListStub = new TaskListStub(5);
        DoneCommand doneCommand = new DoneCommand(3);

        try {
            doneCommand.execute(cacheHandlerStub, null, bufferStub, taskListStub, null);
            assertEquals("Nice! I've marked this task as done:#  O task3#",
                    bufferStub.getOutputString());
        } catch (DuckException e) { //shouldn't throw this exception
            assertEquals(1, 0);
        }
    }

    @Test
    void execute_validIndex_storageErrorPrinted() {
        cacheHandlerStub.setWillThrowStorageException(true);

        TaskListStub taskListStub = new TaskListStub(5);
        DoneCommand doneCommand = new DoneCommand(3);

        try {
            doneCommand.execute(cacheHandlerStub, null, bufferStub, taskListStub, null);
            assertEquals("Nice! I've marked this task as done:#  O task3##Sorry! I "
                    + "was unable to save this update in storage. I'll try again next time.#",
                    bufferStub.getOutputString());
        } catch (DuckException e) { //shouldn't throw this exception
            assertEquals(1, 0);
        }
    }

    @Test
    void execute_negativeIndex_exceptionThrown() {
        cacheHandlerStub.setWillThrowStorageException(true); //should not interact with storage

        TaskListStub taskListStub = new TaskListStub(5);
        DoneCommand doneCommand = new DoneCommand(-4);

        try {
            doneCommand.execute(cacheHandlerStub, null, bufferStub, taskListStub, null);
            assertEquals(1, 0);
        } catch (DuckException e) { //should always throw this exception
            assertEquals("", bufferStub.getOutputString());
            assertEquals("That is not a valid task number.", e.getMessage());
        }
    }

    @Test
    void execute_tooLargeIndex_exceptionThrown() {
        cacheHandlerStub.setWillThrowStorageException(true); //should not interact with storage

        TaskListStub taskListStub = new TaskListStub(5);
        DoneCommand doneCommand = new DoneCommand(9);

        try {
            doneCommand.execute(cacheHandlerStub, null, bufferStub, taskListStub, null);

            assertEquals(1, 0);
        } catch (DuckException e) { //should always throw this exception
            assertEquals("", bufferStub.getOutputString());
            assertEquals("That is not a valid task number.", e.getMessage());
        }
    }
}