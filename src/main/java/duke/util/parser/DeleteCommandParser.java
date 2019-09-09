package duke.util.parser;

import duke.command.DeleteCommand;
import duke.exception.DukeException;

public class DeleteCommandParser {
    private static final String DELETE_COMMAND_HELP = "Please use 'delete i' to delete the i-th task in the list.";

    /**
     * Parses a "delete" command.
     *
     * @param rawInput  raw input passed to Duke
     * @return a DeleteCommand instance to execute the command
     * @throws DukeException  if input format is invalid
     */
    public DeleteCommand parseCommand(String rawInput) throws DukeException {
        try {
            int index = Integer.parseInt(rawInput.split(" ", 2)[1].trim());
            return new DeleteCommand(index);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new DukeException(DELETE_COMMAND_HELP);
        }
    }
}