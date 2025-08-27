package shibe;

public class Todo extends Item {
    public static final String COMMAND = "todo";

    public Todo(String name, boolean isDone) {
        super(name, isDone);
    }

    public Todo(String id, String name, boolean isDone) {
        super(id, name, isDone);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
