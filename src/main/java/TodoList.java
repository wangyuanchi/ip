import java.util.ArrayList;

public class TodoList {
    private ArrayList<String> items;

    public TodoList() {
        this.items = new ArrayList<String>();
    }

    public void addItem(String item) {
        this.items.add(item);
        System.out.println("Added " + item + "!");
    }

    public void listItems() {
        if (this.items.isEmpty()) {
            System.out.println("All items completed!");
            return;
        }

        for (int i = 0; i < this.items.size(); i++) {
            System.out.println((i + 1) + ". " + this.items.get(i));
        }
    }
}
