package penguin;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Task)) {
            return false;
        }
        Task other = (Task) obj;
        return description.equals(other.description);
    }
}
