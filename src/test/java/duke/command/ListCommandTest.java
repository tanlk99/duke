package duke.command;

import duke.stubs.StorageStub;
import duke.stubs.TaskListStub;
import duke.stubs.BufferStub;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ListCommandTest {
    private StorageStub storageStub;
    private BufferStub bufferStub;

    @BeforeEach
    void initTests() {
        storageStub = new StorageStub();
        bufferStub = new BufferStub();
    }

    @Test
    void execute_taskListEmpty() {
        TaskListStub taskListStub = new TaskListStub(0);
        ListCommand listCommand = new ListCommand();

        listCommand.execute(storageStub, bufferStub, taskListStub);
        assertEquals("You have no tasks right now.#", bufferStub.getOutputString());
    }

    @Test
    void execute_taskListSizeOne() {
        TaskListStub taskListStub = new TaskListStub(1);
        ListCommand listCommand = new ListCommand();

        listCommand.execute(storageStub, bufferStub, taskListStub);
        assertEquals("Here are the tasks in your list:#1.X task1#",
                bufferStub.getOutputString());
    }

    @Test
    void execute_taskListMedium() {
        TaskListStub taskListStub = new TaskListStub(5);
        ListCommand listCommand = new ListCommand();

        listCommand.execute(storageStub, bufferStub, taskListStub);
        assertEquals("Here are the tasks in your list:#1.X task1#"
                + "2.X task2#3.X task3#4.X task4#5.X task5#", bufferStub.getOutputString());
    }
}