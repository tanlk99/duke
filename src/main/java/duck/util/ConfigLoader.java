package duck.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import duck.exception.DuckException;

public class ConfigLoader {
    private static final String DEFAULT_CACHE_PATH = "data/duck-cache.txt";
    private static final String DEFAULT_ARCHIVE_PATH = "archive/duck-archive.txt";

    private static final String CONFIG_INITIALIZE_FAILED = "Could not initialize config file.";
    private static final String CONFIG_READ_FAILED = "Could not read config file.";
    private static final String CONFIG_WRITE_FAILED = "Could not write config file.";

    private String configPath;
    private Map<String, String> config = new HashMap<>();

    /**
     * Creates a new ConfigLoader.
     *
     * @param configPath Relative path to config file
     */
    public ConfigLoader(String configPath) {
        this.configPath = configPath;
        initializeDefaultConfig();
    }

    /**
     * Writes Duck's default configuration.
     */
    private void initializeDefaultConfig() {
        config.put("cachePath", DEFAULT_CACHE_PATH);
        config.put("archivePath", DEFAULT_ARCHIVE_PATH);
    }

    /**
     * Loads Duck's configurations from the config file.
     * If the config file does not exist, creates a new config file and saves the default config.
     *
     * @throws DuckException If load failed
     */
    public void loadConfig() throws DuckException {
        if (FileLoader.isFilePresent(configPath)) {
            readConfig();
            return;
        }

        try {
            FileLoader.createFileIfNotExists(configPath);
            initializeDefaultConfig(); //set to default
            writeConfig();
        } catch (IOException | DuckException e) {
            throw new DuckException(CONFIG_INITIALIZE_FAILED);
        }
    }

    /**
     * Reads Duck's configuration from the config file.
     *
     * @throws DuckException If read failed
     */
    private void readConfig() throws DuckException {
        try {
            File file = new File(configPath);
            ObjectMapper mapper = new ObjectMapper();
            config = mapper.readValue(file, new TypeReference<HashMap<String, String>>() {});
        } catch (IOException e) {
            throw new DuckException(CONFIG_READ_FAILED);
        }
    }

    /**
     * Writes Duck's configuration to the config file.
     *
     * @throws DuckException If write failed
     */
    private void writeConfig() throws DuckException {
        try {
            File file = new File(configPath);
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerFor(new TypeReference<HashMap<String, String>>() {})
                    .withDefaultPrettyPrinter().writeValue(file, config);
        } catch (IOException e) {
            throw new DuckException(CONFIG_WRITE_FAILED);
        }
    }

    /**
     * Sets the cache path and saves the new path in the config file.
     *
     * @param name  Name of parameter to update
     * @param value Value of parameter to update
     * @throws DuckException If write failed
     */
    public void updateConfig(String name, String value) throws DuckException {
        config.put(name, value);
        writeConfig();
    }

    /**
     * Gets the cache path.
     *
     * @param name  Name of parameter to get
     * @return Value of parameter
     */
    public String getConfig(String name) {
        return config.get(name);
    }
}
