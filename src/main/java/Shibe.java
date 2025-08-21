import java.util.Scanner;

public class Shibe {
    public static void main(String[] args) {
        System.out.println("Hello! I'm Shibe.\n" +
                "What can I do for you?");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                break;
            }

            System.out.println(input);
        }

        scanner.close();
    }
}
