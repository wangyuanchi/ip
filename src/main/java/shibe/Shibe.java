package shibe;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class Shibe {
    private Scanner scanner;
    private String path;
    private String delimiter;
    private Writer writer;
    private ItemList itemList;

    public Shibe(String path, String delimiter) {
        this.scanner = new Scanner(System.in);
        this.path = path;
        this.delimiter = delimiter;
        this.writer = new Writer(path, delimiter);
    }

    private void run() {
        try {
            itemList = this.loadItemList();
        } catch (IOException e) {
            UI.respond("Ensure the app has permissions for reading and writing to files.");
            return;
        }

        UI.respond("Hello! I'm Shibe.\nWhat can I do for you?");

        while (true) {
            try {
                boolean isByeCommand = this.itemList.runCommand(this.writer, this.scanner.nextLine());
                if (isByeCommand) {
                    this.scanner.close();
                    return;
                }
            } catch (ShibeException e) {
                UI.respond(e.getMessage());
            }
        }
    }

    private ItemList loadItemList() throws IOException {
        File f = new File(this.path);

        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }

        if (!f.exists()) {
            f.createNewFile();
            return new ItemList();
        }

        ItemList itemList = new ItemList();
        Scanner s = new Scanner(f);
        while (s.hasNext()) {
            // Expected format: command,,id,,name,,done,,optional
            String[] inputArray = s.nextLine().split(this.delimiter);

            switch (inputArray[0]) {
            case Todo.COMMAND:
                itemList.addItemSilent(new Todo(inputArray[1], inputArray[2], Boolean.parseBoolean(inputArray[3])));
                break;
            case Deadline.COMMAND:
                itemList.addItemSilent(new Deadline(inputArray[1], inputArray[2], Boolean.parseBoolean(inputArray[3]),
                        LocalDate.parse(inputArray[4])));
                break;
            case Event.COMMAND:
                itemList.addItemSilent(new Event(inputArray[1], inputArray[2], Boolean.parseBoolean(inputArray[3]),
                        LocalDate.parse(inputArray[4]), LocalDate.parse(inputArray[5])));
            }
        }

        s.close();
        return itemList;
    }

    public static void main(String[] args) {
        new Shibe("./data/itemList.txt", ",,").run();
    }
}
