public class Item {
    private String name;
    private boolean done;

    public Item(String name, boolean done) {
        this.name = name;
        this.done = done;
    }

    public boolean isDone() {
        return this.done;
    }

    public void doItem() {
        this.done = true;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        String doneStatus = this.done ? "X" : " ";
        return "[" + doneStatus + "] " + this.name;
    }
}
