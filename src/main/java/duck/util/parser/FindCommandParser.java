package duck.util.parser;

import duck.command.FindCommand;
import duck.exception.DuckException;

/**
 * Parses a FindCommand.
 */
class FindCommandParser implements CommandParser<FindCommand> {
    private static final String FIND_COMMAND_EMPTY = "Your search string cannot be empty. To see all "
            + "tasks, use \"list\" instead.";

    /**
     * Parses a "find" command.
     *
     * @param rawInput Raw input passed to Duck with whitespaces trimmed
     * @return A FindCommand instance to execute the command
     * @throws DuckException  If input format is invalid
     */
    public FindCommand parseCommand(String rawInput) throws DuckException {
        if (!rawInput.contains(" ")) {
            throw new DuckException(FIND_COMMAND_EMPTY);
        }

        return new FindCommand(rawInput.split(" ", 2)[1].trim());
    }
}
