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
        tasks.add(t);
        ui.showAddedTask(t, tasks.size());

        try {
            storage.save(tasks.getTasks());
        } catch (IOException e) {
            // user might have deleted file mid-run
        }

        return false;
    }
}
