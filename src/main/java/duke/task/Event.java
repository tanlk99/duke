package duke.task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Represents a future event.
 * Events have a date associated with them, and can be marked as complete.
 */
public class Event extends Task {
    protected String timeString;
    protected Calendar time;
    protected boolean hasCalendar;

    /**
     * Creates a new Event object using a string to represent time.
     *
     * @param   description description of the Event
     * @param   timeString  a String describing the time of the Event
     */
    public Event(String description, String timeString) {
        super(description);
        this.timeString = timeString;
        this.hasCalendar = false;
    }

    /**
     * Creates a new Event object using a Calendar to represent time.
     *
     * @param   description description of the Event
     * @param   time        a Calendar describing the time of the Event
     */
    public Event(String description, Calendar time) {
        super(description);
        this.time = time;
        this.hasCalendar = true;
    }

    private String getTime() {
        if (hasCalendar) {
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            return dateFormat.format(time.getTime());
        } else {
            return timeString;
        }
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + getTime() + ")";
    }
}