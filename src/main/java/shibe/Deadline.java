package shibe;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Item {
    private LocalDate date;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    public static final String COMMAND = "deadline";

    /**
     * Creates a new Deadline object.
     *
     * @param name   The name of the deadline.
     * @param isDone The completion status of the deadline.
     * @param date   The date of the deadline.
     */
    public Deadline(String name, boolean isDone, LocalDate date) {
        super(name, isDone);
        this.date = date;
    }

    /**
     * Creates a new Deadline object with a pre-defined id.
     *
     * @param id     The pre-defined id of the object.
     * @param name   The name of the deadline.
     * @param isDone The completion status of the deadline.
     * @param date   The date of the deadline.
     */
    public Deadline(String id, String name, boolean isDone, LocalDate date) {
        super(id, name, isDone);
        this.date = date;
    }

    public LocalDate getDate() {
        return this.date;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.date.format(this.formatter) + ")";
    }
}
