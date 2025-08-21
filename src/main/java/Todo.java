public class Todo extends Item {
    public Todo(String name, boolean done) {
        super(name, done);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
