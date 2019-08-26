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
     * @param   cacheAddr   relative path to cache file
     */
    public Storage(String cacheAddr) throws DukeException {
        this.cacheAddr = cacheAddr;
        createCacheIfNotExists();
    }

    /**
     * Creates and initializes the cache file if it does not exist.
     * Throws a DukeException if the creation or initialization failed.
     */
    private void createCacheIfNotExists() throws DukeException {
        try {
            File file = new File(cacheAddr);

            if (!file.exists()) {
                file.createNewFile();
                ArrayList<Task> emptyList = new ArrayList<>();

                FileOutputStream fileOut = new FileOutputStream(cacheAddr, false);
                ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

                objOut.writeObject(emptyList);

                objOut.close();
                fileOut.close();
            }
        } catch (IOException e) {
            throw new DukeException("Could not load/create cache file.");
        }
    }

    /**
     * Retrieves the saved task list from the cache file.
     * If unable to read or the cache file is corrupted, throws a DukeException.
     *
     * @return  an ArrayList containing the retrieved task list
     */
    public ArrayList<Task> readCache() throws DukeException {
        ArrayList<Task> result;

        try {
            FileInputStream fileIn = new FileInputStream(cacheAddr);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);

            ArrayList<Task> taskArrayList = (ArrayList<Task>) objIn.readObject();

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
     * If unable to write, throws a DukeException.
     *
     * @param taskList      the TaskList to write
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
