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
import duke.command.ListCommand;
import duke.exception.DukeException;

/**
 * Interprets and converts raw input to various classes used by Duke.
 */
public class Parser {
    /**
     * Interprets a command input string to create a duke.command.Command object.
     *
     * @param   rawInput    raw input passed into command line
     */
    public Command parseInput(String rawInput) throws DukeException {
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
        case "todo":
        case "event":
        case "deadline":
            return new AddCommand(parseTask(rawInput));
        default:
            throw new DukeException("I don't understand that command.");
        }
    }

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
            } if (taskRawTime.length() == 0) {
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
                throw new DukeException("Please specify the event time using /at (with spaces preceding and following).");
            }

            taskDesc = taskRawDesc.split(" /at ", 2)[0];
            taskRawTime = taskRawDesc.split(" /at ", 2)[1];

            if (taskDesc.length() == 0) {
                throw new DukeException("The description of an event cannot be empty.");
            } if (taskRawTime.length() == 0) {
                throw new DukeException("Please specify the event time using /at (with spaces preceding and following).");
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

    private static final List<String> dateFormatStrings = Arrays.asList(
        "dd/MM/yyyy HH:mm", "dd-MM-yyyy HH:mm", "yyyy/MM/dd HH:mm", "yyyy-MM-dd HH:mm",
        "dd/MM/yyyy", "dd-MM-yyyy", "yyyy/MM/dd", "yyyy-MM-dd",
        "dd/MM HH:mm", "dd-MM HH:mm", "dd/MM", "dd-MM");

    private Calendar parseTime(String rawTime) throws DukeException {
        DateFormat dateFormat;

        for (String dateFormatString : dateFormatStrings) {
            dateFormat = new SimpleDateFormat(dateFormatString);
            dateFormat.setLenient(false);

            try {
                Date dateTime = dateFormat.parse(rawTime);
                Calendar calendarTime = Calendar.getInstance();
                calendarTime.setTime(dateTime);

                if (!dateFormatString.contains("yyyy")) {
                    Calendar currentTime = Calendar.getInstance();
                    int currentYear = currentTime.get(Calendar.YEAR);
                    calendarTime.set(Calendar.YEAR, currentYear);
                    if (calendarTime.before(currentTime)) {
                        calendarTime.set(Calendar.YEAR, currentYear + 1);
                    }
                }
                return calendarTime;
            } catch (ParseException ignored) {}
        }

        //no date format worked
        throw new DukeException("Internal exception: no date format found");
    }
}
