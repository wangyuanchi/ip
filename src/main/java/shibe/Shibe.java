package shibe;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class Shibe {
    private String path;
    private String delimiter;
    private Writer writer;
    private ItemList itemList;

    public Shibe(String path, String delimiter) {
        this.path = path;
        this.delimiter = delimiter;
        this.writer = new Writer(path, delimiter);
    }

    public void run() throws IOException {
        itemList = this.loadItemList();
    }

    public String getResponse(String command) {
        try {
            return this.itemList.runCommand(this.writer, command);
        } catch (ShibeException e) {
            return Ui.formatResponse(e.getMessage());
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
                break;
            default:

            }
        }

        s.close();
        return itemList;
    }
}
