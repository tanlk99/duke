class Event extends Task {
    protected String time;

    public Event(String description, String time) {
        super(description);
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + time + ")";
    }

    public String toStorageString() {
        return "E \\ " + (super.isDone ? "1 \\ " : "0 \\ ") + super.description + " \\ " + time;
    }
}
