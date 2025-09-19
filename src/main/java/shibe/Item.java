package shibe;

import java.util.UUID;

/**
 * Represents a generic item with an ID, name, and completion status. This class
 * is abstract and should be extended by more specific item types.
 */
public abstract class Item {
    private String id;
    private String name;
    private boolean isDone;

    /**
     * Creates a new item with a generated unique ID.
     *
     * @param name   the name of the item
     * @param isDone the completion status of the item
     */
    public Item(String name, boolean isDone) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.name = name;
        this.isDone = isDone;
    }

    /**
     * Creates a new item with a given ID.
     *
     * @param id     the unique ID of the item
     * @param name   the name of the item
     * @param isDone the completion status of the item
     */
    public Item(String id, String name, boolean isDone) {
        this.id = id;
        this.name = name;
        this.isDone = isDone;
    }

    /**
     * Marks this item as done.
     */
    public void doItem() {
        this.isDone = true;
    }

    /**
     * Checks if this item is done.
     *
     * @return true if the item is done, false otherwise
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Returns the ID of this item.
     *
     * @return the item ID
     */
    public String getId() {
        return this.id;
    }

    /**
     * Returns the name of this item.
     *
     * @return the item name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns a string representation of this item. Shows [X] if the item is done,
     * [ ] otherwise.
     *
     * @return a formatted string of the item
     */
    @Override
    public String toString() {
        String doneStatus = this.isDone ? "X" : " ";
        return "[" + doneStatus + "] " + this.name;
    }
}
