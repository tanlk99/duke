package duke.util.parser;

import duke.util.parser.InputParser;
import duke.command.Command;
import duke.command.FindCommand;
import duke.exception.DukeException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InputParserFindCommandTest {
    private InputParser parser;

    @BeforeEach
    void initTests() {
        parser = new InputParser();
    }

    @Test
    void parseInputFindCommand_validInput_successful() {
        String input = "find aabbcc";

        try {
            Command command = parser.parseInput(input);
            assert command instanceof FindCommand;

            String filter = ((FindCommand)command).getFilter();
            assertEquals("aabbcc", filter);
        } catch (DukeException e) {
            assertEquals(1, 0);
        }
    }

    @Test
    void parseInputFindCommand_spacedInput_successful() {
        String input = "  find    aab bcc   ";

        try {
            Command command = parser.parseInput(input);
            assert command instanceof FindCommand;

            String filter = ((FindCommand)command).getFilter();
            assertEquals("aab bcc", filter);
        } catch (DukeException e) {
            assertEquals(1, 0);
        }
    }

    @Test
    void parseInputFindCommand_emptyInput_exceptionThrown() {
        String input = "    find      ";

        try {
            Command command = parser.parseInput(input);
            assertEquals(1, 0);
        } catch (DukeException e) {
            assertEquals("Your search string cannot be empty. To see all tasks, use \"list\" instead.", e.getMessage());
        }
    }
}
