import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {
    public static Todo parseToTodo(String[] inputArray) throws MissingArgumentException {
        if (inputArray.length == 1) {
            throw new MissingArgumentException("todo <item_name>");
        }
        return new Todo(inputArray[1], false);
    }

    public static Deadline parseToDeadline(String[] inputArray)
            throws MissingArgumentException, InvalidArgumentException {
        if (inputArray.length == 1) {
            throw new MissingArgumentException("deadline <item_name> /by <date>");
        }

        inputArray = inputArray[1].split(" /by ", 2);
        if (inputArray.length == 1) {
            throw new MissingArgumentException("deadline <item_name> /by <date>");
        }

        LocalDate date;

        try {
            date = LocalDate.parse(inputArray[1]);
        } catch (DateTimeParseException e) {
            throw new InvalidArgumentException("<date> must be in the following format: yyyy-mm-dd");
        }

        return new Deadline(inputArray[0], false, date);
    }

    public static Event parseToEvent(String[] inputArray) throws MissingArgumentException, InvalidArgumentException {
        if (inputArray.length == 1) {
            throw new MissingArgumentException(
                    "event <item_name> /from <from_date> /to <to_date>");
        }

        inputArray = inputArray[1].split(" /from ", 2);
        if (inputArray.length == 1) {
            throw new MissingArgumentException(
                    "event <item_name> /from <from_date> /to <to_date>");
        }

        String itemName = inputArray[0];

        inputArray = inputArray[1].split(" /to ", 2);
        if (inputArray.length == 1) {
            throw new MissingArgumentException(
                    "event <item_name> /from <from_date> /to <to_date>");
        }

        LocalDate fromDate, toDate;

        try {
            fromDate = LocalDate.parse(inputArray[0]);
            toDate = LocalDate.parse(inputArray[1]);
        } catch (DateTimeParseException e) {
            throw new InvalidArgumentException(
                    "<from_date> and <to_date> must be in the following format: yyyy-mm-dd");
        }

        return new Event(itemName, false, fromDate, toDate);
    }

    public static String parseToItemName(String[] inputArray) throws MissingArgumentException {
        if (inputArray.length == 1) {
            throw new MissingArgumentException("do <item_name>");
        }
        return inputArray[1];
    }

    public static int parseToValidIndex(String[] inputArray) throws MissingArgumentException, InvalidArgumentException {
        if (inputArray.length == 1) {
            throw new MissingArgumentException("delete <item_index>");
        }

        try {
            return Integer.parseInt(inputArray[1]);
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException("<item_index> must be a valid integer");
        }
    }
}
