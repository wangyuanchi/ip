package shibe;

import java.util.ArrayList;
import java.util.Arrays;

public class ItemList {
    private ArrayList<Item> items;

    public ItemList() {
        this.items = new ArrayList<Item>();
    }

    public void addItem(Item item) {
        this.items.add(item);
        UI.respond("I have added the following item:\n" + item);
    }

    public void addItemSilent(Item item) {
        this.items.add(item);
    }

    public void listItems() {
        if (this.items.isEmpty()) {
            UI.respond("No items in this list!");
            return;
        }

        String output = "";
        for (int i = 0; i < this.items.size(); i++) {
            output += (i + 1) + ". " + this.items.get(i);
            if (i != this.items.size() - 1) {
                output += "\n";
            }
        }
        UI.respond(output);
    }

    /**
     * Finds all items in the item list that contains the itemName with 1-based
     * indexing.
     * 
     * @param itemName The name of the item to match against.
     */
    public void findItems(String itemName) {
        String output = "";
        for (int i = 0; i < this.items.size(); i++) {
            if (this.items.get(i).getName().contains(itemName)) {
                output += (i + 1) + ". " + this.items.get(i);
                if (i != this.items.size() - 1) {
                    output += "\n";
                }
            }
        }

        if (output == "") {
            UI.respond("No item matched your specified item name.");
        } else {
            UI.respond("Here are the items that matched your item name:\n" + output);
        }
    }

    public boolean runCommand(Writer writer, String input) throws MissingArgumentException, InvalidArgumentException {
        String[] inputArray = input.split(" ", 2);
        String command = inputArray[0].toLowerCase();

        // Expected format for writing to file: command,,id,,name,,done,,optional
        switch (command) {
        case Todo.COMMAND:
            Todo todoItem = Parser.parseToTodo(inputArray);
            if (writer.writeToFileNewLine(
                    Arrays.asList("todo", todoItem.getID(), todoItem.getName(), String.valueOf(todoItem.isDone())))) {
                this.addItem(todoItem);
            }
            break;

        case Deadline.COMMAND:
            Deadline deadlineItem = Parser.parseToDeadline(inputArray);
            if (writer.writeToFileNewLine(Arrays.asList("deadline", deadlineItem.getID(), deadlineItem.getName(),
                    String.valueOf(deadlineItem.isDone()), deadlineItem.getDate().toString()))) {
                this.addItem(deadlineItem);
            }
            break;

        case Event.COMMAND:
            Event eventItem = Parser.parseToEvent(inputArray);
            if (writer.writeToFileNewLine(
                    Arrays.asList("event", eventItem.getID(), eventItem.getName(), String.valueOf(eventItem.isDone()),
                            eventItem.getStartDate().toString(), eventItem.getEndDate().toString()))) {
                this.addItem(eventItem);
            }
            break;

        case "list":
            this.listItems();
            break;

        case "find":
            this.findItems(Parser.parseToItemName(inputArray));
            break;

        case "do":
            this.findAndDoItem(writer, Parser.parseToItemName(inputArray));
            break;

        case "delete":
            this.deleteItem(writer, Parser.parseToValidIndex(inputArray));
            break;

        case "bye":
            UI.respond("Bye. Hope to see you again soon!");
            return true;

        default:
            UI.respond("No such command!");
        }
        return false;
    }

    public void findAndDoItem(Writer writer, String itemName) {
        for (Item item : this.items) {
            if (item.getName().equals(itemName) && !item.isDone()) {
                if (writer.writeToFileDoItem(item.getID())) {
                    item.doItem();
                    UI.respond("You have completed the following item:\n" + item);
                    return;
                }
            }
        }
        UI.respond("The specified item was not found or was already completed.");
    }

    public void deleteItem(Writer writer, int itemIndex) {
        if (this.items.isEmpty()) {
            UI.respond("There is nothing to delete!");
            return;
        }

        itemIndex = itemIndex - 1;

        if (itemIndex < 0 || itemIndex > this.items.size() - 1) {
            UI.respond("Invalid item index!");
            return;
        }

        Item item = this.items.get(itemIndex);
        if (writer.writeToFileDeleteItem(item.getID())) {
            this.items.remove(itemIndex);
        }

        UI.respond("The following item was deleted:\n" + item);
    }
}
