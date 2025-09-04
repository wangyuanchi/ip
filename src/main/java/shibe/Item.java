package shibe;

import java.util.UUID;

public abstract class Item {
    private String id;
    private String name;
    private boolean isDone;

    public Item(String name, boolean isDone) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.name = name;
        this.isDone = isDone;
    }

    public Item(String id, String name, boolean isDone) {
        this.id = id;
        this.name = name;
        this.isDone = isDone;
    }

    public void doItem() {
        this.isDone = true;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        String doneStatus = this.isDone ? "X" : " ";
        return "[" + doneStatus + "] " + this.name;
    }
}
