package duck.command;

import duck.stubs.StorageHandlerStub;
import duck.stubs.TaskListStub;
import duck.stubs.BufferStub;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ExitCommandTest {
    @Test
    void execute() {
        StorageHandlerStub cacheHandlerStub = new StorageHandlerStub();
        BufferStub bufferStub = new BufferStub();
        TaskListStub taskListStub = new TaskListStub(0);
        ExitCommand exitCommand = new ExitCommand();

        exitCommand.execute(cacheHandlerStub, null, bufferStub, taskListStub, null);
        assertEquals("", bufferStub.getOutputString());
    }
}