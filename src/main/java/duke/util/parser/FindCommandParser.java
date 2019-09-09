package duke.util.parser;

import duke.command.FindCommand;
import duke.exception.DukeException;

class FindCommandParser {
    private static final String FIND_COMMAND_EMPTY = "Your search string cannot be empty. To see all "
            + "tasks, use \"list\" instead.";

    /**
     * Parses a "find" command.
     *
     * @param rawInput  raw input passed to Duke
     * @return a FindCommand instance to execute the command
     * @throws DukeException  if input format is invalid
     */
    FindCommand parseCommand(String rawInput) throws DukeException {
        if (!rawInput.contains(" ")) {
            throw new DukeException(FIND_COMMAND_EMPTY);
        }

        return new FindCommand(rawInput.split(" ", 2)[1].trim());
    }
}
