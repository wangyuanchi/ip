package shibe;

import java.util.ArrayList;
import java.util.Arrays;

public class ItemList {
    private ArrayList<Item> items;

    public ItemList() {
        this.items = new ArrayList<Item>();
    }

    /**
     * Adds the given item to the item list and responds with a success message.
     *
     * @param item The item to add.
     */
    public void addItem(Item item) {
        this.items.add(item);
        Ui.respond("I have added the following item:\n" + item);
    }

    /**
     * Adds the given item to the item list and does not respond with a success
     * message.
     *
     * @param item The item to add.
     */
    public void addItemSilent(Item item) {
        this.items.add(item);
    }

    /**
     * Lists all items in the item list with 1-based indexing.
     */
    public void listItems() {
        if (this.items.isEmpty()) {
            Ui.respond("No items in this list!");
            return;
        }

        String output = "";
        for (int i = 0; i < this.items.size(); i++) {
            output += (i + 1) + ". " + this.items.get(i);
            if (i != this.items.size() - 1) {
                output += "\n";
            }
        }
        Ui.respond(output);
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
            Ui.respond("No item matched your specified item name.");
        } else {
            Ui.respond("Here are the items that matched your item name:\n" + output);
        }
    }

    /**
     * Runs a command based on the user input. Supported commands include todo,
     * deadline, event, list, do, delete, find and bye.
     * 
     * @param writer The writer object used to write data to file.
     * @param input  The command input from the user.
     * @return True if the command is the bye command, otherwise false.
     * @throws MissingArgumentException If an argument is missing based on the
     *                                  command.
     * @throws InvalidArgumentException If an argument is invalid based on the
     *                                  command.
     */
    public boolean runCommand(Writer writer, String input) throws MissingArgumentException, InvalidArgumentException {
        String[] inputArray = input.split(" ", 2);
        String command = inputArray[0].toLowerCase();

        // Expected format for writing to file: command,,id,,name,,done,,optional
        switch (command) {
        case Todo.COMMAND:
            Todo todoItem = Parser.parseToTodo(inputArray);
            if (writer.writeToFileNewLine(
                    Arrays.asList("todo", todoItem.getId(), todoItem.getName(), String.valueOf(todoItem.isDone())))) {
                this.addItem(todoItem);
            }
            break;

        case Deadline.COMMAND:
            Deadline deadlineItem = Parser.parseToDeadline(inputArray);
            if (writer.writeToFileNewLine(Arrays.asList("deadline", deadlineItem.getId(), deadlineItem.getName(),
                    String.valueOf(deadlineItem.isDone()), deadlineItem.getDate().toString()))) {
                this.addItem(deadlineItem);
            }
            break;

        case Event.COMMAND:
            Event eventItem = Parser.parseToEvent(inputArray);
            if (writer.writeToFileNewLine(
                    Arrays.asList("event", eventItem.getId(), eventItem.getName(), String.valueOf(eventItem.isDone()),
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
            Ui.respond("Bye. Hope to see you again soon!");
            return true;

        default:
            Ui.respond("No such command!");
        }
        return false;
    }

    public void findAndDoItem(Writer writer, String itemName) {
        for (Item item : this.items) {
            if (item.getName().equals(itemName) && !item.isDone()) {
                if (writer.writeToFileDoItem(item.getId())) {
                    item.doItem();
                    Ui.respond("You have completed the following item:\n" + item);
                    return;
                }
            }
        }
        Ui.respond("The specified item was not found or was already completed.");
    }

    public void deleteItem(Writer writer, int itemIndex) {
        if (this.items.isEmpty()) {
            Ui.respond("There is nothing to delete!");
            return;
        }

        itemIndex = itemIndex - 1;

        if (itemIndex < 0 || itemIndex > this.items.size() - 1) {
            Ui.respond("Invalid item index!");
            return;
        }

        Item item = this.items.get(itemIndex);
        if (writer.writeToFileDeleteItem(item.getId())) {
            this.items.remove(itemIndex);
        }

        Ui.respond("The following item was deleted:\n" + item);
    }
}
