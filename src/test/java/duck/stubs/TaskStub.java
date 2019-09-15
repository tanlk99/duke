package duck.stubs;

import duck.task.Task;

/**
 * Simplified version of {@link duck.task.Task Task} with a simplified toString output.
 */
public class TaskStub extends Task {
    public TaskStub(String description) {
        super(description);
    }

    public void setDone(boolean isDone) {
        super.isDone = isDone;
    }

    @Override
    public String toString() {
        return (super.isDone ? "O" : "X") + " " + super.description;
    }
}
