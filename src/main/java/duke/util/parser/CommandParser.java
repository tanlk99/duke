package duke.util.parser;

import duke.command.Command;
import duke.exception.DukeException;

interface CommandParser<T extends Command> {
    /**
     * Parses a command of a certain type.
     *
     * @param rawInput raw input passed to Duke
     * @return an Command instance to execute the command
     * @throws DukeException if input format is invalid
     */
    T parseCommand(String rawInput) throws DukeException;
}
