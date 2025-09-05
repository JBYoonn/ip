import java.io.IOException;

public class DeleteCommand extends Command {
    public DeleteCommand(String input) {
        super(input);
    }

    @Override
    public boolean execute(TaskList tasks, Ui ui, Storage storage) throws PenguinException {
        int id = Parser.parseIndex(input, 7);
        if (!tasks.isValidIndex(id)) {
            ui.showBadId();
            return false;
        }

        Task removed = tasks.removeAt(id - 1);
        ui.showRemovedTask(removed, tasks.size());

        try {
            storage.save(tasks.getTasks());
        } catch (IOException e) {
            // user might have deleted file mid-run
        }

        return false;
    }
}
