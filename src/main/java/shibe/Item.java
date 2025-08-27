package shibe;

import java.util.UUID;

public abstract class Item {
    private String id;
    private String name;
    private boolean done;

    public Item(String name, boolean done) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.name = name;
        this.done = done;
    }

    public Item(String id, String name, boolean done) {
        this.id = id;
        this.name = name;
        this.done = done;
    }

    public void doItem() {
        this.done = true;
    }

    public boolean isDone() {
        return this.done;
    }

    public String getID() {
        return this.id;
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
