package duke.util;

import duke.util.parser.Parser;
import duke.command.Command;
import duke.command.DoneCommand;
import duke.exception.DukeException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserDoneCommandTest {
    private Parser parser;

    @BeforeEach
    void initTests() {
        parser = new Parser();
    }

    @Test
    void parseInputDoneCommand_validInput_successful() {
        String input = "done 1";

        try {
            Command command = parser.parseInput(input);
            assert command instanceof DoneCommand;

            int index = ((DoneCommand)command).getIndex();
            assertEquals(1, index);
        } catch (DukeException e) {
            assertEquals(1, 0);
        }
    }

    @Test
    void parseInputDoneCommand_spacedInput_successful() {
        String input = "  done    1   ";

        try {
            Command command = parser.parseInput(input);
            assert command instanceof DoneCommand;

            int index = ((DoneCommand)command).getIndex();
            assertEquals(1, index);
        } catch (DukeException e) {
            assertEquals(1, 0);
        }
    }

    @Test
    void parseInputDoneCommand_invalidInput_exceptionThrown() {
        String input = "done aaaaa";

        try {
            Command command = parser.parseInput(input);
            assertEquals(1, 0);
        } catch (DukeException e) {
            assertEquals("Please use 'done i' to mark completion of the i-th task in the list.", e.getMessage());
        }
    }

    @Test
    void parseInputDoneCommand_emptyInput_exceptionThrown() {
        String input = "    done    ";

        try {
            Command command = parser.parseInput(input);
            assertEquals(1, 0);
        } catch (DukeException e) {
            assertEquals("Please use 'done i' to mark completion of the i-th task in the list.", e.getMessage());
        }
    }
}
