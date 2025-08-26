public class Todo extends Item {
    public Todo(String name, boolean done) {
        super(name, done);
    }

    public Todo(String id, String name, boolean done) {
        super(id, name, done);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
