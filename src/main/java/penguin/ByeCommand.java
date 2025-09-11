package penguin;

/**
 * Command to end session with the chatbot.
 */
public class ByeCommand extends Command {
    public ByeCommand(String input) {
        super(input);
    }

    @Override
    public boolean execute(TaskList tasks, Ui ui, Storage storage) throws PenguinException {
        ui.showGoodBye();
        return true; // exit
    }
}
