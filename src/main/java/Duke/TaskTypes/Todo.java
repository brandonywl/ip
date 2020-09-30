package Duke.TaskTypes;

/**
 * Extends Tasks to have a To-do type
 */
public class Todo extends Task {
    public Todo (String task) {
        super(task);
    }

    public Todo (String task, boolean status) {
        super(task, status);
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
