package duck.util.parser;

import duck.command.Command;
import duck.command.ExitCommand;
import duck.exception.DuckException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InputParserExitCommandTest {
    private InputParser parser;

    @BeforeEach
    void initTests() {
        parser = new InputParser();
    }

    @Test
    void parseInputExitCommand_commandOnly_successful() {
        String input = "bye";

        try {
            Command command = parser.parseInput(input);
            assert command instanceof ExitCommand;
        } catch (DuckException e) {
            assertEquals(1, 0);
        }
    }

    @Test
    void parseInputExitCommand_spacedInput_successful() {
        String input = "    bye       ";

        try {
            Command command = parser.parseInput(input);
            assert command instanceof ExitCommand;
        } catch (DuckException e) {
            assertEquals(1, 0);
        }
    }
}