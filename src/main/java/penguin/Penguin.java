package penguin;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.util.Duration;

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

            assert loaded != null : "Loaded task list should never be null";
        } catch (IOException e) {
            loaded = new ArrayList<>();
        }
        this.taskList = new TaskList(loaded);
    }

    public String getResponse(String input) {
        boolean exit = false;

        try {
            // try to parse user input and execute the command respectively
            Command command = Parser.parse(input);
            exit = command.execute(taskList, ui, storage);

            if (exit) {
                // wait for 1.5s before closing
                PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
                delay.setOnFinished(event -> Platform.exit());
                delay.play();
            }
        } catch (PenguinException e) {
            ui.showError(e.getMessage());
        }
        return ui.flush();
    }

    public String getGreeting() {
        ui.showGreeting(NAME);
        return ui.flush();
    }
}
