package duke.task;

import java.util.Calendar;
import java.util.GregorianCalendar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class EventTest {
    @Test
    void toString_stringConstructor() {
        Event eventString = new Event("do dishes", "tomorrow");
        assertEquals("[E][X] do dishes (at: tomorrow)", eventString.toString());
        eventString.markAsDone();
        assertEquals("[E][O] do dishes (at: tomorrow)", eventString.toString());
    }

    @Test
    void toString_calendarConstructor() {
        Calendar calendar = new GregorianCalendar(2020, Calendar.FEBRUARY, 22, 11, 30);
        Event eventCalendar = new Event("do dishes", calendar);
        assertEquals("[E][X] do dishes (at: 22-02-2020 11:30)", eventCalendar.toString());
        eventCalendar.markAsDone();
        assertEquals("[E][O] do dishes (at: 22-02-2020 11:30)", eventCalendar.toString());
    }
}