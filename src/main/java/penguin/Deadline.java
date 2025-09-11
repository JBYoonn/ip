package penguin;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
}
