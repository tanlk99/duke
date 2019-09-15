package duke.util.parser;

import duke.task.Task;
import duke.command.AddCommand;
import duke.exception.DukeException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddCommandParserTest {
    private AddCommandParser parser;

    @BeforeEach
    void initTests() {
        parser = new AddCommandParser();
    }

    private void parseCommand_successful(String input, String expectedTaskString) {
        try {
            AddCommand command = parser.parseCommand(input);
            Task task = command.getTaskToAdd();
            assertEquals(expectedTaskString, task.toString());
        } catch (DukeException e) {
            assertEquals(1, 0);
        }
    }

    private void parseCommand_exceptionThrown(String input, String expectedExceptionMessage) {
        try {
            AddCommand command = parser.parseCommand(input);
            assertEquals(1, 0);
        } catch (DukeException e) {
            assertEquals(expectedExceptionMessage, e.getMessage());
        }
    }

    @Test
    void parseCommandTodoCommand_defaultInput_successful() {
        parseCommand_successful("todo abc", "[T][X] abc");
    }

    @Test
    void parseCommandTodoCommand_spacedInput_successful() {
        parseCommand_successful("todo      ab c", "[T][X] ab c");
    }

    @Test
    void parseCommandTodoCommand_emptyInput_exceptionThrown() {
        parseCommand_exceptionThrown("todo", "The description of a todo cannot be empty.");
    }

    @Test
    void parseCommandDeadlineCommand_defaultInput_successful() {
        parseCommand_successful("deadline abc /by def", "[D][X] abc (by: def)");
    }

    @Test
    void parseCommandDeadlineCommand_spacedInput_successful() {
        parseCommand_successful("deadline      a bc     /by     de f", "[D][X] a bc (by: de f)");
    }

    @Test
    void parseCommandDeadlineCommand_validDate_successful() {
        parseCommand_successful("deadline abc /by 13/2/2019", "[D][X] abc (by: 13-02-2019 00:00)");
        parseCommand_successful("deadline abc /by 13-2-2019", "[D][X] abc (by: 13-02-2019 00:00)");
        parseCommand_successful("deadline abc /by 2019/2/13", "[D][X] abc (by: 13-02-2019 00:00)");
        parseCommand_successful("deadline abc /by 2019/2/13 23:59", "[D][X] abc (by: 13-02-2019 23:59)");
    }

    @Test
    void parseCommandDeadlineCommand_invalidDate_successful() {
        parseCommand_successful("deadline abc /by 33/2/2019", "[D][X] abc (by: 33/2/2019)");
        parseCommand_successful("deadline abc /by 13-13-2019", "[D][X] abc (by: 13-13-2019)");
        parseCommand_successful("deadline abc /by 1", "[D][X] abc (by: 1)");

        //partial success
        parseCommand_successful("deadline abc /by 2019/2/13 26:59", "[D][X] abc (by: 13-02-2019 00:00)");
    }

    @Test
    void parseCommandDeadlineCommand_emptyInput_exceptionThrown() {
        parseCommand_exceptionThrown("deadline",
                "The description of a deadline cannot be empty.");
    }

    @Test
    void parseCommandDeadlineCommand_emptyDescription_exceptionThrown() {
        parseCommand_exceptionThrown("deadline   /by 2",
                "The description of a deadline cannot be empty.");
    }

    @Test
    void parseCommandDeadlineCommand_emptyDescriptionNoWhitespace_exceptionThrown() {
        parseCommand_exceptionThrown("deadline /by 2",
                "The description of a deadline cannot be empty.");
    }

    @Test
    void parseCommandDeadlineCommand_noDivider_exceptionThrown() {
        parseCommand_exceptionThrown("deadline a",
                "Please specify the deadline time using /by (with spaces preceding and following).");
    }

    @Test
    void parseCommandDeadlineCommand_emptyTime_exceptionThrown() {
        parseCommand_exceptionThrown("deadline a /by",
                "Please specify the deadline time using /by (with spaces preceding and following).");
    }

    @Test
    void parseCommandEventCommand_defaultInput_successful() {
        parseCommand_successful("event abc /at def", "[E][X] abc (at: def)");
    }

    @Test
    void parseCommandEventCommand_emptyInput_exceptionThrown() {
        parseCommand_exceptionThrown("event",
                "The description of an event cannot be empty.");
    }

    @Test
    void parseCommandEventCommand_emptyDescription_exceptionThrown() {
        parseCommand_exceptionThrown("event  /at 2",
                "The description of an event cannot be empty.");
    }

    @Test
    void parseCommandEventCommand_emptyTime_exceptionThrown() {
        parseCommand_exceptionThrown("event a /at",
                "Please specify the event time using /at (with spaces preceding and following).");
    }
}
