import java.util.ArrayList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.lang.ClassNotFoundException;

public class Storage {
    private String cacheAddr;

    public Storage(String cacheAddr) {
        this.cacheAddr = cacheAddr;
        initializeCache();
    }

    private void initializeCache() {
        try {
            File file = new File(cacheAddr);
            System.out.println(file.getAbsolutePath());
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("File created!");
                ArrayList<Task> emptyList = new ArrayList<>();

                FileOutputStream fileOut = new FileOutputStream(cacheAddr, false);
                ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

                objOut.writeObject(emptyList);

                objOut.close();
                fileOut.close();
            }
        } catch (IOException e) {
            throw new DukeException("Could not load cache file.");
        }
    }

    public ArrayList<Task> readCache() {
        try {
            FileInputStream fileIn = new FileInputStream(cacheAddr);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);

            ArrayList<Task> taskArrayList = (ArrayList<Task>)objIn.readObject();

            objIn.close();
            fileIn.close();

            return taskArrayList;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
            throw new DukeException("Could not load cache file.");
        }
    }

    /**
     * Writes the contents of a task list to the cache file.
     *
     * @param taskList      the TaskList to write
     */
    public void writeCache(TaskList taskList) {
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
