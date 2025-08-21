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

    public boolean runCommand(String input) throws ShibeException {
        String[] inputArray = input.split(" ", 2);
        String command = inputArray[0].toLowerCase();

        switch (command) {
            case "todo":
                if (inputArray.length == 1) {
                    throw new MissingArgumentException("todo <item_name>");
                }

                this.addItem(new Todo(inputArray[1], false));
                break;

            case "deadline":
                if (inputArray.length == 1) {
                    throw new MissingArgumentException("deadline <item_name> /by <time>");
                }

                inputArray = inputArray[1].split(" /by ", 2);
                if (inputArray.length == 1) {
                    throw new MissingArgumentException("deadline <item_name> /by <time>");
                }

                this.addItem(new Deadline(inputArray[0], false, inputArray[1]));
                break;

            case "event":
                if (inputArray.length == 1) {
                    throw new MissingArgumentException(
                            "event <item_name> /from <from_time> /to <to_time>");
                }

                inputArray = inputArray[1].split(" /from ", 2);
                if (inputArray.length == 1) {
                    throw new MissingArgumentException(
                            "event <item_name> /from <from_time> /to <to_time>");
                }

                String itemName = inputArray[0];

                inputArray = inputArray[1].split(" /to ", 2);
                if (inputArray.length == 1) {
                    throw new MissingArgumentException(
                            "event <item_name> /from <from_time> /to <to_time>");
                }

                this.addItem(new Event(itemName, false, inputArray[0], inputArray[1]));
                break;

            case "list":
                this.listItems();
                break;

            case "do":
                if (inputArray.length == 1) {
                    throw new MissingArgumentException("do <item_name>");
                } else {
                    String response = this.findAndDoItem(inputArray[1]);
                    System.out.println(response);
                }
                break;

            case "delete":
                if (inputArray.length == 1) {
                    throw new MissingArgumentException("delete <item_index>");
                } else {
                    try {
                        int itemIndex = Integer.parseInt(inputArray[1]);
                        String response = this.deleteItem(itemIndex);
                        System.out.println(response);
                    } catch (NumberFormatException e) {
                        throw new InvalidArgumentException("<item_index> must be a valid integer");
                    }
                }
                break;

            case "bye":
                System.out.println("Bye. Hope to see you again soon!");
                return true;

            default:
                System.out.println("No such command!");
        }
        return false;
    }

    public String findAndDoItem(String itemName) {
        for (Item item : this.items) {
            if (item.getName().equals(itemName) && !item.isDone()) {
                item.doItem();
                return "You have completed the following item:\n" + item;
            }
        }
        return "The specified item was not found or was already completed.";
    }

    public String deleteItem(int itemIndex) {
        if (this.items.isEmpty()) {
            return "There is nothing to delete!";
        }

        if (itemIndex < 0 || itemIndex > this.items.size() - 1) {
            return "Invalid item index!";
        }

        Item item = this.items.get(itemIndex);
        this.items.remove(itemIndex);

        return "The following item was deleted:\n" + item;
    }
}
