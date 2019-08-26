package duke.task;

import java.io.Serializable;

/**
 * Represents a task in the task list.
 * Can be marked as done.
 */
abstract public class Task implements Serializable {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a new Task.
     * New tasks are created as un-done.
     *
     * @param   description a description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Returns the description of the task
     *
     * @return  description of the task
     */
    public String getDescription() {
        return description;
    }

    private String getStatusIcon() {
        return (isDone ? "[O]" : "[X]"); //can't display unicode
    }

    @Override
    public String toString() {
        return getStatusIcon() + " " + description;
    }
}
