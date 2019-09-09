package duke.util.parser;

import duke.command.Command;
import duke.command.DeleteCommand;
import duke.exception.DukeException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeleteCommandParserTest {
    private DeleteCommandParser parser;

    @BeforeEach
    void initTests() {
        parser = new DeleteCommandParser();
    }

    @Test
    void parseCommand_validInput_successful() {
        String input = "delete 1";

        try {
            DeleteCommand command = parser.parseCommand(input);
            int index = command.getIndex();
            assertEquals(1, index);
        } catch (DukeException e) {
            assertEquals(1, 0);
        }
    }

    @Test
    void parseCommand_spacedInput_successful() {
        String input = "delete    1";

        try {
            DeleteCommand command = parser.parseCommand(input);
            int index = command.getIndex();
            assertEquals(1, index);
        } catch (DukeException e) {
            assertEquals(1, 0);
        }
    }

    @Test
    void parseCommand_invalidInput_exceptionThrown() {
        String input = "delete aaaaa";

        try {
            DeleteCommand command = parser.parseCommand(input);
            assertEquals(1, 0);
        } catch (DukeException e) {
            assertEquals("Please use 'delete i' to delete the i-th task in the list.", e.getMessage());
        }
    }

    @Test
    void parseCommand_emptyInput_exceptionThrown() {
        String input = "delete";

        try {
            Command command = parser.parseCommand(input);
            assertEquals(1, 0);
        } catch (DukeException e) {
            assertEquals("Please use 'delete i' to delete the i-th task in the list.", e.getMessage());
        }
    }
}

