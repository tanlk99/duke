package duke.task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Represents a deadline.
 * Deadline have a date associated with them, and can be marked as done.
 */
public class Deadline extends Task {
    protected String timeString;
    protected Calendar time;
    protected boolean hasCalendar;

    /**
     * No-argument constructor for Jackson.
     */
    public Deadline() {

    }

    /**
     * Creates a new Deadline instance using a string to represent time.
     *
     * @param   description Description of the deadline
     * @param   timeString  A String describing the due date
     */
    public Deadline(String description, String timeString) {
        super(description);
        this.timeString = timeString;
        hasCalendar = false;
    }

    /**
     * Creates a new Deadline instance using a {@link Calendar} to represent time.
     *
     * @param   description Description of the deadline
     * @param   time  A {@link Calendar} describing the due data
     */
    public Deadline(String description, Calendar time) {
        super(description);
        this.time = time;
        hasCalendar = true;
    }

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
        return "[D]" + super.toString() + " (by: " + getTime() + ")";
    }
}
