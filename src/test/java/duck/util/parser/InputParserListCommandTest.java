package duck.util.parser;

import duck.command.Command;
import duck.command.ListCommand;
import duck.exception.DuckException;
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
        } catch (DuckException e) {
            assertEquals(1, 0);
        }
    }

    @Test
    void parseInputListCommand_spacedInput_successful() {
        String input = "    list   ";

        try {
            Command command = parser.parseInput(input);
            assert command instanceof ListCommand;
        } catch (DuckException e) {
            assertEquals(1, 0);
        }
    }
}
