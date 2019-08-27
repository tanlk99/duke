package duke.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ToDoTest {
    @Test
    void testToString() {
        ToDo todo = new ToDo("do dishes");
        assertEquals("[T][X] do dishes", todo.toString());
        todo.markAsDone();
        assertEquals("[T][O] do dishes", todo.toString());
    }
}