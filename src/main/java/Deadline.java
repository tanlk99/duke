import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

class Deadline extends Task {
    String timeString;
    Calendar time;
    boolean hasCalendar;

    public Deadline(String description, String timeString) {
        super(description);
        this.timeString = timeString;
        this.hasCalendar = false;
    }

    public Deadline(String description, Calendar time) {
        super(description);
        this.time = time;
        this.hasCalendar = true;
    }

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
