abstract class Command {
    public enum Type {
        EXIT, ECHO
    }

    abstract public Type getType();

    abstract public void execute();
}
