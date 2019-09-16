package duck.util.parser;

import duck.command.SetConfigCommand;
import duck.exception.DuckException;

/**
 * Parses a SetConfigCommand.
 */
public class SetConfigCommandParser implements CommandParser<SetConfigCommand> {
    private static final String CONFIG_INVALID_PARAMETER = "That is not a valid parameter to set.";
    private static final String[] ACCEPTED_PARAMETER_NAMES = {"cachePath", "archivePath"};

    /**
     * Parses a "set" command.
     *
     * @param rawInput Raw input passed to Duck with whitespaces trimmed
     * @return A Command instance to execute the command
     * @throws DuckException If input format is invalid
     */
    public SetConfigCommand parseCommand(String rawInput) throws DuckException {
        if (rawInput.split("\\s+", 3).length < 3) {
            throw new DuckException(CONFIG_INVALID_PARAMETER);
        }

        String name = rawInput.split("\\s+", 3)[1].trim();
        String value = rawInput.split("\\s+", 3)[2].trim();

        for (String acceptedName : ACCEPTED_PARAMETER_NAMES) {
            if (name.equals(acceptedName)) {
                return new SetConfigCommand(name, value);
            }
        }

        throw new DuckException(CONFIG_INVALID_PARAMETER);
    }
}
