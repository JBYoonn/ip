package penguin;

import java.io.IOException;

/**
 * Command to create a Todo task and add it to the tasklist.
 */
public class TodoCommand extends Command {
    public TodoCommand(String input) {
        super(input);
    }

    @Override
    public boolean execute(TaskList tasks, Ui ui, Storage storage) throws PenguinException {
        Task t = Parser.parseTodo(input);

        // AI assistance: Chat-GPT improved duplicate handling
        if (!tasks.addIfAbsent(t)) {
            ui.say("Chill. You can only do the same task once.");
            return false;
        }

        ui.showAddedTask(t, tasks.size());

        try {
            storage.save(tasks.getTasks());
        } catch (IOException e) {
            // user might have deleted file mid-run
        }

        return false;
    }
}
