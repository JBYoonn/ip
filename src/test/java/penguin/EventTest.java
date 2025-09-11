package penguin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EventTest {
    @Test
    public void toString_formatsCorrectly() {
        Event event = new Event("Career fair", "this day", "that day");
        assertEquals("[E][ ] Career fair (from: this day to: that day)", event.toString());

        event.markAsDone();
        assertEquals("[E][X] Career fair (from: this day to: that day)", event.toString());
    }
}
