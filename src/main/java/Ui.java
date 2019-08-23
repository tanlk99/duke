public class Ui {
    public void printHorizontalLine() {
        formatLine("________________________________________" +
            "________________________________________\n");
    }

    public void formatLine(String output) {
        System.out.println("    " + output);
    }

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
