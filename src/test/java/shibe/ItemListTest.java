package shibe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class ItemListTest {
    @Test
    public void testEmptyItemList() {
        ItemList items = new ItemList();

        String res = items.listItems();
        assertEquals("No items in this list!\n------------------------------\n", res);
    }

    @Test
    public void testItemList1() {
        ItemList items = new ItemList();
        items.addItem(new Todo("Task 1", false));
        items.addItem(new Todo("Task 2", true));
        items.addItem(new Event("Task 3", true, LocalDate.parse("2025-09-01"), LocalDate.parse("2025-09-03")));
        items.addItem(new Deadline("Task 4", true, LocalDate.parse("2025-09-01")));

        String res = items.listItems();
        assertEquals("1. [T][ ] Task 1\n" + "2. [T][X] Task 2\n"
                + "3. [E][X] Task 3 (from: 01 September 2025 to 03 September 2025)\n"
                + "4. [D][X] Task 4 (by: 01 September 2025)\n" + "------------------------------\n", res);
    }
}
