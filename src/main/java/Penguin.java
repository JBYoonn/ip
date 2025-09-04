import java.io.IOException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
        Save save = new Save("data", "penguin.txt");
        List<Task> tasks;

        try {
            tasks = save.load();
        } catch (IOException e) {
            tasks = new ArrayList<>();
        }

        while (true) {
            String userInput = scanner.nextLine().trim();
            try {
                boolean exit = handle(userInput, tasks, line, save);
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

    private static boolean handle(String userInput, List<Task> tasks, String line, Save save) throws PenguinException {
        Command cmd = Command.of(userInput);

        switch (cmd) {
            case BYE -> {
                System.out.println(line);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(line);
                return true;
            }
            case LIST -> {
                System.out.println(line);
                System.out.println("Here are the task(s) in the list:");
                for (int i = 0; i < tasks.size(); i++) {
                    int index = i + 1;
                    System.out.println(index + "." + tasks.get(i).toString());
                }
                if (tasks.isEmpty()) {
                    System.out.println("There is nothing here!");
                }
                System.out.println(line);
                return false;
            }
            case MARK -> {
                int id = parseInt(userInput, 5);
                if (!isValidIndex(line, tasks, id)){
                    return false;
                }

                Task t = tasks.get(id - 1);
                System.out.println(line);
                System.out.println("Nice! I've marked this task as done:");
                System.out.println(t.markAsDone());
                System.out.println(line);

                try {
                    save.save(tasks);
                } catch (IOException e) {
                    // should not happen, unless user deletes file mid run
                    return false;
                }

                return false;
            }
            case UNMARK -> {
                int id = parseInt(userInput, 7);
                if (!isValidIndex(line, tasks, id)) {
                    return false;
                }

                Task t = tasks.get(id - 1);
                System.out.println(line);
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println(t.markAsNotDone());
                System.out.println(line);

                try {
                    save.save(tasks);
                } catch (IOException e) {
                    // should not happen, unless file deleted mid run
                    return false;
                }

                return false;
            }
            case DELETE -> {
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

                try {
                    save.save(tasks);
                } catch (IOException e) {
                    // should not happen, unless file deleted mid run
                    return false;
                }

                return false;
            }
            case DEADLINE -> {
                String body = userInput.substring(9);
                String[] parts = body.split("\\s*/by\\s*", 2);
                LocalDate date;
                // check if valid
                if (parts.length != 2 || parts[0].isBlank() || parts[1].isBlank()) {
                    throw new PenguinException("Format: deadline <description> /by <yyyy-mm-dd>");
                }
                try {
                    date = LocalDate.parse(parts[1]);
                } catch (DateTimeParseException e) {
                    throw new PenguinException("Invalid date! Format: deadline <description> /by <yyyy-mm-dd>");
                }
                Task t = new Deadline(parts[0], date);
                tasks.add(t);
                printAdded(line, t, tasks.size());

                try {
                    save.save(tasks);
                } catch (IOException e) {
                    // should not happen, unless file deleted mid run
                    return false;
                }

                return false;
            }
            case EVENT -> {
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

                try {
                    save.save(tasks);
                } catch (IOException e) {
                    // should not happen, unless file deleted mid run
                    return false;
                }

                return false;
            }
            case TODO -> {
                String body = userInput.substring(5);
                if (body.isEmpty()) {
                    throw new PenguinException("Description cannot be empty!!!");
                }
                Task t = new Todo(body);
                tasks.add(t);
                printAdded(line, t, tasks.size());

                try {
                    save.save(tasks);
                } catch (IOException e) {
                    // should not happen, unless file deleted mid run
                    return false;
                }

                return false;
            }
            default -> {
                if (userInput.equals("todo") || userInput.equals("event") || userInput.equals("deadline")) {
                    throw new PenguinException("Description cannot be empty!!!");
                } else {
                    throw new PenguinException(
                            "I don't know what that means :( " + Command.getAllCommands()
                    );
                }
            }
        }
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
