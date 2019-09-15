package duck.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ToDoTest {
    @Test
    void toString_default() {
        ToDo todo = new ToDo("do dishes");
        assertEquals("[T][X] do dishes", todo.toString());
        todo.markAsDone();
        assertEquals("[T][O] do dishes", todo.toString());
    }
}