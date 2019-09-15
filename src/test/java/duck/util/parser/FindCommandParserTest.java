package duck.util.parser;

import duck.command.FindCommand;
import duck.exception.DuckException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FindCommandParserTest {
    private FindCommandParser parser;

    @BeforeEach
    void initTests() {
        parser = new FindCommandParser();
    }

    @Test
    void parseCommand_validInput_successful() {
        String input = "find aabbcc";

        try {
            FindCommand command = parser.parseCommand(input);
            String filter = command.getFilter();
            assertEquals("aabbcc", filter);
        } catch (DuckException e) {
            assertEquals(1, 0);
        }
    }

    @Test
    void parseCommand_spacedInput_successful() {
        String input = "find    aab bcc";

        try {
            FindCommand command = parser.parseCommand(input);
            String filter = command.getFilter();
            assertEquals("aab bcc", filter);
        } catch (DuckException e) {
            assertEquals(1, 0);
        }
    }

    @Test
    void parseCommand_emptyInput_exceptionThrown() {
        String input = "find";

        try {
            FindCommand command = parser.parseCommand(input);
            assertEquals(1, 0);
        } catch (DuckException e) {
            assertEquals("Your search string cannot be empty. To see all tasks, use \"list\" instead.", e.getMessage());
        }
    }
}
