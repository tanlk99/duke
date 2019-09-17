package duck.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import duck.exception.DuckException;
import duck.task.Task;

/**
 * Handles I/O between Duck and an external cache file. StorageHandler instances can load a
 * task list from the cache file, or write a task list to the cache file.
 */
public class StorageHandler {
    private static final String CACHE_INITIALIZE_FAILED = "Could not initialize cache file.";
    private static final String CACHE_READ_FAILED = "Could not read from cache file.";
    private static final String CACHE_WRITE_FAILED = "Could not write to cache file.";

    private String cachePath;

    /**
     * Creates a StorageHandler object.
     *
     * @param  cachePath    Relative path to cache file
     */
    public StorageHandler(String cachePath) {
        this.cachePath = cachePath;
    }

    /**
     * Creates and initializes the cache file if it does not exist.
     *
     * @throws  DuckException  If creation or initialization of the cache file failed.
     */
    private void initializeCacheIfNotExists() throws DuckException {
        if (FileLoader.isFilePresent(cachePath)) {
            return;
        }

        try {
            FileLoader.createFileIfNotExists(cachePath);
            initializeNewCache(cachePath);
        } catch (IOException e) {
            throw new DuckException(CACHE_INITIALIZE_FAILED);
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
     * @throws  DuckException  If unable to read from cache file or the cache file is corrupted
     */
    public ArrayList<Task> readCache() throws DuckException {
        initializeCacheIfNotExists();
        ArrayList<Task> result;

        try {
            File file = new File(cachePath);
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.readValue(file, new TypeReference<ArrayList<Task>>(){});
        } catch (IOException e) {
            throw new DuckException(CACHE_READ_FAILED);
        }

        return result;
    }

    /**
     * Writes the contents of a task list to the cache file.
     *
     * @param   taskList    TaskList to write to cache file
     * @throws  DuckException   If unable to write to cache file
     */
    public void writeCache(TaskList taskList) throws DuckException {
        writeCache(taskList.getAllTasks());
    }

    /**
     * Writes the contents of an ArrayList of tasks to the cache file.
     *
     * @param   taskArrayList   An ArrayList of tasks to write to cache file
     * @throws  DuckException   If unable to write to cache file
     */
    public void writeCache(ArrayList<Task> taskArrayList) throws DuckException {
        initializeCacheIfNotExists();

        try {
            File file = new File(cachePath);
            ObjectMapper objectMapper = new ObjectMapper();

            objectMapper.writerFor(new TypeReference<ArrayList<Task>>(){})
                    .withDefaultPrettyPrinter()
                    .writeValue(file, taskArrayList);
        } catch (IOException e) {
            throw new DuckException(CACHE_WRITE_FAILED);
        }
    }

    /**
     * Appends the contents of an ArrayList of tasks to the cache file.
     *
     * @param   toAppend    An ArrayList of tasks to append to cache file
     * @throws  DuckException   If unable to write to cache file
     */
    public void appendCache(ArrayList<Task> toAppend) throws DuckException {
        initializeCacheIfNotExists();
        ArrayList<Task> taskArrayList = readCache();
        taskArrayList.addAll(toAppend);

        try {
            File file = new File(cachePath);
            ObjectMapper objectMapper = new ObjectMapper();

            objectMapper.writerFor(new TypeReference<ArrayList<Task>>(){})
                    .withDefaultPrettyPrinter()
                    .writeValue(file, taskArrayList);
        } catch (IOException e) {
            throw new DuckException(CACHE_WRITE_FAILED);
        }
    }
}
