package duke.command;

import duke.exception.DukeException;
import duke.stubs.StorageStub;
import duke.stubs.TaskListStub;
import duke.stubs.BufferStub;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DoneCommandTest {
    private StorageStub storageStub;
    private BufferStub uiStub;

    @BeforeEach
    void initTests() {
        storageStub = new StorageStub();
        uiStub = new BufferStub();
    }

    @Test
    void execute_validIndex_successful() {
        TaskListStub taskListStub = new TaskListStub(5);
        DoneCommand doneCommand = new DoneCommand(3);

        try {
            doneCommand.execute(storageStub, uiStub, taskListStub);
            assertEquals("Nice! I've marked this task as done:#  O task3#",
                    uiStub.getOutputString());
        } catch (DukeException e) { //shouldn't throw this exception
            assertEquals(0, 1);
        }
    }

    @Test
    void execute_validIndex_storageExceptionThrown() {
        storageStub.setWillThrowException(true);

        TaskListStub taskListStub = new TaskListStub(5);
        DoneCommand doneCommand = new DoneCommand(3);

        try {
            doneCommand.execute(storageStub, uiStub, taskListStub);
            assertEquals("Nice! I've marked this task as done:#  O task3#Sorry! I "
                    + "was unable to save this update in storage. I'll try again next time.#",
                    uiStub.getOutputString());
        } catch (DukeException e) { //shouldn't throw this exception
            assertEquals(0, 1);
        }
    }

    @Test
    void execute_negativeIndex_exceptionThrown() {
        storageStub.setWillThrowException(true); //should not interact with storage

        TaskListStub taskListStub = new TaskListStub(5);
        DoneCommand doneCommand = new DoneCommand(-4);

        try {
            doneCommand.execute(storageStub, uiStub, taskListStub);
            assertEquals(0, 1);
        } catch (DukeException e) { //should always throw this exception
            assertEquals("", uiStub.getOutputString());
            assertEquals("That is not a valid task number.", e.getMessage());
        }
    }

    @Test
    void execute_tooLargeIndex_exceptionThrown() {
        storageStub.setWillThrowException(true); //should not interact with storage

        TaskListStub taskListStub = new TaskListStub(5);
        DoneCommand doneCommand = new DoneCommand(9);

        try {
            doneCommand.execute(storageStub, uiStub, taskListStub);

            assertEquals(0, 1);
        } catch (DukeException e) { //should always throw this exception
            assertEquals("", uiStub.getOutputString());
            assertEquals("That is not a valid task number.", e.getMessage());
        }
    }
}