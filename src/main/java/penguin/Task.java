package penguin;

import java.util.Objects;

/**
 * Task with a description and a checkbox.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a task with a description.
     * @param description Description of task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Get the description of the task.
     * @return Description of the task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the status of the task.
     * @return Status of task in a string. "X" if done, " " otherwise
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Marks a task as done
     * @return toString() of task
     */
    public String markAsDone() {
        isDone = true;
        return toString();
    }

    /**
     * Marks a task as undone
     * @return toString() of task
     */
    public String markAsNotDone() {
        isDone = false;
        return toString();
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * AI assistance used: Chat-GPT
     * Check if the object is the same concrete class and have the same description
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Task)) {
            return false;
        }
        Task other = (Task) obj;
        return getClass().equals(other.getClass())
                && description.equalsIgnoreCase(other.description);
    }

    /**
     * AI assistance used: Chat-GPT
     * Hash taking into account task description
     */
    @Override
    public int hashCode() {
        return Objects.hash(getClass(), description.toLowerCase());
    }
}
