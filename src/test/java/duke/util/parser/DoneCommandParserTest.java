package duke.util.parser;

import duke.command.DoneCommand;
import duke.exception.DukeException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DoneCommandParserTest {
    private DoneCommandParser parser;

    @BeforeEach
    void initTests() {
        parser = new DoneCommandParser();
    }

    @Test
    void parseComman_validInput_successful() {
        String input = "done 1";

        try {
            DoneCommand command = parser.parseCommand(input);
            int index = command.getIndex();
            assertEquals(1, index);
        } catch (DukeException e) {
            assertEquals(1, 0);
        }
    }

    @Test
    void parseCommand_spacedInput_successful() {
        String input = "done    1";

        try {
            DoneCommand command = parser.parseCommand(input);
            int index = command.getIndex();
            assertEquals(1, index);
        } catch (DukeException e) {
            assertEquals(1, 0);
        }
    }

    @Test
    void parseCommand_invalidInput_exceptionThrown() {
        String input = "done aaaaa";

        try {
            DoneCommand command = parser.parseCommand(input);
            assertEquals(1, 0);
        } catch (DukeException e) {
            assertEquals("Please use 'done i' to mark completion of the i-th task in the list.", e.getMessage());
        }
    }

    @Test
    void parseCommand_emptyInput_exceptionThrown() {
        String input = "done";

        try {
            DoneCommand command = parser.parseCommand(input);
            assertEquals(1, 0);
        } catch (DukeException e) {
            assertEquals("Please use 'done i' to mark completion of the i-th task in the list.", e.getMessage());
        }
    }
}
