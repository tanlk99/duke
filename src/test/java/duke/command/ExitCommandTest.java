package duke.command;

import duke.stubs.StorageStub;
import duke.stubs.TaskListStub;
import duke.stubs.UiStub;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ExitCommandTest {
    @Test
    void execute() {
        StorageStub storageStub = new StorageStub();
        UiStub uiStub = new UiStub();
        TaskListStub taskListStub = new TaskListStub(0);
        ExitCommand exitCommand = new ExitCommand();

        exitCommand.execute(storageStub, uiStub, taskListStub);
        assertEquals("LINE#Bye. Hope to see you again soon!#LINE#", uiStub.getOutputString());
    }
}