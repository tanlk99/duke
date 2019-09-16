package duck.command;

import duck.exception.DuckException;
import duck.util.Buffer;
import duck.util.Storage;
import duck.util.TaskList;
import duck.util.ConfigLoader;

public class SetConfigCommand extends Command {
    private static final String SET_CONFIG_SUCCESS_1 = "Your configuration settings have been updated.";
    private static final String SET_CONFIG_SUCCESS_2 = "  %1$s: %2$s";
    private static final String SET_CONFIG_SUCCESS_3 = "Please restart Duck to apply changes.";

    String configName;
    String configValue;

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
     * @param storage     A {@link Storage} object to cache task list
     * @param buffer      A {@link Buffer} object to buffer Duck's output
     * @param taskList    A {@link TaskList} object which stores the task list
     * @param configLoader  A {@link ConfigLoader} object to write changes to configuration
     */
    public void execute(Storage storage, Buffer buffer, TaskList taskList,
                        ConfigLoader configLoader) throws DuckException {
        configLoader.updateConfig(configName, configValue);
        buffer.formatLine(SET_CONFIG_SUCCESS_1);
        buffer.formatLine(String.format(SET_CONFIG_SUCCESS_2, configName, configValue));
        buffer.formatLine(SET_CONFIG_SUCCESS_3);
    }
}
