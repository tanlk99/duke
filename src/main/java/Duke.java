import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    private Storage storage;
    private Parser parser;

    private void init() {
        storage = new Storage("../duke-cache.txt");
        parser = new Parser();
        greetUser();

        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            try {
                String rawInput = sc.nextLine();
                Command parsedCommand = parser.parseInput(rawInput);
                parsedCommand.execute(storage);

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
