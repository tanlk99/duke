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
     * No-argument constructor for Jackson.
     */
    public Event() {
    }

    /**
     * Creates a new Event instance using a string to represent time.
     *
     * @param   description Description of the event
     * @param   timeString  A String describing the time of the event
     */
    public Event(String description, String timeString) {
        super(description);
        this.timeString = timeString;
        hasCalendar = false;
    }

    /**
     * Creates a new Event instance using a {@link Calendar} to represent time.
     *
     * @param   description Description of the event
     * @param   time  A {@link Calendar} describing the time of the event
     */
    public Event(String description, Calendar time) {
        super(description);
        this.time = time;
        hasCalendar = true;
    }

    /**
     * Returns a string describing the time of the event.
     *
     * @return  A string describing the time of the event
     */
    private String getTime() {
        if (hasCalendar) {
            assert time != null;
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            return dateFormat.format(time.getTime());
        } else {
            assert timeString != null;
            return timeString;
        }
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + getTime() + ")";
    }
}
