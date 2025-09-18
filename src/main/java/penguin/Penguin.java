package penguin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main entry point of the application.
 */
public class Penguin {
    private static final String NAME = "Penguin";
    private final Ui ui;
    private final Storage storage;
    private final TaskList taskList;

    /**
     * Initialises the chatbot and loads in saved tasks from the disk.
     * @param directory Directory of saved file
     * @param fileName Name of save file
     */
    public Penguin(String directory, String fileName) {
        this.ui = new Ui();
        this.storage = new Storage(directory, fileName);

        // try to load saved taskList
        List<Task> loaded;
        try {
            loaded = storage.load();
        } catch (IOException e) {
            loaded = new ArrayList<>();
        }
        this.taskList = new TaskList(loaded);
    }

    /**
     * Starts the interaction between user and chatbot until the user exits.
     */
    public void run() {
        ui.showGreeting(NAME);

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String userInput = scanner.nextLine().trim();

                try {
                    Command command = Parser.parse(userInput);
                    boolean exit = command.execute(taskList, ui, storage);

                    if (exit) {
                        break;
                    }
                } catch (PenguinException e) {
                    ui.showError(e.getMessage());
                }
            }
        }
    }

    public String getResponse(String input) {
        return "Penguin heard: " + input;
    }

    public static void main(String[] args) {
        new Penguin("data", "penguin.txt").run();
    }
}
