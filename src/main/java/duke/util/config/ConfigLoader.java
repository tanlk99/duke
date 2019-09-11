package duke.util.config;

import java.io.File;
import java.io.IOException;
import duke.util.FileLoader;
import duke.exception.DukeException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfigLoader {
    private static final String CONFIG_INITIALIZE_FAILED = "Could not initialize config file.";
    private static final String CONFIG_READ_FAILED = "Could not read config file.";
    private static final String CONFIG_WRITE_FAILED = "Could not write config file.";

    private String configPath;
    private Config config;

    /**
     * Creates a new ConfigLoader.
     *
     * @param configPath Relative path to config file
     */
    public ConfigLoader(String configPath) {
        config = Config.getDefaultConfig();
        this.configPath = configPath;
    }

    /**
     * Loads Duke's configurations from the config file.
     * If the config file does not exist, creates a new config file and saves the default config.
     *
     * @throws DukeException If load failed
     */
    public void loadConfig() throws DukeException {
        if (FileLoader.isFilePresent(configPath)) {
            readConfig();
            return;
        }

        try {
            FileLoader.createFileIfNotExists(configPath);
            config = Config.getDefaultConfig(); //set to default
            writeConfig();
        } catch (IOException | DukeException e) {
            throw new DukeException(CONFIG_INITIALIZE_FAILED);
        }
    }

    /**
     * Reads a Config object from the config file.
     *
     * @throws DukeException If read failed
     */
    private void readConfig() throws DukeException {
        try {
            File file = new File(configPath);
            ObjectMapper mapper = new ObjectMapper();
            config = mapper.readValue(file, Config.class);
        } catch (IOException e) {
            throw new DukeException(CONFIG_READ_FAILED);
        }
    }

    /**
     * Writes the Config object to the config file.
     *
     * @throws DukeException If write failed
     */
    private void writeConfig() throws DukeException {
        try {
            File file = new File(configPath);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, config);
        } catch (IOException e) {
            throw new DukeException(CONFIG_WRITE_FAILED);
        }
    }

    /**
     * Sets the cache path and saves the new path in the config file.
     *
     * @param cachePath Relative path to cache file
     * @throws DukeException If write failed
     */
    public void saveCachePath(String cachePath) throws DukeException {
        config.setCachePath(cachePath);
        writeConfig();
    }

    /**
     * Sets the archive path and saves the new path in the config file.
     *
     * @param archivePath Relative path to archive file
     * @throws DukeException If write failed
     */
    public void saveArchivePath(String archivePath) throws DukeException {
        config.setArchivePath(archivePath);
        writeConfig();
    }

    /**
     * Gets the cache path.
     *
     * @return Relative path to config file
     */
    public String getCachePath() {
        return config.getCachePath();
    }

    /**
     * Gets the archive path.
     *
     * @return Relative path to archive file
     */
    public String getArchivePath() {
        return config.getArchivePath();
    }
}
