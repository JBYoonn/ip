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

        while (true) {
            String userInput = scanner.nextLine();

            if (userInput.equals("bye")) {
                System.out.println(line);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            }

            System.out.println(line);
            System.out.println(userInput);
            System.out.println(line);
        }

        scanner.close();
    }
}
