package penguin;

/**
 * A task with a specified duration.
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Create an Event task with a description and specified duration.
     * @param description Description of task
     * @param from Start of event
     * @param to End of event
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return this.to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
