package duke.task;

import java.util.Calendar;
import java.util.GregorianCalendar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class DeadlineTest {
    @Test
    void testToString_stringConstructor() {
        Deadline deadlineString = new Deadline("do dishes", "tomorrow");
        assertEquals("[D][X] do dishes (by: tomorrow)", deadlineString.toString());
        deadlineString.markAsDone();
        assertEquals("[D][O] do dishes (by: tomorrow)", deadlineString.toString());
    }

    @Test
    void testToString_calendarConstructor() {
        Calendar calendar = new GregorianCalendar(2020, Calendar.FEBRUARY, 22, 11, 30);
        Deadline deadlineCalendar = new Deadline("do dishes", calendar);
        assertEquals("[D][X] do dishes (by: 22-02-2020 11:30)", deadlineCalendar.toString());
        deadlineCalendar.markAsDone();
        assertEquals("[D][O] do dishes (by: 22-02-2020 11:30)", deadlineCalendar.toString());
    }
}