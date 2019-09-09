package duke.util.parser;

import duke.command.DoneCommand;
import duke.exception.DukeException;

class DoneCommandParser {
    private static final String DONE_COMMAND_HELP = "Please use 'done i' to mark completion "
            + "of the i-th task in the list.";

    /**
     * Parses a "done" command.
     *
     * @param rawInput  raw input passed to Duke
     * @return a DoneCommand instance to execute the command
     * @throws DukeException  if input format is invalid
     */
    DoneCommand parseCommand(String rawInput) throws DukeException {
        try {
            int index = Integer.parseInt(rawInput.split(" ", 2)[1].trim());
            return new DoneCommand(index);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new DukeException(DONE_COMMAND_HELP);
        }
    }
}
