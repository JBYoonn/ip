public enum Command {
    BYE("bye"),
    LIST("list"),
    MARK("mark "),
    UNMARK("unmark "),
    DELETE("delete "),
    TODO("todo "),
    DEADLINE("deadline "),
    EVENT("event "),
    UNKNOWN("");

    private final String key;

    Command(String key) {
        this.key = key;
    }

    public static Command of(String input) {
        String s = input.toLowerCase();
        if (s.equals("bye")) return BYE;
        if (s.equals("list")) return LIST;
        if (s.startsWith("mark ")) return MARK;
        if (s.startsWith("unmark ")) return UNMARK;
        if (s.startsWith("delete ")) return DELETE;
        if (s.startsWith("todo ")) return TODO;
        if (s.startsWith("deadline ")) return DEADLINE;
        if (s.startsWith("event ")) return EVENT;
        return UNKNOWN;
    }
}
