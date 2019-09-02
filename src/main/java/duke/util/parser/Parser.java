package duke.util.parser;

import duke.command.AddCommand;
import duke.command.Command;
import duke.command.DeleteCommand;
import duke.command.DoneCommand;
import duke.command.ExitCommand;
import duke.command.FindCommand;
import duke.command.ListCommand;
import duke.exception.DukeException;

/**
 * Parses Duke's commands.
 *
 * <p>Interprets and converts raw input from the command line to instances of
 * {@link Command} to be executed by Duke.</p>
 */
public class Parser {
    private TaskParser taskParser;

    /**
     * Returns true if <i>string</i> begins with a vowel (i.e. 'a', 'e', 'i', 'o', or 'u').
     *
     * @param string    the String to check
     * @return  true if string begins with a vowel
     */
    static boolean isBeginWithVowel(String string) {
        if (string.length() == 0) {
            return false;
        }

        char[] vowels = {'a', 'e', 'i', 'o', 'u'};

        for (char vowel : vowels) {
            if (string.charAt(0) == vowel) {
                return true;
            }
        }

        return false;
    }

    /**
     * Creates a new Parser object.
     */
    public Parser() {
        taskParser = new TaskParser();
    }

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
        int index;

        switch (commandPhrase) {
        case "bye":
            return new ExitCommand();
        case "list":
            return new ListCommand();
        case "done":
            try {
                index = Integer.parseInt(rawInput.split(" ", 2)[1].trim());
                return new DoneCommand(index);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                throw new DukeException("Please use 'done i' to mark completion of the i-th task in the list.");
            }
        case "delete":
            try {
                index = Integer.parseInt(rawInput.split(" ", 2)[1].trim());
                return new DeleteCommand(index);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                throw new DukeException("Please use 'delete i' to delete the i-th task in the list.");
            }
        case "find":
            if (!rawInput.contains(" ")) {
                throw new DukeException("Your search string cannot be empty. To see all tasks, use \"list\" instead.");
            }

            return new FindCommand(rawInput.split(" ", 2)[1].trim());
        case "todo": //Fallthrough
        case "event": //Fallthrough
        case "deadline":
            if (!rawInput.contains(" ")) {
                throw new DukeException("The description of " + (isBeginWithVowel(commandPhrase) ? "an " : "a ")
                        + commandPhrase + " cannot be empty.");
            }
            String rawTaskDescription = rawInput.split(" ", 2)[1];
            return new AddCommand(taskParser.parseTask(rawTaskDescription, commandPhrase));
        default:
            throw new DukeException("I don't understand that command.");
        }
    }
}
