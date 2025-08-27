package shibe;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Item {
    private LocalDate startDate;
    private LocalDate endDate;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    public static final String COMMAND = "event";

    public Event(String name, boolean isDone, LocalDate startDate, LocalDate endDate) {
        super(name, isDone);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Event(String id, String name, boolean isDone, LocalDate startDate, LocalDate endDate) {
        super(id, name, isDone);
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
