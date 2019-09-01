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
    void execute_taskListMedium_successful() {
        TaskListStub taskListStub = new TaskListStub(5);
        DeleteCommand deleteCommand = new DeleteCommand(3);

        try {
            deleteCommand.execute(storageStub, uiStub, taskListStub);

            assertEquals("Noted. I've removed this task.#  X task3#"
                    + "Now you have 4 tasks in the list.#", uiStub.getOutputString());
        } catch (DukeException e) { //shouldn't throw this exception
            assertEquals(0, 1);
        }
    }

    @Test
    void execute_taskListSmall_successful() {
        TaskListStub taskListStub = new TaskListStub(2);
        DeleteCommand deleteCommand = new DeleteCommand(2);

        try {
            deleteCommand.execute(storageStub, uiStub, taskListStub);
            assertEquals("Noted. I've removed this task.#  X task2#"
                    + "Now you have 1 task in the list.#", uiStub.getOutputString());
        } catch (DukeException e) { //shouldn't throw this exception
            assertEquals(0, 1);
        }
    }

    @Test
    void execute_storageExceptionThrown() {
        storageStub.setWillThrowException(true);

        TaskListStub taskListStub = new TaskListStub(5);
        DeleteCommand deleteCommand = new DeleteCommand(3);

        try {
            deleteCommand.execute(storageStub, uiStub, taskListStub);
            assertEquals("Noted. I've removed this task.#  X task3#"
                    + "Now you have 4 tasks in the list.#Sorry! I was unable to save "
                    + "this update in storage. I'll try again next time.#", uiStub.getOutputString());
        } catch (DukeException e) { //shouldn't throw this exception
            assertEquals(0, 1);
        }
    }

    @Test
    void execute_negativeIndex_exceptionThrown() {
        storageStub.setWillThrowException(true);

        TaskListStub taskListStub = new TaskListStub(5);
        DeleteCommand deleteCommand = new DeleteCommand(-1);

        try {
            deleteCommand.execute(storageStub, uiStub, taskListStub);
            assertEquals(0, 1);
        } catch (DukeException e) { //should always throw this exception
            assertEquals("", uiStub.getOutputString());
            assertEquals(e.getMessage(), "That is not a valid task number.");
        }
    }

    @Test
    void execute_tooLargeIndex_exceptionThrown() {
        storageStub.setWillThrowException(true);

        TaskListStub taskListStub = new TaskListStub(5);
        DeleteCommand deleteCommand = new DeleteCommand(9);

        try {
            deleteCommand.execute(storageStub, uiStub, taskListStub);
            assertEquals(0, 1);
        } catch (DukeException e) { //should always throw this exception
            assertEquals("", uiStub.getOutputString());
            assertEquals(e.getMessage(), "That is not a valid task number.");
        }
    }
}