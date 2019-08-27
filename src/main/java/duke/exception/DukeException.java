package duke.exception;

/**
 * Exception class for Duke. Meant to be thrown as an internal exception and
 * must be caught to be thrown in a method.
 */
public class DukeException extends Exception {
    private String message;

    /**
     * Creates a new DukeException.
     *
     * @param   message The error message to display
     */
    public DukeException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
