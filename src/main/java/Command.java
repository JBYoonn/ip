public abstract class Command {
    protected final String input;

    public Command(String input) {
        this.input = input;
    }

    public abstract boolean execute(TaskList tasks, Ui ui, Storage storage) throws PenguinException;
//    BYE("bye"),
//    LIST("list"),
//    MARK("mark "),
//    UNMARK("unmark "),
//    DELETE("delete "),
//    TODO("todo "),
//    DEADLINE("deadline "),
//    EVENT("event "),
//    UNKNOWN("");
}
