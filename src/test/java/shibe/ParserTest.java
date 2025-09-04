package shibe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class ParserTest {
    @Test
    public void testValidEvent() throws MissingArgumentException, InvalidArgumentException {
        String[] input = { "event", "Meeting /from 2025-09-01 /to 2025-09-02" };
        Event event = Parser.parseToEvent(input);

        assertEquals(event.getName(), "Meeting");
        assertEquals(event.isDone(), false);
        assertEquals(event.getStartDate(), LocalDate.parse("2025-09-01"));
        assertEquals(event.getEndDate(), LocalDate.parse("2025-09-02"));
    }

    @Test
    public void testInvalidEventMissingTo() throws InvalidArgumentException {
        String[] input = { "event", "Meeting /from 2025-09-01" };

        assertThrows(MissingArgumentException.class, () -> {
            Parser.parseToEvent(input);
        });
    }

    @Test
    public void testInvalidEventMissingFrom() throws InvalidArgumentException {
        String[] input = { "event", "Meeting /to 2025-09-01" };

        assertThrows(MissingArgumentException.class, () -> {
            Parser.parseToEvent(input);
        });
    }

    @Test
    public void testInvalidEventMissingToAndFrom() throws InvalidArgumentException {
        String[] input = { "event", "Meeting abc" };

        assertThrows(MissingArgumentException.class, () -> {
            Parser.parseToEvent(input);
        });
    }

    @Test
    public void testInvalidEventInvalidDate1() throws MissingArgumentException {
        String[] input = { "event", "Meeting /from 2025-09-01 /to 2025-09-023" };

        assertThrows(InvalidArgumentException.class, () -> {
            Parser.parseToEvent(input);
        });
    }

    @Test
    public void testInvalidEventInvalidDate2() throws MissingArgumentException {
        String[] input = { "event", "Meeting /from abc /to 2025-09-02" };

        assertThrows(InvalidArgumentException.class, () -> {
            Parser.parseToEvent(input);
        });
    }
}
