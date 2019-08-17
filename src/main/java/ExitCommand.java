public class ExitCommand extends Command {
    public Command.Type getType() {
        return Command.Type.EXIT;
    }

    public void execute() {
        Formatter.printHorizontalLine();
        Formatter.formatLine("Bye. Hope to see you again soon!");
        Formatter.printHorizontalLine();
    }
}
