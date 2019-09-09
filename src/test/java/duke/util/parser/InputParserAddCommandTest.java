package duke.util.parser;

import duke.task.Task;
import duke.command.AddCommand;
import duke.command.Command;
import duke.exception.DukeException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InputParserAddCommandTest {
    private InputParser parser;

    @BeforeEach
    void initTests() {
        parser = new InputParser();
    }

    private void parseInputAddCommand_successful(String input, String expectedTaskString) {
        try {
            Command command = parser.parseInput(input);
            assert command instanceof AddCommand;

            Task task = ((AddCommand)command).getTaskToAdd();
            assertEquals(expectedTaskString, task.toString());
        } catch (DukeException e) {
            assertEquals(1, 0);
        }
    }

    private void parseInputAddCommand_exceptionThrown(String input, String expectedExceptionMessage) {
        try {
            Command command = parser.parseInput(input);
            assertEquals(1, 0);
        } catch (DukeException e) {
            assertEquals(expectedExceptionMessage, e.getMessage());
        }
    }

    @Test
    void parseInputTodoCommand_defaultInput_successful() {
        parseInputAddCommand_successful("todo abc", "[T][X] abc");
    }

    @Test
    void parseInputTodoCommand_spacedInput_successful() {
        parseInputAddCommand_successful("  todo      ab c    ", "[T][X] ab c");
    }

    @Test
    void parseInputTodoCommand_emptyInput_exceptionThrown() {
        parseInputAddCommand_exceptionThrown("todo", "The description of a todo cannot be empty.");
    }

    @Test
    void parseInputTodoCommand_emptyInputWithSpaces_exceptionThrown() {
        parseInputAddCommand_exceptionThrown("   todo     ", "The description of a todo cannot be empty.");
    }

    @Test
    void parseInputDeadlineCommand_defaultInput_successful() {
        parseInputAddCommand_successful("deadline abc /by def", "[D][X] abc (by: def)");
    }

    @Test
    void parseInputDeadlineCommand_spacedInput_successful() {
        parseInputAddCommand_successful("  deadline      a bc     /by     de f   ", "[D][X] a bc (by: de f)");
    }

    @Test
    void parseInputDeadlineCommand_validDate_successful() {
        parseInputAddCommand_successful("deadline abc /by 13/2/2019", "[D][X] abc (by: 13-02-2019 00:00)");
        parseInputAddCommand_successful("deadline abc /by 13-2-2019", "[D][X] abc (by: 13-02-2019 00:00)");
        parseInputAddCommand_successful("deadline abc /by 2019/2/13", "[D][X] abc (by: 13-02-2019 00:00)");
        parseInputAddCommand_successful("deadline abc /by 2019/2/13 23:59", "[D][X] abc (by: 13-02-2019 23:59)");
    }

    @Test
    void parseInputDeadlineCommand_invalidDate_successful() {
        parseInputAddCommand_successful("deadline abc /by 33/2/2019", "[D][X] abc (by: 33/2/2019)");
        parseInputAddCommand_successful("deadline abc /by 13-13-2019", "[D][X] abc (by: 13-13-2019)");
        parseInputAddCommand_successful("deadline abc /by 1", "[D][X] abc (by: 1)");

        //partial success
        parseInputAddCommand_successful("deadline abc /by 2019/2/13 26:59", "[D][X] abc (by: 13-02-2019 00:00)");
    }

    @Test
    void parseInputDeadlineCommand_emptyInput_exceptionThrown() {
        parseInputAddCommand_exceptionThrown("deadline",
                "The description of a deadline cannot be empty.");
    }

    @Test
    void parseInputDeadlineCommand_emptyInputWithSpaces_exceptionThrown() {
        parseInputAddCommand_exceptionThrown("   deadline     ",
                "The description of a deadline cannot be empty.");
    }

    @Test
    void parseInputDeadlineCommand_emptyDescription_exceptionThrown() {
        parseInputAddCommand_exceptionThrown("deadline   /by 2",
                "The description of a deadline cannot be empty.");
    }

    @Test
    void parseInputDeadlineCommand_noDivider_exceptionThrown() {
        parseInputAddCommand_exceptionThrown("deadline a",
                "Please specify the deadline time using /by (with spaces preceding and following).");
    }

    @Test
    void parseInputDeadlineCommand_emptyTime_exceptionThrown() {
        parseInputAddCommand_exceptionThrown("deadline a /by",
                "Please specify the deadline time using /by (with spaces preceding and following).");
    }

    @Test
    void parseInputEventCommand_defaultInput_successful() {
        parseInputAddCommand_successful("event abc /at def", "[E][X] abc (at: def)");
    }

    @Test
    void parseInputEventCommand_emptyInput_exceptionThrown() {
        parseInputAddCommand_exceptionThrown("event",
                "The description of an event cannot be empty.");
    }

    @Test
    void parseInputEventCommand_emptyDescription_exceptionThrown() {
        parseInputAddCommand_exceptionThrown("event  /at 2",
                "The description of an event cannot be empty.");
    }

    @Test
    void parseInputEventCommand_emptyTime_exceptionThrown() {
        parseInputAddCommand_exceptionThrown("event a /at",
                "Please specify the event time using /at (with spaces preceding and following).");
    }
}
