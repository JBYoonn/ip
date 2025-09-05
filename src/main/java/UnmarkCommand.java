import java.io.IOException;

public class UnmarkCommand extends Command {
    public UnmarkCommand(String input) {
        super(input);
    }

    @Override
    public boolean execute(TaskList tasks, Ui ui, Storage storage) throws PenguinException {
        int id = Parser.parseIndex(input, 7);
        if (!tasks.isValidIndex(id)) {
            return false;
        }

        // get task, unmark it and print message
        Task t = tasks.get(id - 1);
        t.markAsNotDone();
        ui.showUnmarked(t);

        try {
            storage.save(tasks.getTasks());
        } catch (IOException e) {
            // file removed mid-run
        }

        return false;
    }
}
