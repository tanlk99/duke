package duck.util.parser;

import duck.command.DoneCommand;
import duck.exception.DuckException;
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
    void parseCommand_validInput_successful() {
        String input = "done 1";

        try {
            DoneCommand command = parser.parseCommand(input);
            int index = command.getIndex();
            assertEquals(1, index);
        } catch (DuckException e) {
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
        } catch (DuckException e) {
            assertEquals(1, 0);
        }
    }

    @Test
    void parseCommand_invalidInput_exceptionThrown() {
        String input = "done aaaaa";

        try {
            DoneCommand command = parser.parseCommand(input);
            assertEquals(1, 0);
        } catch (DuckException e) {
            assertEquals("Please use 'done i' to mark completion of the i-th task in the list.", e.getMessage());
        }
    }

    @Test
    void parseCommand_emptyInput_exceptionThrown() {
        String input = "done";

        try {
            DoneCommand command = parser.parseCommand(input);
            assertEquals(1, 0);
        } catch (DuckException e) {
            assertEquals("Please use 'done i' to mark completion of the i-th task in the list.", e.getMessage());
        }
    }
}
