package duke.task;

/**
 * Represents a to-do task.
 * Todos can be marked as done.
 */
public class ToDo extends Task {
    /**
     * No-argument constructor for Jackson.
     */
    public ToDo() {

    }

    /**
     * Creates a new ToDo instance.
     *
     * @param   description     Description of the to-do task
     */
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
