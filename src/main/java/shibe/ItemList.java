package shibe;

import java.io.IOException;
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
     * @return The information of the added item.
     */
    public String addItem(Item item) {
        this.items.add(item);
        return Ui.formatResponse("I have added the following item:\n" + item);
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
     * 
     * @return The string representation of the item list.
     */
    public String listItems() {
        if (this.items.isEmpty()) {
            return Ui.formatResponse("No items in this list!");
        }

        String output = "";
        for (int i = 0; i < this.items.size(); i++) {
            output += (i + 1) + ". " + this.items.get(i);
            if (i != this.items.size() - 1) {
                output += "\n";
            }
        }
        return Ui.formatResponse(output);
    }

    /**
     * Finds all items in the item list that contains the itemName with 1-based
     * indexing.
     *
     * @param itemName The name of the item to match against.
     * @return The string representation of the items found.
     */
    public String findItems(String itemName) {
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
            return Ui.formatResponse("No item matched your specified item name.");
        } else {
            return Ui.formatResponse("Here are the items that matched your item name:\n" + output);
        }
    }

    /**
     * Runs a command based on the user input. Supported commands include todo,
     * deadline, event, list, do, delete, find and bye.
     *
     * @param writer The writer object used to write data to file.
     * @param input  The command input from the user.
     * @return The response to the command.
     * @throws MissingArgumentException If an argument is missing based on the
     *                                  command.
     * @throws InvalidArgumentException If an argument is invalid based on the
     *                                  command.
     */
    public String runCommand(Writer writer, String input) throws MissingArgumentException, InvalidArgumentException {
        String[] inputArray = input.split(" ", 2);
        String command = inputArray[0].toLowerCase();

        // Expected format for writing to file: command,,id,,name,,done,,optional
        switch (command) {
        case Todo.COMMAND:
            return processTodoFromCommand(writer, inputArray);

        case Deadline.COMMAND:
            return processDeadlineFromCommand(writer, inputArray);

        case Event.COMMAND:
            return processEventFromCommand(writer, inputArray);

        case "list":
            return this.listItems();

        case "find":
            return this.findItems(Parser.parseToItemName(inputArray));

        case "do":
            return this.findAndDoItem(writer, Parser.parseToItemName(inputArray));

        case "delete":
            return this.deleteItem(writer, Parser.parseToValidIndex(inputArray));

        case "bye":
            return Ui.formatResponse("Bye. Hope to see you again soon!");

        default:
            return Ui.formatResponse("No such command!");
        }
    }

    public String processTodoFromCommand(Writer writer, String[] inputArray) throws MissingArgumentException {
        Todo todoItem = Parser.parseToTodo(inputArray);
        try {
            writer.writeToFileNewLine(
                    Arrays.asList("todo", todoItem.getId(), todoItem.getName(), String.valueOf(todoItem.isDone())));
            return this.addItem(todoItem);
        } catch (IOException e) {
            return Ui.formatResponse("Could not write to file!");
        }
    }

    public String processDeadlineFromCommand(Writer writer, String[] inputArray)
            throws MissingArgumentException, InvalidArgumentException {
        Deadline deadlineItem = Parser.parseToDeadline(inputArray);
        try {
            writer.writeToFileNewLine(Arrays.asList("deadline", deadlineItem.getId(), deadlineItem.getName(),
                    String.valueOf(deadlineItem.isDone()), deadlineItem.getDate().toString()));
            return this.addItem(deadlineItem);
        } catch (IOException e) {
            return Ui.formatResponse("Could not write to file!");
        }
    }

    public String processEventFromCommand(Writer writer, String[] inputArray)
            throws MissingArgumentException, InvalidArgumentException {
        Event eventItem = Parser.parseToEvent(inputArray);
        try {
            writer.writeToFileNewLine(
                    Arrays.asList("event", eventItem.getId(), eventItem.getName(), String.valueOf(eventItem.isDone()),
                            eventItem.getStartDate().toString(), eventItem.getEndDate().toString()));
            return this.addItem(eventItem);
        } catch (IOException e) {
            return Ui.formatResponse("Could not write to file!");
        }
    }

    public String findAndDoItem(Writer writer, String itemName) {
        for (Item item : this.items) {
            if (item.getName().equals(itemName) && !item.isDone()) {
                try {
                    writer.writeToFileDoItem(item.getId());
                } catch (IOException e) {
                    return Ui.formatResponse("Could not write to file!");
                }

                item.doItem();
                return Ui.formatResponse("You have completed the following item:\n" + item);

            }
        }
        return Ui.formatResponse("The specified item was not found or was already completed.");
    }

    public String deleteItem(Writer writer, int itemIndex) {
        if (this.items.isEmpty()) {
            return Ui.formatResponse("There is nothing to delete!");
        }

        itemIndex = itemIndex - 1;

        if (itemIndex < 0 || itemIndex > this.items.size() - 1) {
            return Ui.formatResponse("Invalid item index!");
        }

        Item item = this.items.get(itemIndex);

        try {
            writer.writeToFileDeleteItem(item.getId());
        } catch (IOException e) {
            return Ui.formatResponse("Could not write to file!");
        }

        this.items.remove(itemIndex);
        return Ui.formatResponse("The following item was deleted:\n" + item);
    }
}
