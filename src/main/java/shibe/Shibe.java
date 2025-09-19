package shibe;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Core application class that loads existing items from storage and delegates
 * command handling to {@link ItemList}. It is responsible for file setup,
 * initialization, and returning formatted responses.
 */
public class Shibe {
    private String path;
    private String delimiter;
    private Writer writer;
    private ItemList itemList;

    /**
     * Constructs a Shibe instance configured with a storage path and delimiter.
     *
     * @param path      The file path used for persistent storage.
     * @param delimiter The delimiter used to parse and write file records.
     */
    public Shibe(String path, String delimiter) {
        this.path = path;
        this.delimiter = delimiter;
        this.writer = new Writer(path, delimiter);
    }

    /**
     * Initializes the application by loading the item list from persistent storage.
     *
     * @throws IOException If an error occurs while accessing the storage file.
     */
    public void run() throws IOException {
        itemList = this.loadItemList();
    }

    /**
     * Processes a user command and returns a formatted response.
     *
     * @param command The raw command string from the user.
     * @return A formatted response string for display.
     */
    public String getResponse(String command) {
        try {
            return this.itemList.runCommand(this.writer, command);
        } catch (ShibeException e) {
            return Ui.formatResponse(e.getMessage());
        }
    }

    /**
     * Loads items from the storage file into an {@link ItemList}. Creates the file
     * and parent directories if they do not exist.
     *
     * @return An initialized {@link ItemList} with items loaded from storage.
     * @throws IOException If an error occurs while reading or creating the file.
     */
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
