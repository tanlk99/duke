package duck.util;

/**
 * Buffers Duck's text output.
 */
public class Buffer {
    private static final String[] DUKE_GREETING = {"Hello! I'm Duck.",
                                                   "What can I do for you?"};
    private static final String[] DUKE_APOLOGY = {"Sorry! Duck could not load up your previous task list from storage.",
                                                  "Starting Duck with a blank task list..."};

    private StringBuilder output = new StringBuilder();

    /**
     * Appends a string to the buffer.
     *
     * @param   output  Output string to append
     */
    public void formatLine(String output) {
        this.output.append(output).append("\n");
    }

    /**
     * Adds Duck's standard welcome on startup to the buffer.
     */
    public void addGreetings() {
        for (String line : DUKE_GREETING) {
            formatLine(line);
        }
    }

    /**
     * Adds the error message for cache load failure to the buffer.
     */
    public void addLoadingError() {
        for (String line : DUKE_APOLOGY) {
            formatLine(line);
        }
    }

    /**
     * Returns the entire buffer, and then empties it.
     *
     * @return Current buffer string
     */
    public String getOutput() {
        String toReturn = output.toString();
        output.setLength(0);
        return toReturn;
    }
}
