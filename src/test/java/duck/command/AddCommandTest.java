package duck.command;

import duck.stubs.BufferStub;
import duck.stubs.StorageStub;
import duck.stubs.TaskListStub;
import duck.stubs.TaskStub;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddCommandTest {
    private StorageStub storageStub;
    private BufferStub bufferStub;

    @BeforeEach
    void initTests() {
        storageStub = new StorageStub();
        bufferStub = new BufferStub();
    }

    @Test
    void execute_taskListNotEmpty_successful() {
        TaskListStub taskListStub = new TaskListStub(5);
        TaskStub dummyTask = new TaskStub("do dishes");
        AddCommand addCommand = new AddCommand(dummyTask);
        addCommand.execute(storageStub, bufferStub, taskListStub);

        assertEquals("Got it. I've added this task:#  X do dishes#"
                + "Now you have 6 tasks in the list.#", bufferStub.getOutputString());
    }

    @Test
    void execute_taskListEmpty_successful() {
        TaskListStub taskListStub = new TaskListStub(0);
        TaskStub dummyTask = new TaskStub("do dishes");
        AddCommand addCommand = new AddCommand(dummyTask);
        addCommand.execute(storageStub, bufferStub, taskListStub);

        assertEquals("Got it. I've added this task:#  X do dishes#"
                + "Now you have 1 task in the list.#", bufferStub.getOutputString());
    }

    @Test
    void execute_taskListNotEmpty_storageErrorPrinted() {
        storageStub.setWillThrowStorageException(true);

        TaskListStub taskListStub = new TaskListStub(5);
        TaskStub dummyTask = new TaskStub("do dishes");
        AddCommand addCommand = new AddCommand(dummyTask);
        addCommand.execute(storageStub, bufferStub, taskListStub);

        assertEquals("Got it. I've added this task:#  X do dishes#"
                + "Now you have 6 tasks in the list.##Sorry! I was unable to save "
                + "this update in storage. I'll try again next time.#", bufferStub.getOutputString());
    }

    @Test
    void execute_taskListEmpty_storageErrorPrinted() {
        storageStub.setWillThrowStorageException(true);

        TaskListStub taskListStub = new TaskListStub(0);
        TaskStub dummyTask = new TaskStub("do dishes");
        AddCommand addCommand = new AddCommand(dummyTask);
        addCommand.execute(storageStub, bufferStub, taskListStub);

        assertEquals("Got it. I've added this task:#  X do dishes#"
                + "Now you have 1 task in the list.##Sorry! I was unable to save "
                + "this update in storage. I'll try again next time.#", bufferStub.getOutputString());
    }
}