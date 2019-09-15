package duck.util.parser;

import duck.command.DoneCommand;
import duck.exception.DuckException;

class DoneCommandParser implements CommandParser<DoneCommand> {
    private static final String DONE_COMMAND_HELP = "Please use 'done i' to mark completion "
            + "of the i-th task in the list.";

    /**
     * Parses a "done" command.
     *
     * @param rawInput Raw input passed to Duck with whitespaces trimmed
     * @return A DoneCommand instance to execute the command
     * @throws DuckException  If input format is invalid
     */
    public DoneCommand parseCommand(String rawInput) throws DuckException {
        try {
            int index = Integer.parseInt(rawInput.split(" ", 2)[1].trim());
            return new DoneCommand(index);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new DuckException(DONE_COMMAND_HELP);
        }
    }
}
