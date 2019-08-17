public class Task {
    String desc;
    boolean isDone;

    public Task(String desc) {
        this.desc = desc;
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
        return getStatusIcon() + " " + desc;
    }
}
