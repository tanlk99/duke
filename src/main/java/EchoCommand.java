public class EchoCommand extends Command {
    String toEcho;

    public EchoCommand(String toEcho) {
        this.toEcho = toEcho;
    }

    public Type getType() {
        return Type.ECHO;
    }

    public void execute() {
        Formatter.printHorizontalLine();
        Formatter.formatLine(toEcho);
        Formatter.printHorizontalLine();
    }
}
