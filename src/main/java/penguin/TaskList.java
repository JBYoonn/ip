package penguin;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the list of tasks and provides methods to modify it.
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Initialises an empty list of tasks.
     */
    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    /**
     * Initialises a tasklist with a list of tasks pre-assigned.
     * @param tasks Tasks to assign to tasklist
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to tasklist.
     * @param task Task to add to tasklist
     */
    public void add(Task task) {
        this.tasks.add(task);
    }

    /**
     * Removes a task at a specified index in tasklist.
     * @param index Index of task to remove from tasklist
     * @return Task removed
     */
    public Task removeAt(int index) {
        return this.tasks.remove(index);
    }

    /**
     * Returns the number of tasks in the tasklist.
     * @return Number of tasks in tasklist
     */
    public int size() {
        return this.tasks.size();
    }

    /**
     * Returns a task at a specified index of tasklist.
     * @return Task at a specified index
     */
    public Task get(int index) {
        return this.tasks.get(index);
    }

    /**
     * Get the tasklist.
     * @return A list of tasks
     */
    public List<Task> getTasks() {
        return this.tasks;
    }

    /**
     * Check if a given index is within range of tasks index.
     * @return {@code true} if index is valid; {@code false} otherwise
     */
    public boolean isValidIndex(int index) {
        return index > 0 && index <= tasks.size();
    }
}
