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
        return "[" + getStatusIcon() + "] " + description;
    }

    public String markAsNotDone() {
        isDone = false;
        return "[" + getStatusIcon() + "] " + description;
    }
}
