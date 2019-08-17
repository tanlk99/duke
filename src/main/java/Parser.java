import java.lang.NumberFormatException;
import java.lang.RuntimeException;

class Parser {
    public static Command parseInput(String rawInput) throws DukeException {
        String commandPhrase = rawInput.split(" ", 2)[0];

        switch (commandPhrase) {
        case "bye":
            return new ExitCommand();
        case "list":
            return new ListCommand();
        case "done":
            try {
                int index = Integer.parseInt(rawInput.split(" ", 2)[1]) - 1;
                return new DoneCommand(index);
            } catch (NumberFormatException e) {
                throw new DukeException("Please use 'done i' to mark completion of the i-th task in the list.");
            }
        case "todo":
        case "event":
        case "deadline":
            return new AddCommand(rawInput);
        default:
            throw new DukeException("I don't understand that command.");
        }
    }

    public static Task parseTask(String rawInput) throws DukeException {
        String taskType = rawInput.split(" ", 2)[0];
        String taskRawDesc;
        String taskDesc;
        String taskTime;

        if (!rawInput.contains(" ")) {
            throw new DukeException("The description of a " + taskType + " cannot be empty.");
        }

        taskRawDesc = rawInput.split(" ", 2)[1];

        switch (taskType) {
        case "todo":
            return new ToDo(taskRawDesc);
        case "deadline":
            if (!taskRawDesc.contains(" /by ")) {
                throw new DukeException("Please specify the deadline using /by (with spaces preceding and following).");
            }

            taskDesc = taskRawDesc.split(" /by ", 2)[0];
            taskTime = taskRawDesc.split(" /by ", 2)[1];

            if (taskDesc.length() == 0) {
                throw new DukeException("The description of a deadline cannot be empty.");
            } if (taskTime.length() == 0) {
                throw new DukeException("Please specify the deadline using /by (with spaces preceding and following).");
            }

            return new Deadline(taskDesc, taskTime);
        case "event":
            if (!taskRawDesc.contains(" /at ")) {
                throw new DukeException("Please specify the event time using /at (with spaces preceding and following).");
            }

            taskDesc = taskRawDesc.split(" /at ", 2)[0];
            taskTime = taskRawDesc.split(" /at ", 2)[1];

            if (taskDesc.length() == 0) {
                throw new DukeException("The description of an event cannot be empty.");
            } if (taskTime.length() == 0) {
                throw new DukeException("Please specify the event time using /at (with spaces preceding and following).");
            }

            return new Event(taskDesc, taskTime);
        default:
            throw new RuntimeException("Our parser encountered a fatal error.");
        }
    }
}
