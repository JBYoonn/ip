package penguin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TodoTest {
    @Test
    public void toString_formatsCorrectly() {
        Todo todo = new Todo("Do this");
        assertEquals("[T][ ] Do this", todo.toString());

        todo.markAsDone();
        assertEquals("[T][X] Do this", todo.toString());
    }
}
