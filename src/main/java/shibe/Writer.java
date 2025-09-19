package shibe;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper for appending and updating persisted item records in a plain text
 * file. Uses a configurable path and delimiter to read, write, and modify
 * lines.
 */
public class Writer {
    private String path;
    private String delimiter;

    /**
     * Creates a Writer configured with the storage path and field delimiter.
     *
     * @param path      The path to the storage file.
     * @param delimiter The delimiter used to join and split fields.
     */
    public Writer(String path, String delimiter) {
        this.path = path;
        this.delimiter = delimiter;
    }

    /**
     * Appends a new line to the text file. The new line is formed from the
     * lineArray by joining the strings with a delimiter.
     *
     * @param lineArray The array of strings which will be processed.
     */
    public void writeToFileNewLine(List<String> lineArray) throws IOException {
        assert this.path != null && !this.path.isEmpty() : "File path must not be null or empty";
        assert this.delimiter != null && !this.delimiter.isEmpty() : "Delimiter must not be null or empty";

        FileWriter fw = new FileWriter(this.path, true);
        fw.write(String.join(this.delimiter, lineArray) + "\n");
        fw.close();
    }

    /**
     * Changes the done status of an item to true if the item id exists.
     *
     * @param id The id of the item.
     */
    public void writeToFileDoItem(String id) throws IOException {
        assert this.path != null && !this.path.isEmpty() : "File path must not be null or empty";
        assert this.delimiter != null && !this.delimiter.isEmpty() : "Delimiter must not be null or empty";

        List<String> lines = Files.readAllLines(Paths.get(this.path));
        List<String> updatedLines = new ArrayList<>();

        for (String line : lines) {
            if (line.contains(id)) {
                String[] lineArray = line.split(this.delimiter);
                lineArray[3] = "true";
                updatedLines.add(String.join(this.delimiter, lineArray));
            } else {
                updatedLines.add(line);
            }
        }

        Files.write(Paths.get(this.path), updatedLines);
    }

    /**
     * Removes all lines where the id of the item matches the id provided.
     *
     * @param id The id of the item to remove.
     */
    public void writeToFileDeleteItem(String id) throws IOException {
        assert this.path != null && !this.path.isEmpty() : "File path must not be null or empty";

        List<String> lines = Files.readAllLines(Paths.get(this.path));
        List<String> updatedLines = new ArrayList<>();

        for (String line : lines) {
            if (!line.contains(id)) {
                updatedLines.add(line);
            }
        }

        Files.write(Paths.get(this.path), updatedLines);
    }
}
