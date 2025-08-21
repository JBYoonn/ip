import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Penguin {
    public static void main(String[] args) {
        String name = "Penguin";
        String line = "_________________________________";

        System.out.println(line);
        System.out.println("Hello! I'm " + name);
        System.out.println("What can I do for you?");
        System.out.println(line);

        Scanner scanner = new Scanner(System.in);
        List<Task> tasks = new ArrayList<>();

        while (true) {
            String userInput = scanner.nextLine().trim();
            try {
                boolean exit = handle(userInput, tasks, line);
                if (exit) {
                    break;
                }
            } catch (PenguinException e) {
                System.out.println(line);
                System.out.println("OH NO!! " + e.getMessage());
                System.out.println(line);
            }
        }
        scanner.close();
    }

    private static boolean handle(String userInput, List<Task> tasks, String line) throws PenguinException {
        // check for bye
        if (userInput.equals("bye")) {
            System.out.println(line);
            System.out.println("Bye. Hope to see you again soon!");
            System.out.println(line);
            return true;
        }

        // check for list
        if (userInput.equals("list")) {
            System.out.println(line);
            System.out.println("Here are the task(s) in the list:");
            for (int i = 0; i < tasks.size(); i++) {
                int index = i + 1;
                System.out.println(index + "." + tasks.get(i).toString());
            }
            System.out.println(line);
            return false;
        }

        // check for mark
        if (userInput.startsWith("mark ")) {
            int id = parseInt(userInput, 5);
            if (!isValidIndex(line, tasks, id)){
                return false;
            }

            Task t = tasks.get(id - 1);
            System.out.println(line);
            System.out.println("Nice! I've marked this task as done:");
            System.out.println(t.markAsDone());
            System.out.println(line);
            return false;
        }

        // check for unmark
        if (userInput.startsWith("unmark ")) {
            int id = parseInt(userInput, 7);
            if (!isValidIndex(line, tasks, id)) {
                return false;
            }

            Task t = tasks.get(id - 1);
            System.out.println(line);
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println(t.markAsNotDone());
            System.out.println(line);
            return false;
        }

        // check for delete
        if (userInput.startsWith("delete ")) {
            String rest = userInput.substring(7);
            int id;
            try {
                id = Integer.parseInt(rest);
            } catch (NumberFormatException e) {
                throw new PenguinException("Format: delete <task id>");
            }
            if (id < 1 || id > tasks.size()) {
                throw new PenguinException("Task id out of range! (1..." + tasks.size() + ")");
            }
            Task removed = tasks.remove(id - 1);
            System.out.println(line);
            System.out.println("Noted. I've removed this task:");
            System.out.println("  " + removed.toString());
            System.out.println("Now you have " + tasks.size() + " task(s) in the list!");
            System.out.println(line);
            return false;
        }

        // create new task and add to tasks
        // first check for deadline task
        if (userInput.startsWith("deadline ")) {
            String body = userInput.substring(9);
            String[] parts = body.split("\\s*/by\\s*", 2);
            // check if valid
            if (parts.length != 2 || parts[0].isBlank() || parts[1].isBlank()) {
                throw new PenguinException("Format: deadline <description> /by <when>");
            }
            Task t = new Deadline(parts[0], parts[1]);
            tasks.add(t);
            printAdded(line, t, tasks.size());
        } else if (userInput.startsWith("event ")) {
            String body = userInput.substring(6);
            String[] partsFrom = body.split("\\s*/from\\s*", 2);
            String[] partsTo = partsFrom.length == 2
                    ? partsFrom[1].split("\\s*/to\\s*", 2)
                    : new String[0];
            // check if valid
            if (partsFrom.length != 2 || partsTo.length != 2
                    || partsTo[0].isBlank() || partsTo[1].isBlank() || partsFrom[0].isBlank()) {
                throw new PenguinException("Format: event <description> /from <when> /to <when>");
            }
            Task t = new Event(partsFrom[0], partsTo[0], partsTo[1]);
            tasks.add(t);
            printAdded(line, t, tasks.size());
        } else if (userInput.startsWith("todo ")) {
            // TODOS
            String body = userInput.substring(5);
            if (body.isEmpty()) {
                throw new PenguinException("Description cannot be empty!!!");
            }
            Task t = new Todo(body);
            tasks.add(t);
            printAdded(line, t, tasks.size());
        } else {
            if (userInput.equals("todo") || userInput.equals("deadline") ||  userInput.equals("event")) {
                throw new PenguinException("Description cannot be empty!!!");
            }
            throw new PenguinException("""
                    I don't know what that means :( Here is a list of commands:
                    list
                    mark
                    unmark
                    todo
                    deadline
                    event
                    bye""");
        }
        return false;
    }

    private static boolean isValidIndex(String line, List<Task> tasks, int id) {
        if (id <= 0 || id > tasks.size()) {
            System.out.println(line);
            System.out.println("Please provide a valid ID :(");
            System.out.println(line);
            return false;
        }
        return true;
    }

    private static Integer parseInt(String input, int sub) {
        try {
            return Integer.parseInt(input.substring(sub));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static void printAdded(String line, Task task, int size) {
        System.out.println(line);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task.toString());
        System.out.println("Now you have " + size + " task(s) in the list!");
        System.out.println(line);
    }
}
