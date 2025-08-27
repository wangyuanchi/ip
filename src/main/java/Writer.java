import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Writer {
    private String path;
    private String delimiter;

    public Writer(String path, String delimiter) {
        this.path = path;
        this.delimiter = delimiter;
    }

    public boolean writeToFileNewLine(List<String> lineArray) {
        try {
            FileWriter fw = new FileWriter(this.path, true);
            fw.write(String.join(this.delimiter, lineArray) + "\n");
            fw.close();
            return true;
        } catch (IOException e) {
            UI.respond("Could not write to file!");
        }
        return false;
    }

    public boolean writeToFileDoItem(String id) {
        try {
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
            return true;
        } catch (IOException e) {
            UI.respond("Could not write to file!");
        }
        return false;
    }

    public boolean writeToFileDeleteItem(String id) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(this.path));
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                if (!line.contains(id)) {
                    updatedLines.add(line);
                }
            }

            Files.write(Paths.get(this.path), updatedLines);
            return true;
        } catch (IOException e) {
            UI.respond("Could not write to file!");
        }
        return false;
    }
}
