import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    private ArrayList<Task> taskList = new ArrayList<Task>();

    private void init() {
        greetUser();

        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            try {
                String rawInput = sc.nextLine();
                Command parsedCommand = Parser.parseInput(rawInput);
                parsedCommand.execute(taskList);

                if (parsedCommand.terminate()) {
                    return;
                }
            } catch (DukeException e) {
                Formatter.printHorizontalLine();
                Formatter.formatLine("Sorry! " + e);
                Formatter.printHorizontalLine();
            }
        }
    }

    public static void main(String[] args) {
        new Duke().init();
    }

    private void greetUser() {
        String[] greeting = {" ____        _        ",
            "|  _ \\ _   _| | _____ ",
            "| | | | | | | |/ / _ \\",
            "| |_| | |_| |   <  __/",
            "|____/ \\__,_|_|\\_\\___|\n",
            "Hello! I'm Duke.",
            "What can I do for you?"};

        Formatter.printHorizontalLine();
        for (String line : greeting) {
            Formatter.formatLine(line);
        }
        Formatter.printHorizontalLine();
    }
}
