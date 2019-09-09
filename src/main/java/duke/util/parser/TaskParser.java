package duke.util.parser;

import java.util.Arrays;
import java.util.Optional;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;
import duke.exception.DukeException;

/**
 * Parses tasks in Duke's commands.
 */
class TaskParser {
    private static final String TASK_EMPTY_DESCRIPTION = "The description of %1$s %2$s cannot be empty.";
    private static final String TASK_EMPTY_TIME = "Please specify the %1$s time using %2$s "
            + "(with spaces preceding and following).";

    private static final String INTERNAL_DATE_FORMAT_NOT_FOUND = "Internal exception: no date format found";
    private static final String PARSER_FATAL_ERROR = "My parser encountered a fatal error.";

    /**
     * Enumerates all task types known to the parser.
     */
    private enum TaskType {
        TODO("todo", null),
        DEADLINE("deadline", "/by"),
        EVENT("event", "/at");

        public final String commandPhrase;
        public final String timeDivider;

        TaskType(String commandPhrase, String timeDivider) {
            this.commandPhrase = commandPhrase;
            this.timeDivider = timeDivider;
        }

        /**
         * Gets the corresponding TaskType using its command phrase.
         *
         * @param   commandPhrase   Command phrase passed to command line
         * @return  A TaskType object corresponding to the command phrase
         */
        static TaskType getTaskType(String commandPhrase) {
            Optional<TaskType> result = Arrays.stream(TaskType.values())
                    .filter(t -> t.commandPhrase.equals(commandPhrase))
                    .findFirst();

            if (result.isEmpty()) {
                throw new RuntimeException(PARSER_FATAL_ERROR);
            }

            return result.get();
        }
    }

    /**
     * Interprets a command to add a task and creates the corresponding {@link Task} object.
     *
     * @param   rawTaskDescription    Raw description of task passed to command line
     * @param   commandPhrase         Command phrase passed to command line (to indicate task type)
     * @return  A Task object to add to the task list
     * @throws  DukeException   If task description or time is invalid
     */
    Task parseTask(String rawTaskDescription, String commandPhrase) throws DukeException {
        TaskType taskType = TaskType.getTaskType(commandPhrase);
        if (taskType.timeDivider != null) {
            return parseTimedTask(rawTaskDescription, taskType);
        } else {
            return parseUntimedTask(rawTaskDescription, taskType);
        }
    }

    /**
     * Interprets a command to add a timed task and creates the corresponding {@link Task} object.
     * Timed tasks include {@link Event} and {@link Deadline}.
     *
     * @param   rawTaskDescription    Raw description of task passed to command line
     * @return  A Task object to add to the task list
     * @throws  DukeException   If task description or time is invalid
     */
    private Task parseTimedTask(String rawTaskDescription, TaskType taskType) throws DukeException {
        assert taskType.timeDivider != null;
        String divider = taskType.timeDivider;
        String command = taskType.commandPhrase;

        if (!rawTaskDescription.contains(" " + divider + " ")) {
            throw new DukeException(String.format(TASK_EMPTY_TIME, command, divider));
        }

        String taskDescription = rawTaskDescription.split(" " + divider + " ", 2)[0].trim();
        String rawTaskTime = rawTaskDescription.split(" " + divider + " ", 2)[1].trim();

        if (taskDescription.length() == 0) {
            throw new DukeException(String.format(TASK_EMPTY_DESCRIPTION,
                    ParserUtil.getArticle(command), command));
        }
        if (rawTaskTime.length() == 0) {
            throw new DukeException(String.format(TASK_EMPTY_TIME, command, divider));
        }

        switch (taskType) {
        case DEADLINE:
            try {
                return new Deadline(taskDescription, new DateParser().parseTime(rawTaskTime));
            } catch (DukeException e) {
                assert e.getMessage().equals(INTERNAL_DATE_FORMAT_NOT_FOUND);
                return new Deadline(taskDescription, rawTaskTime);
            }
        case EVENT:
            try {
                return new Event(taskDescription, new DateParser().parseTime(rawTaskTime));
            } catch (DukeException e) {
                assert e.getMessage().equals(INTERNAL_DATE_FORMAT_NOT_FOUND);
                return new Event(taskDescription, rawTaskTime);
            }
        default:
            throw new RuntimeException(PARSER_FATAL_ERROR); //should not continue
        }
    }

    /**
     * Interprets a command to add an untimed task and creates the corresponding {@link Task} object.
     * Untimed tasks include {@link ToDo}.
     *
     * @param   rawTaskDescription    Raw description of task passed to command line
     * @return  A Task object to add to the task list
     * @throws  DukeException   If task description is invalid
     */
    private Task parseUntimedTask(String rawTaskDescription, TaskType taskType) throws DukeException {
        assert taskType.timeDivider == null;
        String taskDescription = rawTaskDescription.trim();
        String command = taskType.commandPhrase;

        if (taskDescription.length() == 0) {
            throw new DukeException(String.format(TASK_EMPTY_DESCRIPTION,
                    ParserUtil.getArticle(command), command));
        }

        switch (taskType) {
        case TODO:
            return new ToDo(taskDescription);
        default:
            throw new RuntimeException(PARSER_FATAL_ERROR); //should not continue
        }
    }
}
