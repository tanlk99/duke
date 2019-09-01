package duke.command;

import duke.stubs.StorageStub;
import duke.stubs.TaskListStub;
import duke.stubs.UiStub;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FindCommandTest {
    private StorageStub storageStub;
    private UiStub uiStub;

    @BeforeEach
    void initTests() {
        storageStub = new StorageStub();
        uiStub = new UiStub();
    }

    @Test
    void execute_taskListEmpty() {
        TaskListStub taskListStub = new TaskListStub(0);
        FindCommand findCommand = new FindCommand("nullity");

        findCommand.execute(storageStub, uiStub, taskListStub);
        assertEquals("There were no tasks in the list that matched your search term.#"
                + "", uiStub.getOutputString());
    }

    @Test
    void execute_allMatch() {
        TaskListStub taskListStub = new TaskListStub(3);
        FindCommand findCommand = new FindCommand("nullity");

        findCommand.execute(storageStub, uiStub, taskListStub);
        assertEquals("Here are the matching tasks in your list:#"
                + "1.X task1#2.X task2#3.X task3#", uiStub.getOutputString());
    }

    @Test
    void execute_noneMatch() {
        TaskListStub taskListStub = new TaskListStub(3);
        taskListStub.setMatchType(1);
        FindCommand findCommand = new FindCommand("nullity");

        findCommand.execute(storageStub, uiStub, taskListStub);
        assertEquals("There were no tasks in the list that matched your search term.#"
                + "", uiStub.getOutputString());
    }

    @Test
    void testExecute_oddMatch() {
        TaskListStub taskListStub = new TaskListStub(3);
        taskListStub.setMatchType(2);
        FindCommand findCommand = new FindCommand("nullity");

        findCommand.execute(storageStub, uiStub, taskListStub);
        assertEquals("Here are the matching tasks in your list:#"
                + "1.X task1#3.X task3#", uiStub.getOutputString());
    }

    @Test
    void testExecute_lastMatch() {
        TaskListStub taskListStub = new TaskListStub(3);
        taskListStub.setMatchType(3);
        FindCommand findCommand = new FindCommand("nullity");

        findCommand.execute(storageStub, uiStub, taskListStub);
        assertEquals("Here are the matching tasks in your list:#"
                + "3.X task3#", uiStub.getOutputString());
    }
}