package shibe;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

public class ItemListTest {
    @Test
    public void testEmptyItemList() {
        ItemList items = new ItemList();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        items.listItems();

        assertEquals("No items in this list!\n------------------------------\n", outContent.toString());
    }

    @Test
    public void testItemList1() {
        ItemList items = new ItemList();
        items.addItem(new Todo("Task 1", false));
        items.addItem(new Todo("Task 2", true));
        items.addItem(new Event("Task 3", true, LocalDate.parse("2025-09-01"), LocalDate.parse("2025-09-03")));
        items.addItem(new Deadline("Task 4", true, LocalDate.parse("2025-09-01")));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        items.listItems();

        assertEquals("1. [T][ ] Task 1\n" +
                "2. [T][X] Task 2\n" +
                "3. [E][X] Task 3 (from: 01 September 2025 to 03 September 2025)\n" +
                "4. [D][X] Task 4 (by: 01 September 2025)\n" +
                "------------------------------\n", outContent.toString());
    }
}
