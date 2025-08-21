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

    @Override
    public String toString() {
        return this.name;
    }
}
