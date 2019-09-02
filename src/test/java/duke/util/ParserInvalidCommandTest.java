package duke.util;

import duke.util.parser.Parser;
import duke.command.Command;
import duke.exception.DukeException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserInvalidCommandTest {
    private Parser parser;

    @BeforeEach
    void initTests() {
        parser = new Parser();
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
