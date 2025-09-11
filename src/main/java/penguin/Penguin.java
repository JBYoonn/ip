package penguin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Penguin {
    private final String NAME = "Penguin";
    private final Ui ui;
    private final Storage storage;
    private final TaskList taskList;

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

    public void run() {
        ui.showGreeting(this.NAME);

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

    public static void main(String[] args) {
        new Penguin("data", "penguin.txt").run();
    }
}