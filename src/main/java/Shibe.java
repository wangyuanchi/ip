import java.util.Scanner;

public class Shibe {
    public static void main(String[] args) {
        ItemList itemList = new ItemList();

        System.out.println("Hello! I'm Shibe.\n" +
                "What can I do for you?");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            String[] inputArray = input.split(" ", 2);
            String command = inputArray[0].toLowerCase();

            switch (command) {
                case "todo":
                    if (inputArray.length == 1) {
                        System.out.println("Missing argument! Usage: todo <item_name>");
                    } else {
                        String itemName = inputArray[1];
                        itemList.addItem(new Todo(itemName, false));
                    }
                    break;

                case "deadline":
                    if (inputArray.length == 1) {
                        System.out.println("Missing argument! Usage: deadline <item_name> /by <time>");
                        break;
                    }

                    inputArray = inputArray[1].split(" /by ", 2);
                    if (inputArray.length == 1) {
                        System.out.println("Missing argument! Usage: deadline <item_name> /by <time>");
                        break;
                    }

                    itemList.addItem(new Deadline(inputArray[0], false, inputArray[1]));
                    break;

                case "event":
                    if (inputArray.length == 1) {
                        System.out
                                .println("Missing argument! Usage: event <item_name> /from <from_time> /to <to_time>");
                        break;
                    }

                    inputArray = inputArray[1].split(" /from ", 2);
                    if (inputArray.length == 1) {
                        System.out
                                .println("Missing argument! Usage: event <item_name> /from <from_time> /to <to_time>");
                        break;
                    }

                    String itemName = inputArray[0];

                    inputArray = inputArray[1].split(" /to ", 2);
                    if (inputArray.length == 1) {
                        System.out
                                .println("Missing argument! Usage: event <item_name> /from <from_time> /to <to_time>");
                        break;
                    }

                    itemList.addItem(new Event(itemName, false, inputArray[0], inputArray[1]));
                    break;

                case "list":
                    itemList.listItems();
                    break;

                case "do":
                    if (inputArray.length == 1) {
                        System.out.println("Missing argument! Usage: do <item_name>");
                    } else {
                        String response = itemList.findAndDoItem(inputArray[1]);
                        System.out.println(response);
                    }
                    break;

                case "bye":
                    System.out.println("Bye. Hope to see you again soon!");
                    scanner.close();
                    return;

                default:
                    System.out.println("No such command!");
            }
        }
    }
}
