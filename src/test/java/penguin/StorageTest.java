package penguin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class StorageTest {
    @TempDir
    Path tempDir;

    @Test
    public void load_whenFileMissing_createsFileAndReturnsEmpty() throws IOException {
        Storage storage = new Storage(tempDir.toString(), "penguin.txt");

        List<Task> tasks = storage.load();

        assertTrue(tasks.isEmpty());
        assertTrue(Files.exists(tempDir.resolve("penguin.txt")), "File should be created on first load");
    }

    @Test
    public void saveAndLoad_roundTrip_allTypesAndDoneFlags() throws IOException {
        Storage storage = new Storage(tempDir.toString(), "data.txt");

        // build list of tasks
        List<Task> toSave = new ArrayList<>();

        Todo todo = new Todo("Buy milk"); // undone
        toSave.add(todo);

        Deadline deadline = new Deadline("Assignment", LocalDate.parse("2025-10-10"));
        deadline.markAsDone();
        toSave.add(deadline);

        Event event = new Event("Career fair", "this day", "that day"); // undone
        toSave.add(event);

        storage.save(toSave);

        // create new instance of storage to read from disk
        Storage storage2 = new Storage(tempDir.toString(), "data.txt");
        List<Task> loaded = storage2.load();

        assertEquals(3, loaded.size(), "Should load the same number of tasks");

        assertEquals("[T][ ] Buy milk", loaded.get(0).toString());
        assertEquals("[D][X] Assignment (by: Oct 10 2025)", loaded.get(1).toString());
        assertEquals("[E][ ] Career fair (from: this day to: that day)", loaded.get(2).toString());
    }

    @Test
    public void load_skipsCorruptedLines_butParsesValidOnes() throws IOException {
        Path file = tempDir.resolve("mixed.txt");

        // tasks with 1 corrupted line
        List<String> lines = List.of(
                "T | 0 | Do this",
                "D | 1 | Assign | 2025-12-01",
                "E | 0 | Career | from-only",
                "X | 0 | Unknown",
                "T | 2 | Bad done",
                "garbage",
                "E | 1 | Conf | today | tomorrow"
        );
        Files.write(file, lines, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        Storage storage = new Storage(tempDir.toString(), "mixed.txt");
        List<Task> loaded = storage.load();

        // expect only the 3 valid lines to load
        assertEquals(3, loaded.size(), "Only valid lines should be loaded");

        assertEquals("[T][ ] Do this", loaded.get(0).toString());
        assertEquals("[D][X] Assign (by: Dec 1 2025)", loaded.get(1).toString());
        assertEquals("[E][X] Conf (from: today to: tomorrow)", loaded.get(2).toString());
    }

    @Test
    public void save_truncatesExistingFile() throws IOException {
        Path file = tempDir.resolve("truncate.txt");

        // write some tasks
        Files.write(file, List.of(
                "T | 0 | Old 1",
                "T | 1 | Old 2",
                "T | 0 | Old 3"
        ), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        Storage storage = new Storage(tempDir.toString(), "truncate.txt");

        // save one task and ensure file is truncated not appended
        List<Task> toSave = new ArrayList<>();
        toSave.add(new Todo("New only"));
        storage.save(toSave);

        List<String> now = Files.readAllLines(file, StandardCharsets.UTF_8);
        assertEquals(1, now.size(), "File should be truncated to new content length");
        assertEquals("T | 0 | New only", now.get(0));
    }
}
