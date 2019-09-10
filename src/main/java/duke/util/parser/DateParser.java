package duke.util.parser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import duke.exception.DukeException;

/**
 * Parses dates in Duke's commands.
 */
class DateParser {
    private static final String INTERNAL_DATE_FORMAT_NOT_FOUND = "Internal exception: no date format found";

    private static final List<String> DATE_FORMAT_STRINGS = Arrays.asList(
            "dd/MM/yyyy HH:mm", "dd-MM-yyyy HH:mm", "yyyy/MM/dd HH:mm", "yyyy-MM-dd HH:mm",
            "dd/MM/yyyy", "dd-MM-yyyy", "yyyy/MM/dd", "yyyy-MM-dd",
            "dd/MM HH:mm", "dd-MM HH:mm", "dd/MM", "dd-MM");

    /**
     * Attempts to interpret a string representing time using a list of date formats.
     * If there was no suitable format, throws a {@link DukeException}.
     *
     * @param   rawTime Substring of command representing a time
     * @return  A {@link Calendar} object if the command can be parsed
     * @throws  DukeException   If command cannot be parsed
     */
    Calendar parseTime(String rawTime) throws DukeException {
        DateFormat dateFormat;

        for (String dateFormatString : DATE_FORMAT_STRINGS) {
            dateFormat = new SimpleDateFormat(dateFormatString);
            dateFormat.setLenient(false);

            try {
                Date dateTime = dateFormat.parse(rawTime);
                Calendar calendarTime = Calendar.getInstance();
                calendarTime.setTime(dateTime);

                if (!dateFormatString.contains("yyyy")) { //set year of calendar
                    Calendar currentTime = Calendar.getInstance();
                    int currentYear = currentTime.get(Calendar.YEAR);
                    calendarTime.set(Calendar.YEAR, currentYear);
                    if (calendarTime.before(currentTime)) {
                        calendarTime.set(Calendar.YEAR, currentYear + 1);
                    }
                }
                return calendarTime;
            } catch (ParseException ignored) {
                continue; //try next date format string
            }
        }

        //no date format worked
        throw new DukeException(INTERNAL_DATE_FORMAT_NOT_FOUND);
    }
}
