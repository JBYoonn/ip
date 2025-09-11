package penguin;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Parses user input into it's respective {@code Command}
 */
public class Parser {
    private static final Map<String, Function<String, Command>> COMMANDS = new LinkedHashMap<>();

    static {
        COMMANDS.put("bye", ByeCommand::new);
        COMMANDS.put("list", ListCommand::new);
        COMMANDS.put("mark", MarkCommand::new);
        COMMANDS.put("unmark", UnmarkCommand::new);
        COMMANDS.put("delete", DeleteCommand::new);
        COMMANDS.put("todo", TodoCommand::new);
        COMMANDS.put("deadline", DeadlineCommand::new);
        COMMANDS.put("event", EventCommand::new);
    }

    /**
     * Parses an input string.
     * @param input Raw user input
     * @return Command respective to input
     * @throws PenguinException If input is not recognised
     */
    public static Command parse(String input) throws PenguinException {
        String lower = input.toLowerCase();
        for (String key : COMMANDS.keySet()) {
            if (lower.equals(key) || lower.startsWith(key + " ")) {
                return COMMANDS.get(key).apply(input);
            }
        }
        throw new PenguinException("I don't know what that means :( " + getAllCommands());
    }

    /**
     * Parses an input string to a number.
     * @param input Raw input
     * @param prefixLength Length of substring
     * @return Respective integer
     * @throws PenguinException If unable to parse the substring
     */
    public static int parseIndex(String input, int prefixLength) throws PenguinException {
        String rest = input.substring(prefixLength).trim();
        try {
            return Integer.parseInt(rest);
        } catch (NumberFormatException e) {
            throw new PenguinException("Please provide a valid task id (integer).");
        }
    }

    /**
     * Returns a string containing all the commands neatly formatted.
     * @return String containing all commands
     */
    public static String getAllCommands() {
        StringBuilder sb = new StringBuilder("Here are the available commands:");
        for (String key : COMMANDS.keySet()) {
            sb.append("\n").append(key);
        }
        return sb.toString();
    }

    /**
     * Parses a input string into a {@link Todo} task.
     * @param input Raw input
     * @return A Task representing the parsed {@link Todo}
     * @throws PenguinException If the description is missing or blank
     */
    public static Task parseTodo(String input) throws PenguinException {
        String body = input.length() >= 5 ? input.substring(5).trim() : "";
        if (body.isEmpty()) {
            throw new PenguinException("Description cannot be empty!!!");
        }
        return new Todo(body);
    }

    /**
     * Parses a input string into a {@link Deadline} task.
     * @param input Raw input
     * @return A Task representing the parsed {@link Deadline}
     * @throws PenguinException If the description or date is missing/blank,
     * or if the date is not a valid ISO {@code yyyy-MM-dd} value
     */
    public static Task parseDeadline(String input) throws PenguinException {
        String body = input.length() >= 9 ? input.substring(9).trim() : "";
        String[] parts = body.split("\\s*/by\\s*", 2);

        if (parts.length != 2 || parts[0].isBlank() || parts[1].isBlank()) {
            throw new PenguinException("Format: deadline <description> /by <yyyy-mm-dd>");
        }

        try {
            LocalDate date = LocalDate.parse(parts[1].trim());
            return new Deadline(parts[0].trim(), date);
        } catch (DateTimeParseException e) {
            throw new PenguinException("Invalid date! Format: deadline <description> /by <yyyy-mm-dd>");
        }
    }

    /**
     * Parses a input string into a {@link Event} task.
     * @param input Raw input
     * @return A Task representing the parsed {@link Event}
     * @throws PenguinException If the description or duration is missing/blank
     */
    public static Task parseEvent(String input) throws PenguinException {
        String body = input.length() >= 6 ? input.substring(6).trim() : "";

        String[] partsFrom = body.split("\\s*/from\\s*", 2);
        String[] partsTo = partsFrom.length == 2
                ? partsFrom[1].split("\\s*/to\\s*", 2)
                : new String[0];

        if (partsFrom.length != 2 || partsTo.length != 2
                || partsFrom[0].isBlank() || partsTo[0].isBlank() || partsTo[1].isBlank()) {
            throw new PenguinException("Format: event <description> /from <when> /to <when>");
        }

        return new Event(partsFrom[0].trim(), partsTo[0].trim(), partsTo[1].trim());
    }
}
