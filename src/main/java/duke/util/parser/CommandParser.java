package duke.util.parser;

import duke.command.Command;
import duke.exception.DukeException;

interface CommandParser<T extends Command> {
    /**
     * Parses a command of a certain type.
     *
     * @param rawInput Raw input passed to Duke with whitespaces trimmed
     * @return A Command instance to execute the command
     * @throws DukeException If input format is invalid
     */
    T parseCommand(String rawInput) throws DukeException;
}
