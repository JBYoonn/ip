package penguin;

import java.io.IOException;

public class EventCommand extends Command {
    public EventCommand(String input) {
        super(input);
    }

    @Override
    public boolean execute(TaskList tasks, Ui ui, Storage storage) throws PenguinException {
        Task t = Parser.parseEvent(input);
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
