package duke.util.parser;

import duke.command.Command;
import duke.command.ExitCommand;
import duke.command.ListCommand;
import duke.exception.DukeException;

/**
 * Parses Duke's commands.
 *
 * <p>Interprets and converts raw input from the command line to instances of
 * {@link Command} to be executed by Duke.</p>
 */
public class InputParser {
    private static final String INVALID_COMMAND = "I don't understand that command.";

    /**
     * Interprets a command input string to create a {@link Command} object.
     * Leading and trailing spaces are ignored. Below is a table of all accepted commands:
     *
     * <table border="1">
     *   <tr><td>Command</td><td>Function</td></tr>
     *   <tr>
     *     <td>todo <i>name</i></td>
     *     <td>Add a new to-do with description <i>name</i> to the task list.</td>
     *   </tr>
     *   <tr>
     *     <td>deadline <i>name</i> /by <i>time</i></td>
     *     <td>Add a new deadline with description <i>name</i> and due date <i>time</i> to the task list.</td>
     *   </tr>
     *   <tr>
     *     <td>event <i>name</i> /at <i>time</i></td>
     *     <td>Add a new event with description <i>name</i> which occurs at time <i>time</i> to the task list.</td>
     *   </tr>
     *   <tr>
     *     <td>list</td>
     *     <td>List all the events in order indicating their completion status, type, and time (if applicable).</td>
     *   </tr>
     *   <tr>
     *     <td>done <i>index</i></td>
     *     <td>Mark the <i>index</i>-th task in the task list as complete (<i>index</i> must be a valid integer).</td>
     *   </tr>
     *   <tr>
     *     <td>delete <i>index</i></td>
     *     <td>Delete the <i>index</i>-th task in the task list (<i>index</i> must be a valid integer).</td>
     *   </tr>
     *   <tr>
     *     <td>find <i>substring</i></td>
     *     <td>Finds and lists all tasks with description containing <i>substring</i>.</td>
     *   <tr>
     *     <td>bye</td>
     *     <td>Exit Duke.</td>
     *   </tr>
     * </table>
     *
     * @param   untrimmedRawInput    Raw input passed into command line
     * @return  A {@link Command} object representing the command
     * @throws  DukeException   If input is invalid (see above)
     */
    public Command parseInput(String untrimmedRawInput) throws DukeException {
        String rawInput = untrimmedRawInput.trim();
        String commandPhrase = rawInput.split(" ", 2)[0];

        switch (commandPhrase) {
        case "bye":
            return new ExitCommand();
        case "list":
            return new ListCommand();
        case "done":
            return new DoneCommandParser().parseCommand(rawInput);
        case "delete":
            return new DeleteCommandParser().parseCommand(rawInput);
        case "find":
            return new FindCommandParser().parseCommand(rawInput);
        case "todo": //Fallthrough
        case "event": //Fallthrough
        case "deadline":
            return new AddCommandParser().parseCommand(rawInput);
        default:
            throw new DukeException(INVALID_COMMAND);
        }
    }
}
