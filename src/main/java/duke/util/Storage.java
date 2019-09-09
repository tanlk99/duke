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
    private String cacheAddr;
    private String archiveAddr;

    /**
     * Creates a Storage object.
     *
     * @param   cacheAddr   Relative path to cache file
     */
    public Storage(String cacheAddr) {
        this.cacheAddr = cacheAddr;
        this.archiveAddr = "archive/duke-archive.txt";
    }

    /**
     * Creates and initializes the cache file if it does not exist.
     *
     * @throws  DukeException  If creation or initialization of the cache file failed.
     */
    public void initializeCacheIfNotExists() throws DukeException {
        try {
            boolean isFileExists = createFileIfNotExists(cacheAddr);
            if (!isFileExists) {
                initializeNewCache(cacheAddr);
            }
        } catch (IOException e) {
            throw new DukeException("Could not load/create cache file.");
        }
    }

    /**
     * Initializes a newly created cache file with an empty task list.
     *
     * @param filePath  path of cache file to initialize
     * @throws IOException  if initalization of file failed
     */
    private void initializeNewCache(String filePath) throws IOException {
        File file = new File(filePath);
        List<Task> emptyList = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(file, emptyList);
    }

    /**
     * Creates an empty file at the specified location if it does not exist.
     *
     * @param filePath    path of file to create
     * @return true if file already exists
     * @throws IOException   if file creation failed
     */
    private boolean createFileIfNotExists(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            return true;
        }

        String dirPath = getDirectoryPath(filePath);
        createDirectoryIfNotExists(dirPath);
        file.createNewFile();
        return false;
    }

    /**
     * Creates a directory (and all parent directories) if it does not exist.
     *
     * @param dirPath  path of directory
     */
    private void createDirectoryIfNotExists(String dirPath) {
        File dir = new File(dirPath);
        if (dir.exists()) {
            return;
        }

        dir.mkdirs();
    }

    /**
     * Gets the directory location of a file path.
     *
     * @param filePath  path of file
     * @return  parent directory of file
     */
    String getDirectoryPath(String filePath) {
        int lastSlash = filePath.lastIndexOf("/");
        if (lastSlash == -1) {
            return ".";
        }

        return filePath.substring(0, lastSlash);
    }

    /**
     * Retrieves the saved task list from the cache file.
     *
     * @return  An ArrayList containing the retrieved task list
     * @throws  DukeException  If unable to read from cache file or the cache file is corrupted
     */
    public ArrayList<Task> readCache() throws DukeException {
        ArrayList<Task> result;

        try {
            File file = new File(cacheAddr);
            ObjectMapper objectMapper = new ObjectMapper();
            result = objectMapper.readValue(file, new TypeReference<ArrayList<Task>>(){});
        } catch (IOException e) {
            throw new DukeException("Could not load cache file.");
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
            File file = new File(cacheAddr);
            ObjectMapper objectMapper = new ObjectMapper();

            objectMapper.writerFor(new TypeReference<ArrayList<Task>>(){})
                    .withDefaultPrettyPrinter()
                    .writeValue(file, taskList.getFullTaskList());
        } catch (IOException e) {
            throw new DukeException("Could not write to cache file.");
        }
    }

    /**
     * Appends the contents of a task list to the archive file.
     *
     * @param taskList      TaskList to write to archive file
     * @throws DukeException    If unable to write to archive file
     */
    public void writeArchive(TaskList taskList) throws DukeException {
        try {
            createFileIfNotExists(archiveAddr);

            File file = new File(archiveAddr);
            FileWriter fileWriter = new FileWriter(file, true);

            ObjectMapper objectMapper = new ObjectMapper();
            SequenceWriter sequenceWriter = objectMapper.writerFor(Task.class)
                    .withDefaultPrettyPrinter()
                    .writeValues(fileWriter);

            for (Task task : taskList.getFullTaskList()) {
                System.out.println(task);
                sequenceWriter.write(task);
            }
            sequenceWriter.close();
        } catch (IOException e) {
            throw new DukeException("Could not write to archive file.");
        }
    }
}
