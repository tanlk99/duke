package duke.task;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

/**
 * Represents a task in the task list.
 * Can be marked as done.
 */
@JsonTypeInfo(
        use = Id.NAME,
        include = As.PROPERTY,
        property = "@class"
)
@JsonSubTypes({
        @Type(value = ToDo.class, name = "Todo"),
        @Type(value = Deadline.class, name = "Deadline"),
        @Type(value = Event.class, name = "Event")
})
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public abstract class Task {
    protected String description;
    protected boolean isDone = false;

    /**
     * No-argument constructor for Jackson.
     */
    public Task() {
    }

    /**
     * Creates a new Task.
     * New tasks are created as incomplete.
     *
     * @param   description A description of the task
     */
    public Task(String description) {
        this.description = description;
    }

    /**
     * Creates a new Task, which has already been completed.
     *
     * @param   description A description of the task
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Returns the description of the task.
     *
     * @return  Description of the task
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
