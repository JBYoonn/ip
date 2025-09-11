package penguin;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class DeadlineTest {
    @Test
    public void toString_formatsCorrectly() {
        Deadline deadline = new Deadline("Assignment", LocalDate.parse("2025-10-10"));
        assertEquals("[D][ ] Assignment (by: Oct 10 2025)", deadline.toString());

        deadline.markAsDone();
        assertEquals("[D][X] Assignment (by: Oct 10 2025)", deadline.toString());
    }
}
