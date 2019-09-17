package duck.command;

import duck.exception.DuckException;
import duck.util.Buffer;
import duck.util.StorageHandler;
import duck.util.ConfigLoader;
import duck.util.TaskList;

public class SetConfigCommand extends Command {
    private static final String SET_CONFIG_SUCCESS_1 = "Your configuration settings have been updated.";
    private static final String SET_CONFIG_SUCCESS_2 = "  %1$s: %2$s";
    private static final String SET_CONFIG_SUCCESS_3 = "Please restart Duck to apply changes.";

    private String configName;
    private String configValue;

    /**
     * Creates a new FindCommand.
     *
     * @param configName  Name of parameter to update
     * @param configValue Value of parameter to update
     */
    public SetConfigCommand(String configName, String configValue) {
        this.configName = configName;
        this.configValue = configValue;
    }

    /**
     * Updates a configuration parameter to a new value.
     *
     * @param cacheHandler     A {@link StorageHandler} object to cache task list
     * @param archiveHandler   A {@link StorageHandler} object to archive tasks
     * @param buffer           A {@link Buffer} object to buffer Duck's output
     * @param taskList         A {@link TaskList} object which stores the task list
     * @param configLoader     A {@link ConfigLoader} object to write changes to configuration
     */
    public void execute(StorageHandler cacheHandler, StorageHandler archiveHandler, Buffer buffer, TaskList taskList,
                        ConfigLoader configLoader) throws DuckException {
        configLoader.updateConfig(configName, configValue);
        buffer.formatLine(SET_CONFIG_SUCCESS_1);
        buffer.formatLine(String.format(SET_CONFIG_SUCCESS_2, configName, configValue));
        buffer.formatLine(SET_CONFIG_SUCCESS_3);
    }
}
