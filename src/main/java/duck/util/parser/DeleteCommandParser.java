package duck.util.parser;

import duck.command.DeleteCommand;
import duck.exception.DuckException;

class DeleteCommandParser implements CommandParser<DeleteCommand> {
    private static final String DELETE_COMMAND_HELP = "Please use 'delete i' to delete the i-th task in the list.";

    /**
     * Parses a "delete" command.
     *
     * @param rawInput Raw input passed to Duck with whitespaces trimmed
     * @return A DeleteCommand instance to execute the command
     * @throws DuckException  If input format is invalid
     */
    public DeleteCommand parseCommand(String rawInput) throws DuckException {
        try {
            int index = Integer.parseInt(rawInput.split(" ", 2)[1].trim());
            return new DeleteCommand(index);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new DuckException(DELETE_COMMAND_HELP);
        }
    }
}
