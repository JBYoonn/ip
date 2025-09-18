package penguin;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * A tasks with a specified deadline.
 */
public class Deadline extends Task {
    protected LocalDate by;

    /**
     * Create a deadline task.
     * @param description Description of task
     * @param by Deadline of task
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    public String getBy() {
        return this.by.toString();
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }

    /**
     * AI assistance used: Chat-GPT
     * Check if the object is the same concrete class, description and by date
     */
    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false; // checks same class + description
        }
        Deadline other = (Deadline) o;
        return by.equals(other.by);
    }

    /**
     * AI assistance used: Chat-GPT
     * Hash taking into account task description, by date
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), by);
    }
}
