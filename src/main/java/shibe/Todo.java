package shibe;

public class Todo extends Item {
    public static final String COMMAND = "todo";

    /**
     * Creates a new Todo object.
     *
     * @param name The name of the todo.
     * @param done The completion status of the todo.
     */
    public Todo(String name, boolean done) {
        super(name, done);
    }

    /**
     * Creates a new Todo object with a pre-defined id.
     *
     * @param id   The pre-defined id of the object.
     * @param name The name of the todo.
     * @param done The completion status of the todo.
     */
    public Todo(String id, String name, boolean done) {
        super(id, name, done);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
