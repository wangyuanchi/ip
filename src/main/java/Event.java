public class Event extends Item {
    private String startTime;
    private String endTime;

    public Event(String name, boolean done, String startTime, String endTime) {
        super(name, done);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Event(String id, String name, boolean done, String startTime, String endTime) {
        super(id, name, done);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.startTime + " to " + this.endTime + ")";
    }
}
