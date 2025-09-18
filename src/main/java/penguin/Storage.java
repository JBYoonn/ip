package penguin;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the encoding/decoding of tasklist in {@code ./data/<fileName>} on startup.
 * Creates {@code data/} directory and file if it is missing.
 */
public class Storage {
    private final Path dir;
    private final Path file;

    /**
     * Initialises storage with a directory and fileName of the save file.
     * @param directory Directory of save file
     * @param fileName Name of save file
     */
    public Storage(String directory, String fileName) {
        this.dir = Paths.get(directory);
        this.file = dir.resolve(fileName);
    }

    /**
     * Loads tasks from save file.
     * Returns an empty list if the file does not exist.
     *
     * @return List of tasks saved
     */
    public List<Task> load() throws IOException {
        if (Files.notExists(file)) {
            if (Files.notExists(dir)) {
                Files.createDirectories(dir);
            }
            Files.createFile(file);
            return new ArrayList<>();
        }

        List<String> lines = Files.readAllLines(file);
        List<Task> tasks = new ArrayList<>();
        int badLines = 0;

        for (String line : lines) {
            try {
                tasks.add(decode(line));
            } catch (CorruptedLineException e) {
                badLines++;
            }
        }

        if (badLines > 0) {
            System.err.println("Warning: Skipped " + badLines + " corrupted line(s)!");
        }
        return tasks;
    }

    /**
     * Overwrites save file with the given list of tasks.
     */
    public void save(List<Task> tasks) throws IOException {
        List<String> lines = new ArrayList<>();

        for (Task t : tasks) {
            lines.add(encode(t));
        }

        Files.write(
                file,
                lines,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        );
    }

    private String encode(Task t) {
        if (t instanceof Todo todo) {
            return encodeTodo(todo);
        } else if (t instanceof Deadline d) {
            return encodeDeadline(d);
        } else if (t instanceof Event e) {
            return encodeEvent(e);
        } else {
            return String.join(
                    " | ",
                    "?",
                    t.isDone ? "1" : "0",
                    t.toString()
            );
        }
    }

    private Task decode(String line) throws CorruptedLineException {
        String[] p = line.split("\\s*\\|\\s*");
        if (p.length < 3) {
            throw new CorruptedLineException("too few fields");
        }

        String type = p[0].trim();

        switch (type) {
        case "T" -> {
            return decodeTodo(p);
        }
        case "D" -> {
            return decodeDeadline(p);
        }
        case "E" -> {
            return decodeEvent(p);
        }
        default -> {
            throw new CorruptedLineException("unknown type: " + type);
        }
        }
    }

    private String encodeTodo(Todo t) {
        return String.join(
                " | ",
                "T",
                t.isDone ? "1" : "0",
                t.getDescription()
        );
    }

    private String encodeDeadline(Deadline d) {
        return String.join(
                " | ",
                "D",
                d.isDone ? "1" : "0",
                d.getDescription(),
                d.getBy()
        );
    }

    private String encodeEvent(Event e) {
        return String.join(
                " | ",
                "E",
                e.isDone ? "1" : "0",
                e.getDescription(),
                e.getFrom(),
                e.getTo()
        );
    }

    private Boolean isDone(String doneStr) throws CorruptedLineException {
        if (doneStr.equals("1")) {
            return true;
        } else if (doneStr.equals("0")) {
            return false;
        } else {
            throw new CorruptedLineException("done must be either 0 or 1");
        }
    }

    private Task decodeTodo(String[] input) throws CorruptedLineException {
        String doneStr = input[1].trim();
        Boolean done = isDone(doneStr);
        if (input.length != 3) {
            throw new CorruptedLineException("Todo needs 3 fields!");
        }
        Todo t = new Todo(input[2]);
        if (done) {
            t.markAsDone();
        }
        return t;
    }

    private Task decodeDeadline(String[] input) throws CorruptedLineException {
        String doneStr = input[1].trim();
        Boolean done = isDone(doneStr);
        if (input.length != 4) {
            throw new CorruptedLineException("Deadline needs 4 fields!");
        }
        LocalDate deadlineDate = LocalDate.parse(input[3]);
        Deadline d = new Deadline(input[2], deadlineDate);
        if (done) {
            d.markAsDone();
        }
        return d;
    }

    private Task decodeEvent(String[] input) throws CorruptedLineException {
        String doneStr = input[1].trim();
        Boolean done = isDone(doneStr);
        if (input.length != 5) {
            throw new CorruptedLineException("Event needs 5 fields!");
        }
        Event e = new Event(input[2], input[3], input[4]);
        if (done) {
            e.markAsDone();
        }
        return e;
    }

}
