package penguin;

import java.util.List;

public class Ui {
    private final String LINE = "_________________________________";

    public void showLine() {
        System.out.println(LINE);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showGreeting(String name) {
        showLine();
        System.out.println("Hello! I'm " + name);
        System.out.println("What can I do for you?");
        showLine();
    }

    public void showGoodBye() {
        showLine();
        System.out.println("Good bye. Hope to see you again soon!");
        showLine();
    }

    public void showError(String msg) {
        showLine();
        System.out.println("OH NO!! " + msg);
        showLine();
    }

    public void showAddedTask(Task t, int size) {
        showLine();
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + t);
        System.out.println("Now you have " + size + " task(s) in the list!");
        showLine();
    }

    public void showTasks(TaskList taskList) {
        List<Task> tasks = taskList.getTasks();
        showLine();
        System.out.println("Here are the task(s) in the list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
        if (tasks.isEmpty()) System.out.println("There is nothing here!");
        showLine();
    }

    public void showMatches(List<Task> matches, List<Integer> indices) {
        showLine();
        System.out.println("Here are matching tasks in your list:");

        if (matches.isEmpty()) {
            System.out.println("There is nothing here!");
        } else {
            for (int i = 0; i < matches.size(); i++) {
                System.out.println(indices.get(i) + "." + matches.get(i));
            }
        }
        showLine();
    }

    public void showRemovedTask(Task t, int remaining) {
        showLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + t);
        System.out.println("Now you have " + remaining + " task(s) in the list!");
        showLine();
    }

    public void showMarked(Task t) {
        showLine();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(t);
        showLine();
    }

    public void showUnmarked(Task t) {
        showLine();
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(t);
        showLine();
    }

    public void showBadId() {
        showLine();
        System.out.println("Please provide a valid ID :(");
        showLine();
    }
}
