package duke.command;

import duke.stubs.StorageStub;
import duke.stubs.TaskListStub;
import duke.stubs.BufferStub;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ExitCommandTest {
    @Test
    void execute() {
        StorageStub storageStub = new StorageStub();
        BufferStub uiStub = new BufferStub();
        TaskListStub taskListStub = new TaskListStub(0);
        ExitCommand exitCommand = new ExitCommand();

        exitCommand.execute(storageStub, uiStub, taskListStub);
        assertEquals("", uiStub.getOutputString());
    }
}