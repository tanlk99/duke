import java.util.Scanner;

public class Duke {
    private void init() {
        greetUser();

        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String rawInput = sc.nextLine();
            Command parsedCommand = parseInput(rawInput);
            parsedCommand.execute();

            if (parsedCommand.getType() == Command.Type.EXIT) {
                return;
            }
        }
    }

    private Command parseInput(String rawInput) {
        String commandPhrase = rawInput.split(" ", 2)[0];
        switch (commandPhrase) {
        case "bye":
            return new ExitCommand();
        default:
            return new EchoCommand(rawInput);
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
