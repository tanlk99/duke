package duke.util;

/**
 * Buffers Duke's text output.
 */
public class Buffer {
    private String output = "";

    /**
     * Appends a string to the buffer.
     *
     * @param   output  Output string to append
     */
    public void formatLine(String output) {
        this.output += output + "\n";
    }

    /**
     * Adds Duke's standard welcome on startup to the buffer.
     */
    public void addGreetings() {
        String[] greeting = {"Hello! I'm Duke.",
                             "What can I do for you?"};

        for (String line : greeting) {
            formatLine(line);
        }
    }

    /**
     * Adds the error message if loading from cache failed to the buffer.
     */
    public void addLoadingError() {
        String[] apology = {"Sorry! Duke could not load up your previous task list from storage.",
                            "If this is your first time using Duke, you can ignore this message.",
                            "Starting Duke with a blank task list..."};

        for (String line : apology) {
            formatLine(line);
        }
    }

    /**
     * Returns the entire buffer, and then empties it.
     *
     * @return Current buffer string
     */
    public String getOutput() {
        String toReturn = output;
        output = "";
        return toReturn;
    }
}
