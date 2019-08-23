import java.io.Serializable;

abstract public class Task implements Serializable {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markAsDone() {
        isDone = true;
    }

    private String getStatusIcon() {
        return (isDone ? "[O]" : "[X]"); //can't display unicode
    }

    @Override
    public String toString() {
        return getStatusIcon() + " " + description;
    }

    abstract public String toStorageString();
}
