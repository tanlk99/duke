package duke.util;

/**
 * Handles the output done by Duke through wrapper functions.
 */
public class Ui {
    private String output = "";

    /**
     * Appends a string to the output of Duke.
     *
     * @param   output  Output string to append
     */
    public void formatLine(String output) {
        this.output += output + "\n";
    }

    /**
     * Shows Duke's standard welcome on startup.
     */
    public void showGreetings() {
        String[] greeting = {" ____        _        ",
                             "|  _ \\ _   _| | _____ ",
                             "| | | | | | | |/ / _ \\",
                             "| |_| | |_| |   <  __/",
                             "|____/ \\__,_|_|\\_\\___|\n",
                             "Hello! I'm Duke.",
                             "What can I do for you?"};

        for (String line : greeting) {
            formatLine(line);
        }
    }

    /**
     * Shows an error message if loading from cache failed.
     */
    public void showLoadingError() {
        String[] apology = {"Sorry! Duke could not load up your previous task list from storage.",
                            "If this is your first time using Duke, you can ignore this message.",
                            "Starting Duke with a blank task list..."};

        for (String line : apology) {
            formatLine(line);
        }
    }

    /**
     * Returns all of Duke's stored output, and then empties it.
     *
     * @return Duke's stored output
     */
    public String getOutput() {
        String toReturn = output;
        output = "";
        return toReturn;
    }
}
