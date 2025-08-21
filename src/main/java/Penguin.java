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
            String userInput = scanner.nextLine();

            // check for bye
            if (userInput.equals("bye")) {
                System.out.println(line);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            }

            // check for list
            if (userInput.equals("list")) {
                System.out.println(line);
                System.out.println("Here are the tasks in your tasks:");
                for (int i = 0; i < tasks.size(); i++) {
                    int index = i + 1;
                    System.out.println(index + ". [" + tasks.get(i).getStatusIcon() + "] " + tasks.get(i).getDescription());
                }
                System.out.println(line);
                continue;
            }

            // check for mark
            if (userInput.startsWith("mark ")) {
                int id = parseInt(userInput, 5);
                if (!isValidIndex(line, tasks, id)){
                    continue;
                }

                Task t = tasks.get(id - 1);
                System.out.println(line);
                System.out.println("Nice! I've marked this task as done:");
                System.out.println(t.markAsDone());
                System.out.println(line);
                continue;
            }

            // check for unmark
            if (userInput.startsWith("unmark ")) {
                int id = parseInt(userInput, 7);
                if (!isValidIndex(line, tasks, id)) {
                    continue;
                }

                Task t = tasks.get(id - 1);
                System.out.println(line);
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println(t.markAsNotDone());
                System.out.println(line);
                continue;
            }

            // create new task and add to tasks
            Task t = new Task(userInput);
            tasks.add(t);

            System.out.println(line);
            System.out.println("added: " + userInput);
            System.out.println(line);
        }

        scanner.close();
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
}
