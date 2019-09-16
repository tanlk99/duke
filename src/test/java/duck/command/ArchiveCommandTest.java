package duck.command;

import java.util.ArrayList;
import java.util.Arrays;
import duck.exception.DuckException;
import duck.stubs.BufferStub;
import duck.stubs.StorageStub;
import duck.stubs.TaskListStub;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArchiveCommandTest {
    private StorageStub storageStub;
    private BufferStub bufferStub;

    @BeforeEach
    void initTests() {
        storageStub = new StorageStub();
        bufferStub = new BufferStub();
    }

    @Test
    void execute_indexesValid_successful() {
        TaskListStub taskListStub = new TaskListStub(5);
        ArrayList<Integer> indexesToArchive = new ArrayList<>(Arrays.asList(1, 3));
        ArchiveCommand archiveCommand = new ArchiveCommand(indexesToArchive);

        try {
            archiveCommand.execute(storageStub, bufferStub, taskListStub, null);
            assertEquals("I saved the following tasks to the archive file:#  X task1#"
                    + "  X task3#", bufferStub.getOutputString());
        } catch (DuckException e) {
            assertEquals(1, 0);
        }
    }

    @Test
    void execute_indexInvalid_exceptionThrown() {
        TaskListStub taskListStub = new TaskListStub(5);
        ArrayList<Integer> indexesToArchive = new ArrayList<>(Arrays.asList(1, -1, 2));
        ArchiveCommand archiveCommand = new ArchiveCommand(indexesToArchive);

        try {
            archiveCommand.execute(storageStub, bufferStub, taskListStub, null);
            assertEquals(1, 0);
        } catch (DuckException e) {
            assertEquals("-1 is not a valid task number.", e.getMessage());
        }
    }

    @Test
    void execute_archiveFailed_exceptionThrown() {
        storageStub.setWillThrowArchiveException(true);
        TaskListStub taskListStub = new TaskListStub(5);
        ArrayList<Integer> indexesToArchive = new ArrayList<>(Arrays.asList(1, 3));
        ArchiveCommand archiveCommand = new ArchiveCommand(indexesToArchive);

        try {
            archiveCommand.execute(storageStub, bufferStub, taskListStub, null);
            assertEquals(1, 0);
        } catch (DuckException e) {
            assertEquals("I was unable to save your task(s) to the archive location. "
                    + "Your task(s) will not be deleted.", e.getMessage());
        }
    }

    @Test
    void execute_storageErrorPrinted() {
        storageStub.setWillThrowStorageException(true);
        TaskListStub taskListStub = new TaskListStub(5);
        ArrayList<Integer> indexesToArchive = new ArrayList<>(Arrays.asList(1, 3));
        ArchiveCommand archiveCommand = new ArchiveCommand(indexesToArchive);

        try {
            archiveCommand.execute(storageStub, bufferStub, taskListStub, null);
            assertEquals("I saved the following tasks to the archive file:#  X task1#"
                    + "  X task3##Sorry! I was unable to save this update "
                    + "in storage. I'll try again next time.#", bufferStub.getOutputString());
        } catch (DuckException e) {
            assertEquals(1, 0);
        }
    }

    @Test
    void executeArchiveAll_taskListEmpty_exceptionThrown() {
        TaskListStub taskListStub = new TaskListStub(0);
        ArchiveAllCommand archiveCommand = new ArchiveAllCommand();

        try {
            archiveCommand.execute(storageStub, bufferStub, taskListStub, null);
            assertEquals("You have no tasks in your task list right now.#", bufferStub.getOutputString());
        } catch (DuckException e) {
            assertEquals(1, 0);
        }
    }
}