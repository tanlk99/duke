import java.util.ArrayList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.lang.RuntimeException;

public class Storage {
    String cacheAddr;
    ArrayList<Task> taskList;

    public Storage(String cacheAddr) {
        this.cacheAddr = cacheAddr;
        taskList = new ArrayList<Task>();
        initializeCache();
        readCache();
    }

    private void initializeCache() {
        try {
            File file = new File(cacheAddr);
            if (!file.exists()) {
                file.createNewFile();
                ArrayList<Task> emptyList = new ArrayList<Task>();

                FileOutputStream fileOut = new FileOutputStream(cacheAddr, false);
                ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

                objOut.writeObject(emptyList);

                objOut.close();
                fileOut.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not load cache file.");
        }
    }

    private void readCache() {
        try {
            FileInputStream fileIn = new FileInputStream(cacheAddr);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);

            taskList = (ArrayList<Task>)objIn.readObject();

            objIn.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e);
            throw new RuntimeException("Could not load cache file.");
        }
    }

    public void writeCache() {
        try {
            FileOutputStream fileOut = new FileOutputStream(cacheAddr, false);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

            objOut.writeObject((ArrayList<Task>)taskList);

            objOut.close();
            fileOut.close();
        } catch (IOException e) {
            throw new RuntimeException("Could not write to cache file.");
        }
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }
}
