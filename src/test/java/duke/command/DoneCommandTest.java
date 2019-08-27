package duke.command;

import duke.exception.DukeException;
import duke.stubs.StorageStub;
import duke.stubs.TaskListStub;
import duke.stubs.UiStub;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DoneCommandTest {
    private StorageStub storageStub;
    private UiStub uiStub;

    @BeforeEach
    void initTests() {
        storageStub = new StorageStub();
        uiStub = new UiStub();
    }

    @Test
    void testExecute_default() {
        TaskListStub taskListStub = new TaskListStub(5);
        DoneCommand doneCommand = new DoneCommand(3);

        try {
            doneCommand.execute(storageStub, uiStub, taskListStub);
            assertEquals("LINE#Nice! I've marked this task as done:#  O task3#LINE#",
                    uiStub.getOutputString());
        } catch (DukeException e) { //shouldn't throw this exception
            assertEquals(0, 1);
        }
    }

    @Test
    void testExecute_storageExceptionThrown() {
        storageStub.setWillThrowException(true);

        TaskListStub taskListStub = new TaskListStub(5);
        DoneCommand doneCommand = new DoneCommand(3);

        try {
            doneCommand.execute(storageStub, uiStub, taskListStub);
            assertEquals("LINE#Nice! I've marked this task as done:#  O task3#Sorry! I "
                    + "was unable to save this update in storage. I'll try again next time.#LINE#",
                    uiStub.getOutputString());
        } catch (DukeException e) { //shouldn't throw this exception
            assertEquals(0, 1);
        }
    }

    @Test
    void testExecute_negativeIndex() {
        storageStub.setWillThrowException(true);

        TaskListStub taskListStub = new TaskListStub(5);
        DoneCommand doneCommand = new DoneCommand(-4);

        try {
            doneCommand.execute(storageStub, uiStub, taskListStub);
            assertEquals(0, 1);
        } catch (DukeException e) { //should always throw this exception
            assertEquals("That is not a valid task number.", e.getMessage());
        }
    }

    @Test
    void testExecute_tooLargeIndex() {
        storageStub.setWillThrowException(true);

        TaskListStub taskListStub = new TaskListStub(5);
        DoneCommand doneCommand = new DoneCommand(9);

        try {
            doneCommand.execute(storageStub, uiStub, taskListStub);

            assertEquals(0, 1);
        } catch (DukeException e) { //should always throw this exception
            assertEquals("That is not a valid task number.", e.getMessage());
        }
    }
}