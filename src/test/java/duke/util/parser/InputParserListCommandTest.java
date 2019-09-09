package duke.util.parser;

import duke.command.Command;
import duke.command.ListCommand;
import duke.exception.DukeException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InputParserListCommandTest {
    private InputParser parser;

    @BeforeEach
    void initTests() {
        parser = new InputParser();
    }

    @Test
    void parseInputListCommand_commandOnly_successful() {
        String input = "list";

        try {
            Command command = parser.parseInput(input);
            assert command instanceof ListCommand;
        } catch (DukeException e) {
            assertEquals(1, 0);
        }
    }

    @Test
    void parseInputListCommand_spacedInput_successful() {
        String input = "    list   ";

        try {
            Command command = parser.parseInput(input);
            assert command instanceof ListCommand;
        } catch (DukeException e) {
            assertEquals(1, 0);
        }
    }
}
