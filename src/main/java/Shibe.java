import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Shibe {
    private static final String ITEM_LIST_FILE_PATH = "./data/itemList.txt";
    private static final String DELIMITER = ",,";

    public static void main(String[] args) {
        ItemList itemList;

        try {
            itemList = loadItemList();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ensure the app has permissions for reading and writing to files.");
            return;
        }

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
            } catch (ShibeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static ItemList loadItemList() throws IOException {
        File f = new File(Shibe.ITEM_LIST_FILE_PATH);

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
            String line = s.nextLine();

            // Expected format: command,,id,,name,,done,,optional
            // Assumes the file is not edited manually
            String[] inputArray = line.split(Shibe.DELIMITER);

            switch (inputArray[0]) {
                case "todo":
                    itemList.addItemSilent(new Todo(inputArray[1], inputArray[2], Boolean.parseBoolean(inputArray[3])));
                    break;
                case "deadline":
                    itemList.addItemSilent(
                            new Deadline(inputArray[1], inputArray[2], Boolean.parseBoolean(inputArray[3]),
                                    LocalDate.parse(inputArray[4])));
                    break;
                case "event":
                    itemList.addItemSilent(
                            new Event(inputArray[1], inputArray[2], Boolean.parseBoolean(inputArray[3]),
                                    LocalDate.parse(inputArray[4]), LocalDate.parse(inputArray[5])));
            }
        }

        s.close();
        return itemList;
    }

    public static boolean writeToFileNewLine(List<String> lineArray) {
        try {
            FileWriter fw = new FileWriter(Shibe.ITEM_LIST_FILE_PATH, true);
            fw.write(String.join(Shibe.DELIMITER, lineArray) + System.lineSeparator());
            fw.close();
            return true;
        } catch (IOException e) {
            System.out.println("Could not write to file!");
        }
        return false;
    }

    public static boolean writeToFileDoItem(String id) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(Shibe.ITEM_LIST_FILE_PATH));
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                if (line.contains(id)) {
                    String[] lineArray = line.split(Shibe.DELIMITER);
                    lineArray[3] = "true";
                    updatedLines.add(String.join(Shibe.DELIMITER, lineArray));
                } else {
                    updatedLines.add(line);
                }
            }

            Files.write(Paths.get(Shibe.ITEM_LIST_FILE_PATH), updatedLines);
            return true;
        } catch (IOException e) {
            System.out.println("Could not write to file!");
        }
        return false;
    }

    public static boolean writeToFileDeleteItem(String id) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(Shibe.ITEM_LIST_FILE_PATH));
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                if (!line.contains(id)) {
                    updatedLines.add(line);
                }
            }

            Files.write(Paths.get(Shibe.ITEM_LIST_FILE_PATH), updatedLines);
            return true;
        } catch (IOException e) {
            System.out.println("Could not write to file!");
        }
        return false;
    }
}