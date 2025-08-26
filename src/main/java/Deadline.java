public class Deadline extends Item {
    private String time;

    public Deadline(String name, boolean done, String time) {
        super(name, done);
        this.time = time;
    }

    public Deadline(String id, String name, boolean done, String time) {
        super(id, name, done);
        this.time = time;
    }

    public String getTime() {
        return this.time;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.time + ")";
    }
}
