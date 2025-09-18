package penguin;

import java.util.Objects;

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

    /**
     * AI assistance used: Chat-GPT
     * Check if the object is the same concrete class, description and from/to
     */
    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false; // checks class + description
        }
        Event other = (Event) o;
        return from.equalsIgnoreCase(other.from) && to.equalsIgnoreCase(other.to);
    }

    /**
     * AI assistance used: Chat-GPT
     * Hash taking into account task description, from/to
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), from.toLowerCase(), to.toLowerCase());
    }
}
