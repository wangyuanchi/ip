package shibe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Manages a list of items and provides command handling utilities such as add,
 * list, find, do, and delete.
 */
public class ItemList {
    /**
     * The backing store for all items in this list.
     */
    private ArrayList<Item> items;

    /**
     * A mapping from short commands to their full command representations.
     */
    private HashMap<String, String> shortCommandMap = new HashMap<>();

    /**
     * Creates a new item list and initializes the short command mappings.
     */
    public ItemList() {
        this.items = new ArrayList<Item>();
        this.shortCommandMap.put("t", Todo.COMMAND);
        this.shortCommandMap.put("dl", Deadline.COMMAND);
        this.shortCommandMap.put("e", Event.COMMAND);
        this.shortCommandMap.put("l", "list");
        this.shortCommandMap.put("f", "find");
        this.shortCommandMap.put("d", "delete");
    }

    /**
     * Adds the given item to the item list and responds with a success message.
     *
     * @param item The item to add.
     * @return The information of the added item.
     */
    public String addItem(Item item) {
        assert item != null : "Item to add should not be null";

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
        assert item != null : "Item to add should not be null";

        this.items.add(item);
    }

    /**
     * Lists all items in the item list with 1-based indexing.
     *
     * @return The string representation of the item list.
     */
    public String listItems() {
        assert this.items != null : "Items list should not be null";

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
        assert this.items != null : "Items list should not be null";

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
        assert writer != null : "Writer must not be null";

        String[] inputArray = input.split(" ", 2);
        String command = inputArray[0].toLowerCase();

        if (this.shortCommandMap.containsKey(command)) {
            command = shortCommandMap.get(command);
        }

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

    /**
     * Parses a todo command from the input and adds the created item to the list,
     * also writes the item to file.
     *
     * @param writer     The writer used for file operations.
     * @param inputArray The split user input where index 0 is the command and index
     *                   1 is the arguments.
     * @return The response after adding the item.
     * @throws MissingArgumentException If required arguments are missing.
     */
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

    /**
     * Parses a deadline command from the input and adds the created item to the
     * list, also writes the item to file.
     *
     * @param writer     The writer used for file operations.
     * @param inputArray The split user input where index 0 is the command and index
     *                   1 is the arguments.
     * @return The response after adding the item.
     * @throws MissingArgumentException If required arguments are missing.
     * @throws InvalidArgumentException If arguments are present but invalid.
     */
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

    /**
     * Parses an event command from the input and adds the created item to the list,
     * also writes the item to file.
     *
     * @param writer     The writer used for file operations.
     * @param inputArray The split user input where index 0 is the command and index
     *                   1 is the arguments.
     * @return The response after adding the item.
     * @throws MissingArgumentException If required arguments are missing.
     * @throws InvalidArgumentException If arguments are present but invalid.
     */
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

    /**
     * Finds an item by exact name and marks it as done if it is not already done,
     * also writes the update to file.
     *
     * @param writer   The writer used for file operations.
     * @param itemName The exact name of the item to complete.
     * @return The response indicating success or failure.
     */
    public String findAndDoItem(Writer writer, String itemName) {
        assert writer != null : "Writer must not be null";

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

    /**
     * Deletes an item from the item list if it exists based on the item index.
     *
     * @param writer    The writer object used to write data to file.
     * @param itemIndex The index of the item to delete.
     * @return The response to the command.
     */
    public String deleteItem(Writer writer, int itemIndex) {
        assert writer != null : "Writer must not be null";

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
