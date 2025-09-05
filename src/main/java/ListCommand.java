public class ListCommand extends Command {
    public ListCommand(String input) {
        super(input);
    }

    @Override
    public boolean execute(TaskList tasks, Ui ui, Storage storage) throws PenguinException {
        ui.showTasks(tasks);
        return false;
    }
}
