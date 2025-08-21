import java.util.ArrayList;

public class ItemList {
    private ArrayList<Item> items;

    public ItemList() {
        this.items = new ArrayList<Item>();
    }

    public void addItem(Item item) {
        this.items.add(item);
        System.out.println("I have added the following item:\n" + item);
    }

    public void listItems() {
        if (this.items.isEmpty()) {
            System.out.println("No items in this list!");
            return;
        }

        for (int i = 0; i < this.items.size(); i++) {
            System.out.println((i + 1) + ". " + this.items.get(i));
        }
    }

    public String findAndDoItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equals(itemName) && !item.isDone()) {
                item.doItem();
                return "You have completed the following item:\n" + item;
            }
        }
        return "The specified item was not found or was already completed.";
    }
}
