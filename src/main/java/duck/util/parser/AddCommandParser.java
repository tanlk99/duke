package duck.util.parser;

import duck.command.AddCommand;
import duck.exception.DuckException;

class AddCommandParser implements CommandParser<AddCommand> {
    private static final String ADD_COMMAND_EMPTY_DESCRIPTION = "The description of %1$s %2$s cannot be empty.";

    /**
     * Parses a command to add a new task.
     *
     * @param rawInput Raw input passed to Duck with whitespaces trimmed
     * @return An AddCommand instance to execute the command
     * @throws DuckException If input format is invalid
     */
    public AddCommand parseCommand(String rawInput) throws DuckException {
        String commandPhrase = rawInput.split(" ", 2)[0];

        if (!rawInput.contains(" ")) {
            throw new DuckException(String.format(ADD_COMMAND_EMPTY_DESCRIPTION,
                    ParserUtil.getArticle(commandPhrase),
                    commandPhrase));
        }

        String rawTaskDescription = rawInput.split(" ", 2)[1];
        return new AddCommand(new TaskParser().parseTask(rawTaskDescription, commandPhrase));
    }
}
