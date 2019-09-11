package duke.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import duke.task.Task;
import duke.exception.DukeException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;

/**
 * Handles I/O between Duke and an external cache file. Storage instances can load the
 * task list from the cache file, or write the task list to the cache file.
 */
public class Storage {
    private static final String CACHE_INITIALIZE_FAILED = "Could not initialize cache file.";
    private static final String CACHE_READ_FAILED = "Could not read from cache file.";
    private static final String CACHE_WRITE_FAILED = "Could not write to cache file.";
    private static final String ARCHIVE_WRITE_FAILED = "Could not write to archive file.";

    private String cachePath;
    private String archivePath;

    /**
     * Creates a Storage object.
     *
     * @param  cachePath    Relative path to cache file
     * @param  archiveAddr  Relative path to archive file
     */
    public Storage(String cachePath, String archiveAddr) {
        this.cachePath = cachePath;
        this.archivePath = archiveAddr;
    }

    /**
     * Creates and initializes the cache file if it does not exist.
     *
     * @throws  DukeException  If creation or initialization of the cache file failed.
     */
    public void initializeCacheIfNotExists() throws DukeException {
        if (FileLoader.isFilePresent(cachePath)) {
            return;
        }

        try {
            FileLoader.createFileIfNotExists(cachePath);
            initializeNewCache(cachePath);
        } catch (IOException e) {
            throw new DukeException(CACHE_INITIALIZE_FAILED);
        }
    }

    /**
     * Initializes a newly created cache file with an empty task list.
     *
     * @param filePath  Path to cache file to initialize
     * @throws IOException  If initalization of file failed
     */
    private void initializeNewCache(String filePath) throws IOException {
        File file = new File(filePath);
        List<Task> emptyList = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(file, emptyList);
    }

    /**
     * Retrieves the saved task list from the cache file.
     *
     * @return  ArrayList containing the retrieved task list
     * @throws  DukeException  If unable to read from cache file or the cache file is corrupted
     */
    public ArrayList<Task> readCache() throws DukeException {
        ArrayList<Task> result;

        try {
            File file = new File(cachePath);
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.readValue(file, new TypeReference<ArrayList<Task>>(){});
        } catch (IOException e) {
            throw new DukeException(CACHE_READ_FAILED);
        }

        return result;
    }

    /**
     * Writes the contents of a task list to the cache file.
     *
     * @param   taskList    TaskList to write to cache file
     * @throws  DukeException   If unable to write to cache file
     */
    public void writeCache(TaskList taskList) throws DukeException {
        try {
            File file = new File(cachePath);
            ObjectMapper objectMapper = new ObjectMapper();

            objectMapper.writerFor(new TypeReference<ArrayList<Task>>(){})
                    .withDefaultPrettyPrinter()
                    .writeValue(file, taskList.getAllTasks());
        } catch (IOException e) {
            throw new DukeException(CACHE_WRITE_FAILED);
        }
    }

    /**
     * Appends the contents of a list of tasks to the archive file.
     *
     * @param taskArrayList    List of task objects to write to archive file
     * @throws DukeException    If unable to write to archive file
     */
    public void writeArchive(ArrayList<Task> taskArrayList) throws DukeException {
        try {
            FileLoader.createFileIfNotExists(archivePath);

            File file = new File(archivePath);
            FileWriter fileWriter = new FileWriter(file, true);

            ObjectMapper objectMapper = new ObjectMapper();
            SequenceWriter sequenceWriter = objectMapper.writerFor(Task.class)
                    .withDefaultPrettyPrinter()
                    .writeValues(fileWriter);

            for (Task task : taskArrayList) {
                sequenceWriter.write(task);
            }
            sequenceWriter.close();
        } catch (IOException e) {
            throw new DukeException(ARCHIVE_WRITE_FAILED);
        }
    }
}
