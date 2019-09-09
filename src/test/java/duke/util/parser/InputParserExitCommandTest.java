package duke.util.parser;

import duke.command.Command;
import duke.command.ExitCommand;
import duke.exception.DukeException;
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
        } catch (DukeException e) {
            assertEquals(1, 0);
        }
    }

    @Test
    void parseInputExitCommand_spacedInput_successful() {
        String input = "    bye       ";

        try {
            Command command = parser.parseInput(input);
            assert command instanceof ExitCommand;
        } catch (DukeException e) {
            assertEquals(1, 0);
        }
    }
}