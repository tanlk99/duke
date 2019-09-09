package duke.util.parser;

import duke.command.AddCommand;
import duke.exception.DukeException;

public class AddCommandParser {
    private static final String ADD_COMMAND_EMPTY_DESCRIPTION = "The description of %1$s %2$s cannot be empty.";

    /**
     * Parses a command to add a new task.
     *
     * @param rawInput raw input passed to Duke
     * @return an AddCommand instance to execute the command
     * @throws DukeException if input format is invalid
     */
    public AddCommand parseCommand(String rawInput) throws DukeException {
        String commandPhrase = rawInput.split(" ", 2)[0];

        if (!rawInput.contains(" ")) {
            throw new DukeException(String.format(ADD_COMMAND_EMPTY_DESCRIPTION,
                    ParserUtil.getArticle(commandPhrase),
                    commandPhrase));
        }

        String rawTaskDescription = rawInput.split(" ", 2)[1];
        return new AddCommand(new TaskParser().parseTask(rawTaskDescription, commandPhrase));
    }
}
