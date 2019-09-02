package duke.util;

import duke.util.parser.Parser;
import duke.command.Command;
import duke.command.DeleteCommand;
import duke.exception.DukeException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserDeleteCommandTest {
    private Parser parser;

    @BeforeEach
    void initTests() {
        parser = new Parser();
    }

    @Test
    void parseInputDeleteCommand_validInput_successful() {
        String input = "delete 1";

        try {
            Command command = parser.parseInput(input);
            assert command instanceof DeleteCommand;

            int index = ((DeleteCommand)command).getIndex();
            assertEquals(1, index);
        } catch (DukeException e) {
            assertEquals(1, 0);
        }
    }

    @Test
    void parseInputDeleteCommand_spacedInput_successful() {
        String input = "  delete    1   ";

        try {
            Command command = parser.parseInput(input);
            assert command instanceof DeleteCommand;

            int index = ((DeleteCommand)command).getIndex();
            assertEquals(1, index);
        } catch (DukeException e) {
            assertEquals(1, 0);
        }
    }

    @Test
    void parseInputDeleteCommand_invalidInput_exceptionThrown() {
        String input = "delete aaaaa";

        try {
            Command command = parser.parseInput(input);
            assertEquals(1, 0);
        } catch (DukeException e) {
            assertEquals("Please use 'delete i' to delete the i-th task in the list.", e.getMessage());
        }
    }

    @Test
    void parseInputDeleteCommand_emptyInput_exceptionThrown() {
        String input = "    delete    ";

        try {
            Command command = parser.parseInput(input);
            assertEquals(1, 0);
        } catch (DukeException e) {
            assertEquals("Please use 'delete i' to delete the i-th task in the list.", e.getMessage());
        }
    }
}

