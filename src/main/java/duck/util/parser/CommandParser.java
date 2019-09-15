package duck.util.parser;

import duck.command.Command;
import duck.exception.DuckException;

interface CommandParser<T extends Command> {
    /**
     * Parses a command of a certain type.
     *
     * @param rawInput Raw input passed to Duck with whitespaces trimmed
     * @return A Command instance to execute the command
     * @throws DuckException If input format is invalid
     */
    T parseCommand(String rawInput) throws DuckException;
}
