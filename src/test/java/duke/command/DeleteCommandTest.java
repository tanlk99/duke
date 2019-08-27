package duke.command;

import duke.exception.DukeException;
import duke.stubs.StorageStub;
import duke.stubs.TaskListStub;
import duke.stubs.UiStub;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeleteCommandTest {
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
        DeleteCommand deleteCommand = new DeleteCommand(3);

        try {
            deleteCommand.execute(storageStub, uiStub, taskListStub);

            assertEquals("LINE#Noted. I've removed this task.#  X task3#"
                    + "Now you have 4 tasks in the list.#LINE#", uiStub.getOutputString());
        } catch (DukeException e) { //shouldn't throw this exception
            assertEquals(0, 1);
        }
    }

    @Test
    void testExecute_taskListSmall() {
        TaskListStub taskListStub = new TaskListStub(2);
        DeleteCommand deleteCommand = new DeleteCommand(2);

        try {
            deleteCommand.execute(storageStub, uiStub, taskListStub);
            assertEquals("LINE#Noted. I've removed this task.#  X task2#"
                    + "Now you have 1 task in the list.#LINE#", uiStub.getOutputString());
        } catch (DukeException e) { //shouldn't throw this exception
            assertEquals(0, 1);
        }
    }

    @Test
    void testExecute_storageExceptionThrown() {
        storageStub.setWillThrowException(true);

        TaskListStub taskListStub = new TaskListStub(5);
        DeleteCommand deleteCommand = new DeleteCommand(3);

        try {
            deleteCommand.execute(storageStub, uiStub, taskListStub);
            assertEquals("LINE#Noted. I've removed this task.#  X task3#"
                    + "Now you have 4 tasks in the list.#Sorry! I was unable to save "
                    + "this update in storage. I'll try again next time.#LINE#", uiStub.getOutputString());
        } catch (DukeException e) { //shouldn't throw this exception
            assertEquals(0, 1);
        }
    }

    @Test
    void testExecute_negativeIndex() {
        storageStub.setWillThrowException(true);

        TaskListStub taskListStub = new TaskListStub(5);
        DeleteCommand deleteCommand = new DeleteCommand(-1);

        try {
            deleteCommand.execute(storageStub, uiStub, taskListStub);
            assertEquals(0, 1);
        } catch (DukeException e) { //should always throw this exception
            assertEquals(e.getMessage(), "That is not a valid task number.");
        }
    }

    @Test
    void testExecute_tooLargeIndex() {
        storageStub.setWillThrowException(true);

        TaskListStub taskListStub = new TaskListStub(5);
        DeleteCommand deleteCommand = new DeleteCommand(9);

        try {
            deleteCommand.execute(storageStub, uiStub, taskListStub);
            assertEquals(0, 1);
        } catch (DukeException e) { //should always throw this exception
            assertEquals(e.getMessage(), "That is not a valid task number.");
        }
    }
}