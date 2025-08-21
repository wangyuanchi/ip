import java.util.ArrayList;

public class TodoList {
    private ArrayList<Item> items;

    public TodoList() {
        this.items = new ArrayList<Item>();
    }

    public void addItem(String itemName) {
        this.items.add(new Item(itemName, false));
        System.out.println("Added " + itemName + "!");
    }

    public void listItems() {
        if (this.items.isEmpty()) {
            System.out.println("All items completed!");
            return;
        }

        for (int i = 0; i < this.items.size(); i++) {
            String doneStatus = this.items.get(i).isDone() ? "X" : " ";
            System.out.println((i + 1) + ". [" + doneStatus + "] " + this.items.get(i));
        }
    }

    public String findAndDoItem(String itemName) {
        for (Item item : items) {
            if (item.toString().equals(itemName) && !item.isDone()) {
                item.doItem();
                return "Completed " + itemName + ".";
            }
        }
        return "No uncompleted " + itemName + " found.";
    }
}
