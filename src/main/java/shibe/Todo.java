package shibe;

/**
 * Represents a todo item. A todo has a name and a completion status, and it
 * uses the command keyword {@link #COMMAND} in user input.
 */
public class Todo extends Item {
    public static final String COMMAND = "todo";

    /**
     * Creates a new Todo object.
     *
     * @param name   The name of the todo.
     * @param isDone The completion status of the todo.
     */
    public Todo(String name, boolean isDone) {
        super(name, isDone);
    }

    /**
     * Creates a new Todo object with a pre-defined id.
     *
     * @param id     The pre-defined id of the object.
     * @param name   The name of the todo.
     * @param isDone The completion status of the todo.
     */
    public Todo(String id, String name, boolean isDone) {
        super(id, name, isDone);
    }

    /**
     * Returns the string representation of the todo, prefixed with [T] to indicate
     * its type.
     *
     * @return The formatted string for display.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
