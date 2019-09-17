package duck.command;

import duck.exception.DuckException;
import duck.stubs.BufferStub;
import duck.stubs.StorageHandlerStub;
import duck.stubs.TaskListStub;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeleteCommandTest {
    private StorageHandlerStub cacheHandlerStub;
    private BufferStub bufferStub;

    @BeforeEach
    void initTests() {
        cacheHandlerStub = new StorageHandlerStub();
        bufferStub = new BufferStub();
    }

    @Test
    void execute_taskListMedium_successful() {
        TaskListStub taskListStub = new TaskListStub(5);
        DeleteCommand deleteCommand = new DeleteCommand(3);

        try {
            deleteCommand.execute(cacheHandlerStub, null, bufferStub, taskListStub, null);

            assertEquals("Noted. I've removed this task.#  X task3#"
                    + "Now you have 4 tasks in the list.#", bufferStub.getOutputString());
        } catch (DuckException e) { //shouldn't throw this exception
            assertEquals(1, 0);
        }
    }

    @Test
    void execute_taskListSmall_successful() {
        TaskListStub taskListStub = new TaskListStub(2);
        DeleteCommand deleteCommand = new DeleteCommand(2);

        try {
            deleteCommand.execute(cacheHandlerStub, null, bufferStub, taskListStub, null);
            assertEquals("Noted. I've removed this task.#  X task2#"
                    + "Now you have 1 task in the list.#", bufferStub.getOutputString());
        } catch (DuckException e) { //shouldn't throw this exception
            assertEquals(1, 0);
        }
    }

    @Test
    void execute_storageErrorPrinted() {
        cacheHandlerStub.setWillThrowStorageException(true);

        TaskListStub taskListStub = new TaskListStub(5);
        DeleteCommand deleteCommand = new DeleteCommand(3);

        try {
            deleteCommand.execute(cacheHandlerStub, null, bufferStub, taskListStub, null);
            assertEquals("Noted. I've removed this task.#  X task3#"
                    + "Now you have 4 tasks in the list.##Sorry! I was unable to save "
                    + "this update in storage. I'll try again next time.#", bufferStub.getOutputString());
        } catch (DuckException e) { //shouldn't throw this exception
            assertEquals(1, 0);
        }
    }

    @Test
    void execute_negativeIndex_exceptionThrown() {
        cacheHandlerStub.setWillThrowStorageException(true);

        TaskListStub taskListStub = new TaskListStub(5);
        DeleteCommand deleteCommand = new DeleteCommand(-1);

        try {
            deleteCommand.execute(cacheHandlerStub, null, bufferStub, taskListStub, null);
            assertEquals(1, 0);
        } catch (DuckException e) { //should always throw this exception
            assertEquals("", bufferStub.getOutputString());
            assertEquals(e.getMessage(), "That is not a valid task number.");
        }
    }

    @Test
    void execute_tooLargeIndex_exceptionThrown() {
        cacheHandlerStub.setWillThrowStorageException(true);

        TaskListStub taskListStub = new TaskListStub(5);
        DeleteCommand deleteCommand = new DeleteCommand(9);

        try {
            deleteCommand.execute(cacheHandlerStub, null, bufferStub, taskListStub, null);
            assertEquals(1, 0);
        } catch (DuckException e) { //should always throw this exception
            assertEquals("", bufferStub.getOutputString());
            assertEquals(e.getMessage(), "That is not a valid task number.");
        }
    }
}