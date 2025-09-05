import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final Path dir;
    private final Path file;

    public Storage(String directory, String fileName) {
        this.dir = Paths.get(directory);
        this.file = dir.resolve(fileName);
    }

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
        String done = t.isDone ? "1" : "0";

        if (t instanceof Todo todo) {
            return String.join(
                    " | ",
                    "T",
                    done,
                    todo.getDescription()
            );
        } else if (t instanceof Deadline d) {
            return String.join(
                    " | ",
                    "D",
                    done,
                    d.getDescription(),
                    d.getBy()
            );
        } else if (t instanceof Event e) {
            return String.join(
                    " | ",
                    "E",
                    done,
                    e.getDescription(),
                    e.getFrom(),
                    e.getTo()
            );
        } else {
            return String.join(
                    " | ",
                    "?",
                    done,
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
        String doneStr = p[1].trim();
        boolean done;

        if (doneStr.equals("1")) {
            done = true;
        } else if (doneStr.equals("0")) {
            done = false;
        } else {
            throw new CorruptedLineException("done must be either 0 or 1");
        }

        switch (type) {
            case "T" -> {
                if (p.length != 3) {
                    throw new CorruptedLineException("Todo needs 3 fields!");
                }
                Todo t = new Todo(p[2]);
                if (done) {
                    t.markAsDone();
                }
                return t;
            }
            case "D" -> {
                if (p.length != 4) {
                    throw new CorruptedLineException("Deadline needs 4 fields!");
                }
                LocalDate deadlineDate = LocalDate.parse(p[3]);
                Deadline d = new Deadline(p[2], deadlineDate);
                if (done) {
                    d.markAsDone();
                }
                return d;
            }
            case "E" -> {
                if (p.length != 5) {
                    throw new CorruptedLineException("Event needs 5 fields!");
                }
                Event e = new Event(p[2], p[3], p[4]);
                if (done) {
                    e.markAsDone();
                }
                return e;
            }
            default -> {
                    throw new CorruptedLineException("unknown type: " + type);
            }
        }
    }
}
