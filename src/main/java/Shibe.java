import java.util.Scanner;

public class Shibe {
    public static void main(String[] args) {
        TodoList todoList = new TodoList();

        System.out.println("Hello! I'm Shibe.\n" +
                "What can I do for you?");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            String[] inputArray = input.split(" ", 2);
            String command = inputArray[0].toLowerCase();

            switch (command) {
                case "add":
                    if (inputArray.length == 1) {
                        System.out.println("Missing argument! Usage: add <item_name>");
                    } else {
                        todoList.addItem(inputArray[1]);
                    }
                    break;

                case "list":
                    todoList.listItems();
                    break;

                case "do":
                    if (inputArray.length == 1) {
                        System.out.println("Missing argument! Usage: do <item_name>");
                    } else {
                        String response = todoList.findAndDoItem(inputArray[1]);
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
