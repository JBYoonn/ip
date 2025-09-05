import java.io.IOException;

public class MarkCommand extends Command {
    public MarkCommand(String input) {
        super(input);
    }

    @Override
    public boolean execute(TaskList tasks, Ui ui, Storage storage) throws PenguinException {
        int id = Parser.parseIndex(input, 5); // after "mark "
        if (!tasks.isValidIndex(id)) {
            ui.showBadId();
            return false;
        }

        // get task, mark it and print message
        Task t = tasks.get(id - 1);
        t.markAsDone();
        ui.showMarked(t);

        try {
            storage.save(tasks.getTasks());
        } catch (IOException e) {
            // file removed mid-run
        }

        return false;
    }
}
