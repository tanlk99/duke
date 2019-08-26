package duke.util;

/**
 * Handles the output done by Duke through wrapper functions.
 */
public class Ui {
    /**
     * Prints an indented long horizontal line. The length of this line is fixed.
     */
    public void printHorizontalLine() {
        formatLine("________________________________________"
                + "________________________________________\n");
    }

    /**
     * Formats and prints a string as the output of Duke.
     * Currently, this function indents the output by 4 spaces and prints it.
     *
     * @param   output  output string to print
     */
    public void formatLine(String output) {
        System.out.println("    " + output);
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

        printHorizontalLine();
        for (String line : greeting) {
            formatLine(line);
        }
        printHorizontalLine();
    }

    /**
     * Shows an error message if loading from cache failed.
     */
    public void showLoadingError() {
        String[] apology = {"Sorry! Duke could not load up your previous task list from storage.",
                            "If this is your first time using Duke, you can ignore this message.",
                            "Starting Duke with a blank task list..."};

        printHorizontalLine();
        for (String line : apology) {
            formatLine(line);
        }
        printHorizontalLine();
    }
}
