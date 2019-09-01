package duke.command;

import duke.stubs.StorageStub;
import duke.stubs.TaskListStub;
import duke.stubs.TaskStub;
import duke.stubs.UiStub;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddCommandTest {
    private StorageStub storageStub;
    private UiStub uiStub;

    @BeforeEach
    void initTests() {
        storageStub = new StorageStub();
        uiStub = new UiStub();
    }

    @Test
    void execute_taskListNotEmpty_successful() {
        TaskListStub taskListStub = new TaskListStub(5);
        TaskStub dummyTask = new TaskStub("do dishes");
        AddCommand addCommand = new AddCommand(dummyTask);
        addCommand.execute(storageStub, uiStub, taskListStub);

        assertEquals("LINE#Got it. I've added this task:#  X do dishes#"
                + "Now you have 6 tasks in the list.#LINE#", uiStub.getOutputString());
    }

    @Test
    void execute_taskListEmpty_successful() {
        TaskListStub taskListStub = new TaskListStub(0);
        TaskStub dummyTask = new TaskStub("do dishes");
        AddCommand addCommand = new AddCommand(dummyTask);
        addCommand.execute(storageStub, uiStub, taskListStub);

        assertEquals("LINE#Got it. I've added this task:#  X do dishes#"
                + "Now you have 1 task in the list.#LINE#", uiStub.getOutputString());
    }

    @Test
    void execute_taskListNotEmpty_storageExceptionThrown() {
        storageStub.setWillThrowException(true);

        TaskListStub taskListStub = new TaskListStub(5);
        TaskStub dummyTask = new TaskStub("do dishes");
        AddCommand addCommand = new AddCommand(dummyTask);
        addCommand.execute(storageStub, uiStub, taskListStub);

        assertEquals("LINE#Got it. I've added this task:#  X do dishes#"
                + "Now you have 6 tasks in the list.#Sorry! I was unable to save "
                + "this update in storage. I'll try again next time.#LINE#", uiStub.getOutputString());
    }

    @Test
    void execute_taskListEmpty_storageExceptionThrown() {
        storageStub.setWillThrowException(true);

        TaskListStub taskListStub = new TaskListStub(0);
        TaskStub dummyTask = new TaskStub("do dishes");
        AddCommand addCommand = new AddCommand(dummyTask);
        addCommand.execute(storageStub, uiStub, taskListStub);

        assertEquals("LINE#Got it. I've added this task:#  X do dishes#"
                + "Now you have 1 task in the list.#Sorry! I was unable to save "
                + "this update in storage. I'll try again next time.#LINE#", uiStub.getOutputString());
    }
}