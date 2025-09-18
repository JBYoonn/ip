package penguin;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.application.Platform;

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
        // store original System.out
        PrintStream originalOut = System.out;

        // create BAOS to capture output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        // redirect System.out to our PrintStream
        System.setOut(ps);

        String capturedOutput;

        boolean exit = false;

        try {
            // regular operation
            Command command = Parser.parse(input);
            exit = command.execute(taskList, ui, storage);


        } catch (PenguinException e) {
            ui.showError(e.getMessage());
        } finally {
            if (exit) {
                Platform.exit();
            }

            // flush stream to ensure all content is written
            ps.flush();

            // restore the original System.out
            System.setOut(originalOut);

            // get captured output
            capturedOutput = baos.toString();
        }
        return capturedOutput;
    }
}
