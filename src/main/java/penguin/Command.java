package penguin;

public abstract class Command {
    protected final String input;

    public Command(String input) {
        this.input = input;
    }

    public abstract boolean execute(TaskList tasks, Ui ui, Storage storage) throws PenguinException;
}
