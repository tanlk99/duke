package duck.command;

import duck.stubs.BufferStub;
import duck.stubs.StorageHandlerStub;
import duck.stubs.TaskListStub;
import duck.stubs.TaskStub;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddCommandTest {
    private StorageHandlerStub cacheHandlerStub;
    private BufferStub bufferStub;

    @BeforeEach
    void initTests() {
        cacheHandlerStub = new StorageHandlerStub();
        bufferStub = new BufferStub();
    }

    @Test
    void execute_taskListNotEmpty_successful() {
        TaskListStub taskListStub = new TaskListStub(5);
        TaskStub dummyTask = new TaskStub("do dishes");
        AddCommand addCommand = new AddCommand(dummyTask);
        addCommand.execute(cacheHandlerStub, null, bufferStub, taskListStub, null);

        assertEquals("Got it. I've added this task:#  X do dishes#"
                + "Now you have 6 tasks in the list.#", bufferStub.getOutputString());
    }

    @Test
    void execute_taskListEmpty_successful() {
        TaskListStub taskListStub = new TaskListStub(0);
        TaskStub dummyTask = new TaskStub("do dishes");
        AddCommand addCommand = new AddCommand(dummyTask);
        addCommand.execute(cacheHandlerStub, null, bufferStub, taskListStub, null);

        assertEquals("Got it. I've added this task:#  X do dishes#"
                + "Now you have 1 task in the list.#", bufferStub.getOutputString());
    }

    @Test
    void execute_taskListNotEmpty_storageErrorPrinted() {
        cacheHandlerStub.setWillThrowStorageException(true);

        TaskListStub taskListStub = new TaskListStub(5);
        TaskStub dummyTask = new TaskStub("do dishes");
        AddCommand addCommand = new AddCommand(dummyTask);
        addCommand.execute(cacheHandlerStub, null, bufferStub, taskListStub, null);

        assertEquals("Got it. I've added this task:#  X do dishes#"
                + "Now you have 6 tasks in the list.##Sorry! I was unable to save "
                + "this update in storage. I'll try again next time.#", bufferStub.getOutputString());
    }

    @Test
    void execute_taskListEmpty_storageErrorPrinted() {
        cacheHandlerStub.setWillThrowStorageException(true);

        TaskListStub taskListStub = new TaskListStub(0);
        TaskStub dummyTask = new TaskStub("do dishes");
        AddCommand addCommand = new AddCommand(dummyTask);
        addCommand.execute(cacheHandlerStub, null, bufferStub, taskListStub, null);

        assertEquals("Got it. I've added this task:#  X do dishes#"
                + "Now you have 1 task in the list.##Sorry! I was unable to save "
                + "this update in storage. I'll try again next time.#", bufferStub.getOutputString());
    }
}