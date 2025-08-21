import java.util.Scanner;

public class Shibe {
    public static void main(String[] args) {
        ItemList itemList = new ItemList();

        System.out.println("Hello! I'm Shibe.\n" +
                "What can I do for you?");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            try {
                boolean bye = itemList.runCommand(input);
                if (bye) {
                    scanner.close();
                    return;
                }
            } catch (MissingArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
