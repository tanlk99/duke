package duck.util;

import java.io.File;
import java.io.IOException;

/**
 * Handles the creation of new files used by Duck.
 */
public class FileLoader {
    /**
     * Checks if a file at a given path exists.
     *
     * @param filePath Relative path to file to check
     * @return True if file already exists
     */
    public static boolean isFilePresent(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * Creates an empty file at the specified location if it does not exist.
     *
     * @param filePath      Relative path to file to create
     * @throws IOException  If file creation failed
     */
    public static void createFileIfNotExists(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            return;
        }

        String dirPath = getDirectoryPath(filePath);
        createDirectoryIfNotExists(dirPath);
        file.createNewFile();
    }

    /**
     * Creates a directory (and all parent directories) if it does not exist.
     *
     * @param dirPath  Relative path to directory
     */
    private static void createDirectoryIfNotExists(String dirPath) {
        File dir = new File(dirPath);
        if (dir.exists()) {
            return;
        }

        dir.mkdirs();
    }

    /**
     * Gets the directory location of a file path.
     *
     * @param filePath  Relative path to the file
     * @return  Parent directory of the file
     */
    static String getDirectoryPath(String filePath) {
        int lastSlash = filePath.lastIndexOf("/");
        if (lastSlash == -1) {
            return ".";
        }

        return filePath.substring(0, lastSlash);
    }
}
