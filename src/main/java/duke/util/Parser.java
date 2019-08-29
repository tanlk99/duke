package duke.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
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
     * Enumerates all task types known to the parser.
     */
    private static enum TaskType {
        TODO("todo", Optional.empty()),
        DEADLINE("deadline", Optional.of("/by")),
        EVENT("event", Optional.of("/at"));

        public final String commandPhrase;
        public final Optional<String> timeDivider;

        TaskType(String commandPhrase, Optional<String> timeDivider) {
            this.commandPhrase = commandPhrase;
            this.timeDivider = timeDivider;
        }

        /**
         * Gets the corresponding TaskType using its command phrase.
         */
        static TaskType getTaskType(String commandPhrase) {
            Optional<TaskType> result = Arrays.stream(TaskType.values())
                    .filter(t -> t.commandPhrase.equals(commandPhrase))
                    .findFirst();

            if (!result.isPresent()) {
                throw new RuntimeException("Our parser encountered a fatal error.");
            }

            return result.get();
        }
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
            if (!rawInput.contains(" ")) {
                throw new DukeException("The description of a " + commandPhrase + " cannot be empty.");
            }
            String rawTaskDescription = rawInput.split(" ", 2)[1];
            TaskType taskType = TaskType.getTaskType(commandPhrase);
            return new AddCommand(parseTask(rawTaskDescription, taskType));
        default:
            throw new DukeException("I don't understand that command.");
        }
    }

    /**
     * Interprets a command to add a task and creates the corresponding {@link Task} object.
     *
     * @param   rawTaskDescription    Raw description of task passed to command line
     * @return  A Task object to add to the task list
     * @throws  DukeException   If task description or time is invalid
     */
    private Task parseTask(String rawTaskDescription, TaskType taskType) throws DukeException {
        if (taskType.timeDivider.isPresent()) {
            return parseTimedTask(rawTaskDescription, taskType);
        } else {
            return parseUntimedTask(rawTaskDescription, taskType);
        }
    }

    /**
     * Interprets a command to add a timed task and creates the corresponding {@link Task} object.
     * Timed tasks include {@link Todo} and {@link Deadline}.
     *
     * @param   rawTaskDescription    Raw description of task passed to command line
     * @return  A Task object to add to the task list
     * @throws  DukeException   If task description or time is invalid
     */
    private Task parseTimedTask(String rawTaskDescription, TaskType taskType) throws DukeException {
        String divider = taskType.timeDivider.get();
        String command = taskType.commandPhrase;

        if (!rawTaskDescription.contains(" " + divider + " ")) {
            throw new DukeException("Please specify the " + command + " using " + divider
                    + " (with spaces preceding and following).");
        }

        String taskDescription = rawTaskDescription.split(" " + divider + " ", 2)[0].trim();
        String rawTaskTime = rawTaskDescription.split(" " + divider + " ", 2)[1].trim();

        if (taskDescription.length() == 0) {
            throw new DukeException("The description of a " + command + " cannot be empty.");
        }
        if (rawTaskTime.length() == 0) {
            throw new DukeException("Please specify the " + command + " using " + divider
                    + " (with spaces preceding and following).");
        }

        switch (taskType) {
        case DEADLINE:
            try {
                return new Deadline(taskDescription, parseTime(rawTaskTime));
            } catch (DukeException e) {
                assert e.getMessage().equals("Internal exception: no date format found");
                return new Deadline(taskDescription, rawTaskTime);
            }
        case EVENT:
            try {
                return new Event(taskDescription, parseTime(rawTaskTime));
            } catch (DukeException e) {
                assert e.getMessage().equals("Internal exception: no date format found");
                return new Event(taskDescription, rawTaskTime);
            }
        default:
            throw new RuntimeException("Our parser encountered a fatal error."); //should not continue
        }
    }

    /**
     * Interprets a command to add an untimed task and creates the corresponding {@link Task} object.
     * Untimed tasks include {@link Todo}.
     *
     * @param   rawTaskDescription    Raw description of task passed to command line
     * @return  A Task object to add to the task list
     * @throws  DukeException   If task description is invalid
     */
    private Task parseUntimedTask(String rawTaskDescription, TaskType taskType) throws DukeException {
        String taskDescription = rawTaskDescription.trim();
        if (taskDescription.length() == 0) {
            throw new DukeException("The description of a " + taskType.commandPhrase + " cannot be empty.");
        }

        switch (taskType) {
        case TODO:
            return new ToDo(rawTaskDescription);
        default:
            throw new RuntimeException("Our parser encountered a fatal error."); //should not continue
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
