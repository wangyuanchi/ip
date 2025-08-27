package shibe;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Item {
    private LocalDate startDate;
    private LocalDate endDate;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    public static final String COMMAND = "event";

    /**
     * Creates a new Event object.
     *
     * @param name      The name of the event.
     * @param done      The completion status of the event.
     * @param startDate The start date of the event.
     * @param endDate   The end date of the event.
     */
    public Event(String name, boolean done, LocalDate startDate, LocalDate endDate) {
        super(name, done);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Creates a new Event object with a pre-defined id.
     *
     * @param id        The pre-defined id of the object.
     * @param name      The name of the event.
     * @param done      The completion status of the event.
     * @param startDate The start date of the event.
     * @param endDate   The end date of the event.
     */
    public Event(String id, String name, boolean done, LocalDate startDate, LocalDate endDate) {
        super(id, name, done);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.startDate.format(this.formatter) + " to "
                + this.endDate.format(this.formatter) + ")";
    }
}
