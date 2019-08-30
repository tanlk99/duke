package duke.command;

import duke.stubs.StorageStub;
import duke.stubs.TaskListStub;
import duke.stubs.UiStub;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ListCommandTest {
    private StorageStub storageStub;
    private UiStub uiStub;

    @BeforeEach
    void initTests() {
        storageStub = new StorageStub();
        uiStub = new UiStub();
    }

    @Test
    void testExecute_taskListEmpty() {
        TaskListStub taskListStub = new TaskListStub(0);
        ListCommand listCommand = new ListCommand();

        listCommand.execute(storageStub, uiStub, taskListStub);
        assertEquals("LINE#You have no tasks right now.#LINE#", uiStub.getOutputString());
    }

    @Test
    void testExecute_taskListSizeOne() {
        TaskListStub taskListStub = new TaskListStub(1);
        ListCommand listCommand = new ListCommand();

        listCommand.execute(storageStub, uiStub, taskListStub);
        assertEquals("LINE#Here are the tasks in your list:#1.X task1#LINE#",
                uiStub.getOutputString());
    }

    @Test
    void testExecute_taskListMedium() {
        TaskListStub taskListStub = new TaskListStub(5);
        ListCommand listCommand = new ListCommand();

        listCommand.execute(storageStub, uiStub, taskListStub);
        assertEquals("LINE#Here are the tasks in your list:#1.X task1#"
                + "2.X task2#3.X task3#4.X task4#5.X task5#LINE#", uiStub.getOutputString());
    }
}