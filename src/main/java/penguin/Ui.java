package penguin;

import java.util.List;

/**
 * Handles the output of the chatbot.
 */
public class Ui {
    private final StringBuilder buffer = new StringBuilder();

    /**
     * Adds a string to buffer.
     */
    public void say(String s) {
        if (!s.isEmpty()) {
            buffer.append(s).append("\n");
        }
    }

    /**
     * Returns string in buffer and resets buffer
     */
    public String flush() {
        String out = buffer.toString();
        buffer.setLength(0);
        return out;
    }

    /**
     * Adds greeting message to buffer.
     * @param name Name of chatbot
     */
    public void showGreeting(String name) {
        say("Hello! I'm " + name);
        say("What can I do for you?");
    }

    /**
     * Adds goodbye message to buffer.
     */
    public void showGoodBye() {
        say("Good bye. Hope to see you again soon!");
    }

    /**
     * Adds error message to buffer.
     * @param msg Error message
     */
    public void showError(String msg) {
        say("OH NO!! " + msg);
    }

    /**
     * Adds task added message to buffer.
     * @param t Task added
     * @param size Number of task(s) in tasklist
     */
    public void showAddedTask(Task t, int size) {
        say("Got it. I've added this task:");
        say("  " + t);
        say("Now you have " + size + " task(s) in the list!");
    }

    /**
     * Adds tasklist message to buffer.
     * @param taskList Tasklist
     */
    public void showTasks(TaskList taskList) {
        List<Task> tasks = taskList.getTasks();

        say("Here are the task(s) in the list:");
        if (tasks.isEmpty()) {
            say("There is nothing here!");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                say((i + 1) + "." + tasks.get(i));
            }
        }
    }

    /**
     * Adds all matches if any to buffer.
     * @param matches List of tasks with matching keyword
     * @param indices List of indices of tasks with matching keyword
     */
    public void showMatches(List<Task> matches, List<Integer> indices) {
        say("Here are matching tasks in your list:");
        if (matches.isEmpty()) {
            say("There is nothing here!");
        } else {
            for (int i = 0; i < matches.size(); i++) {
                say(indices.get(i) + "." + matches.get(i));
            }
        }
    }

    /**
     * Adds task removed message to buffer.
     * @param t Task removed
     * @param remaining Number of tasks left in tasklist
     */
    public void showRemovedTask(Task t, int remaining) {
        say("Noted. I've removed this task:");
        say("  " + t);
        say("Now you have " + remaining + " task(s) in the list!");
    }

    /**
     * Adds marked task message to buffer.
     * @param t Marked task
     */
    public void showMarked(Task t) {
        say("Nice! I've marked this task as done:");
        say(t.toString());
    }

    /**
     * Adds unmarked task message to buffer.
     * @param t Unmarked task
     */
    public void showUnmarked(Task t) {
        say("OK, I've marked this task as not done yet:");
        say(t.toString());
    }

    /**
     * Adds bad ID message to buffer.
     */
    public void showBadId() {
        say("Please provide a valid ID :(");
    }
}
