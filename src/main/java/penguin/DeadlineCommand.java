package penguin;

import java.io.IOException;

/**
 * Command to create and add a Deadline task to tasklist.
 */
public class DeadlineCommand extends Command {
    public DeadlineCommand(String input) {
        super(input);
    }

    @Override
    public boolean execute(TaskList tasks, Ui ui, Storage storage) throws PenguinException {
        Task t = Parser.parseDeadline(input);
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
