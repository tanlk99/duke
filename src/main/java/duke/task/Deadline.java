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
     * Creates a new Deadline object using a string to represent time.
     *
     * @param   description description of the Deadline
     * @param   timeString  a String describing the due date
     */
    public Deadline(String description, String timeString) {
        super(description);
        this.timeString = timeString;
        hasCalendar = false;
    }

    /**
     * Creates a new Deadline object using a Calendar to represent time.
     *
     * @param   description description of the Deadline
     * @param   time        a Calendar describing the due data
     */
    public Deadline(String description, Calendar time) {
        super(description);
        this.time = time;
        hasCalendar = true;
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
        return "[D]" + super.toString() + " (by: " + getTime() + ")";
    }
}
