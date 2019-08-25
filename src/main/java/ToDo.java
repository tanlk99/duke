/**
 * Represents a Todo task.
 * Todos can be marked as done.
 */
class ToDo extends Task {
    /**
     * Creates a new ToDo object.
     *
     * @param   description     description of the ToDo task
     */
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
