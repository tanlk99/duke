package duke.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import duke.task.Task;
import duke.exception.DukeException;

/**
 * Handles I/O between Duke and an external cache file. Storage instances can load the
 * task list from the cache file, or write the task list to the cache file.
 */
public class Storage {
    private String cacheAddr;

    /**
     * Creates a Storage object.
     *
     * @param   cacheAddr   Relative path to cache file
     */
    public Storage(String cacheAddr) {
        this.cacheAddr = cacheAddr;
    }

    /**
     * Creates and initializes the cache file if it does not exist.
     *
     * @throws  DukeException  If creation or initialization of the cache file failed.
     */
    public void initializeCacheIfNotExists() throws DukeException {
        try {
            File file = new File(cacheAddr);
            if (file.exists()) {
                return;
            }

            String dirPath = getDirectoryPath(cacheAddr);
            createDirectoryIfNotExists(dirPath);
            file.createNewFile();
            initializeNewCache();
        } catch (IOException e) {
            throw new DukeException("Could not load/create cache file.");
        }
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
     * Initializes a newly created cache file with an empty task list.
     *
     * @throws IOException  if initalization of file failed
     */
    private void initializeNewCache() throws IOException {
        ArrayList<Task> emptyList = new ArrayList<>();

        FileOutputStream fileOut = new FileOutputStream(cacheAddr, false);
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

        objOut.writeObject(emptyList);

        objOut.close();
        fileOut.close();
    }

    /**
     * Gets the directory location of a file path.
     *
     *
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
            FileInputStream fileIn = new FileInputStream(cacheAddr);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);

            ArrayList<Task> taskArrayList = (ArrayList<Task>)objIn.readObject();

            objIn.close();
            fileIn.close();

            result = taskArrayList;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
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
            FileOutputStream fileOut = new FileOutputStream(cacheAddr, false);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

            objOut.writeObject(taskList.getFullTaskList());

            objOut.close();
            fileOut.close();
        } catch (IOException e) {
            throw new DukeException("Could not write to cache file.");
        }
    }
}
