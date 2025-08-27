package shibe;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {
    /**
     * Creates a todo object based on the given input.
     * The expected format is ["todo", "<item_name>"].
     *
     * @param inputArray The input from the user.
     * @return Returns the created todo object.
     * @throws MissingArgumentException If <item_name> is missing.
     */
    public static Todo parseToTodo(String[] inputArray) throws MissingArgumentException {
        if (inputArray.length == 1) {
            throw new MissingArgumentException("todo <item_name>");
        }
        return new Todo(inputArray[1], false);
    }

    /**
     * Creates a deadline object based on the given input.
     * The expected format is ["deadline", "<item_name> /by <date>"].
     *
     * @param inputArray The input from the user.
     * @return Returns the created deadline object.
     * @throws MissingArgumentException If <item_name> is missing.
     * @throws InvalidArgumentException If <date> is not of the format yyyy-mm-dd.
     */
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

    /**
     * Creates an event object based on the given input.
     * The expected format is ["event", "<item_name> /from <from_date> /to
     * <to_date>"].
     *
     * @param inputArray The input from the user.
     * @return Returns the created event object.
     * @throws MissingArgumentException If <item_name> is missing.
     * @throws InvalidArgumentException If <from_date> or <to_date> is not of the
     *                                  format yyyy-mm-dd.
     */
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

    /**
     * Gets the item name from the given input.
     * The expected format is ["do", "<item_name>"].
     *
     * @param inputArray The input from the user.
     * @return Returns the name of the item.
     * @throws MissingArgumentException If <item_name> is missing.
     */
    public static String parseToItemName(String[] inputArray) throws MissingArgumentException {
        if (inputArray.length == 1) {
            throw new MissingArgumentException("do <item_name>");
        }
        return inputArray[1];
    }

    /**
     * Gets the index from the given input.
     * The expected format is ["delete", "<item_index>"].
     *
     * @param inputArray The input from the user.
     * @return Returns the index specified.
     * @throws MissingArgumentException If <item_index> is missing.
     * @throws InvalidArgumentException If <item_index> cannot be parsed into a
     *                                  valid integer.
     */
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
