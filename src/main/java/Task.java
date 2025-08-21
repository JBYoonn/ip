public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    public String markAsDone() {
        isDone = true;
        return toString();
    }

    public String markAsNotDone() {
        isDone = false;
        return toString();
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
