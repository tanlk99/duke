import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Represents a deadeline.
 * Deadline have a date associated with them, and can be marked as done.
 */
class Deadline extends Task {
    protected String timeString;
    protected Calendar time;
    protected boolean hasCalendar;

    /**
     * Creates a new Deadline object using a string to represent time.
     *
     * @param   description description of the Deadline
     * @param   timsString  a String describing the due date
     */
    public Deadline(String description, String timeString) {
        super(description);
        this.timeString = timeString;
        this.hasCalendar = false;
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
        this.hasCalendar = true;
    }

    /**
     * Returns a String of the due date.
     * If a Calendar was provided, the time will be formatted as "dd-MM-yyyy HH:mm".
     *
     * @returns a String of the due date
     */
    public String getTime() {
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
