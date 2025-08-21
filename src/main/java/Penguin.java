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
        List<String> list = new ArrayList<String>();

        while (true) {
            String userInput = scanner.nextLine();

            if (userInput.equals("bye")) {
                System.out.println(line);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            }

            if (userInput.equals("list")) {
                System.out.println(line);
                for (int i = 0; i < list.size(); i++) {
                    int index = i + 1;
                    System.out.println(index + ". " + list.get(i));
                }
                System.out.println(line);
                continue;
            }

            // add user input
            list.add(userInput);

            System.out.println(line);
            System.out.println("added: " + userInput);
            System.out.println(line);
        }

        scanner.close();
    }
}
