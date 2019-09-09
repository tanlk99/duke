package duke.util;

import duke.exception.DukeException;
import duke.stubs.TaskStub;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class StorageTest {
    private Storage storage = new Storage("");

    @Test
    void testDirectoryPath() {
        assertEquals(".", storage.getDirectoryPath("a.txt"));
        assertEquals("f", storage.getDirectoryPath("f/a.txt"));
        assertEquals("f/g", storage.getDirectoryPath("f/g/a.txt"));
    }

    @Test
    void testWriteArchive() {
        TaskList taskList = new TaskList();
        taskList.addNewTask(new TaskStub("1"));
        taskList.addNewTask(new TaskStub("2"));
        taskList.addNewTask(new TaskStub("3"));

        try {
            storage.writeArchive(taskList);
        } catch (DukeException e) {
            e.printStackTrace();
        }
    }
}
