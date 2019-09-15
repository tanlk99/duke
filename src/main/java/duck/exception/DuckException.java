package duck.exception;

/**
 * Exception class for Duck. Meant to be thrown as an internal exception and
 * must be caught to be thrown in a method.
 */
public class DuckException extends Exception {
    private String message;

    /**
     * Creates a new DuckException.
     *
     * @param   message The error message to display
     */
    public DuckException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
