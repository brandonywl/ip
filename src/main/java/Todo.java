public class Todo extends Task {
    public Todo(String job) {
        super(job);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
