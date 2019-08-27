package duke.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;
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
    private static final List<String> dateFormatStrings = Arrays.asList(
        "dd/MM/yyyy HH:mm", "dd-MM-yyyy HH:mm", "yyyy/MM/dd HH:mm", "yyyy-MM-dd HH:mm",
        "dd/MM/yyyy", "dd-MM-yyyy", "yyyy/MM/dd", "yyyy-MM-dd",
        "dd/MM HH:mm", "dd-MM HH:mm", "dd/MM", "dd-MM");

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
     * @param   rawInput    Raw input passed into command line
     * @return  A {@link Command} object representing the command
     * @throws  DukeException   If input is invalid (see above)
     */
    public Command parseInput(String rawInput) throws DukeException {
        String commandPhrase = rawInput.trim().split(" ", 2)[0];
        int index;

        switch (commandPhrase) {
        case "bye":
            return new ExitCommand();
        case "list":
            return new ListCommand();
        case "done":
            try {
                index = Integer.parseInt(rawInput.split(" ", 2)[1]);
                return new DoneCommand(index);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                throw new DukeException("Please use 'done i' to mark completion of the i-th task in the list.");
            }
        case "delete":
            try {
                index = Integer.parseInt(rawInput.split(" ", 2)[1]);
                return new DeleteCommand(index);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                throw new DukeException("Please use 'delete i' to delete the i-th task in the list.");
            }
        case "find":
            if (!rawInput.contains(" ")) {
                throw new DukeException("Your search string cannot be empty. To see all tasks, use \"list\" instead.");
            }

            return new FindCommand(rawInput.split(" ", 2)[1]);
        case "todo": //Fallthrough
        case "event": //Fallthrough
        case "deadline":
            return new AddCommand(parseTask(rawInput));
        default:
            throw new DukeException("I don't understand that command.");
        }
    }

    /**
     * Interprets a todo, deadline or event command and creates the corresponding {@link Task} object.
     *
     * @param   rawInput    Raw input passed into command line
     * @return  A Task object to add to the task list
     * @throws  DukeException   If task description or time is invalid
     */
    private Task parseTask(String rawInput) throws DukeException {
        String taskType = rawInput.split(" ", 2)[0];
        String taskRawDesc;
        String taskDesc;

        if (!rawInput.contains(" ")) {
            throw new DukeException("The description of a " + taskType + " cannot be empty.");
        }

        taskRawDesc = rawInput.split(" ", 2)[1];

        String taskRawTime;
        switch (taskType) {
        case "todo":
            return new ToDo(taskRawDesc);
        case "deadline":
            if (!taskRawDesc.contains(" /by ")) {
                throw new DukeException("Please specify the deadline using /by (with spaces preceding and following).");
            }

            taskDesc = taskRawDesc.split(" /by ", 2)[0];
            taskRawTime = taskRawDesc.split(" /by ", 2)[1];

            if (taskDesc.length() == 0) {
                throw new DukeException("The description of a deadline cannot be empty.");
            }
            if (taskRawTime.length() == 0) {
                throw new DukeException("Please specify the deadline using /by (with spaces preceding and following).");
            }

            try {
                return new Deadline(taskDesc, parseTime(taskRawTime));
            } catch (DukeException e) {
                assert e.getMessage().equals("Internal exception: no date format found");
                return new Deadline(taskDesc, taskRawTime);
            }
        case "event":
            if (!taskRawDesc.contains(" /at ")) {
                throw new DukeException("Please specify the event time using /at"
                        + "(with spaces preceding and following).");
            }

            taskDesc = taskRawDesc.split(" /at ", 2)[0];
            taskRawTime = taskRawDesc.split(" /at ", 2)[1];

            if (taskDesc.length() == 0) {
                throw new DukeException("The description of an event cannot be empty.");
            }
            if (taskRawTime.length() == 0) {
                throw new DukeException("Please specify the event time using /at"
                        + "(with spaces preceding and following).");
            }

            try {
                return new Event(taskDesc, parseTime(taskRawTime));
            } catch (DukeException e) {
                assert e.getMessage().equals("Internal exception: no date format found");
                return new Event(taskDesc, taskRawTime);
            }
        default:
            throw new RuntimeException("Our parser encountered a fatal error.");
        }
    }

    /**
     * Attempts to interpret a string representing time using a list of date formats.
     * If there was no suitable format, throws a {@link DukeException DukeException}.
     *
     * @param   rawTime Substring of command representing a time
     * @return  A {@link Calendar} object if the command can be parsed
     * @throws  DukeException   If command cannot be parsed
     */
    private Calendar parseTime(String rawTime) throws DukeException {
        DateFormat dateFormat;

        for (String dateFormatString : dateFormatStrings) {
            dateFormat = new SimpleDateFormat(dateFormatString);
            dateFormat.setLenient(false);

            try {
                Date dateTime = dateFormat.parse(rawTime);
                Calendar calendarTime = Calendar.getInstance();
                calendarTime.setTime(dateTime);

                if (!dateFormatString.contains("yyyy")) { //set year of calendar
                    Calendar currentTime = Calendar.getInstance();
                    int currentYear = currentTime.get(Calendar.YEAR);
                    calendarTime.set(Calendar.YEAR, currentYear);
                    if (calendarTime.before(currentTime)) {
                        calendarTime.set(Calendar.YEAR, currentYear + 1);
                    }
                }
                return calendarTime;
            } catch (ParseException ignored) {
                continue;
            }
        }

        //no date format worked
        throw new DukeException("Internal exception: no date format found");
    }
}
