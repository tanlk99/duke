package duke.util.parser;

import duke.util.parser.InputParser;
import duke.command.Command;
import duke.exception.DukeException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InputParserInvalidCommandTest {
    private InputParser parser;

    @BeforeEach
    void initTests() {
        parser = new InputParser();
    }

    @Test
    void parseInputInvalidCommand_exceptionThrown() {
        String input = "badcommand";

        try {
            Command command = parser.parseInput(input);
        } catch (DukeException e) {
            assertEquals("I don't understand that command.", e.getMessage());
        }
    }
}
