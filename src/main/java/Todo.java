public class Todo extends Task {
    public Todo (String task) {
        super(task);
    }

    @Override
    public String toPlainText() {
        return "|T" + super.toPlainText();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
