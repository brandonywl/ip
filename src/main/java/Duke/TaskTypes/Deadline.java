package Duke.TaskTypes;

/**
 * Duke.TaskTypes.Deadline task which inherits from Duke.TaskTypes.Task.
 * Adds the ability to have a deadline (by).
 */
public class Deadline extends Task {
    private String deadline;

    public Deadline(String job, String deadline) {
        super(job);
        this.deadline = deadline;
    }

    public Deadline(String job, boolean status, String deadline) {
        super(job, status);
        this.deadline = deadline;
    }

    public String getTiming() {
        return deadline;
    }

    @Override
    public String toPlainText() {
        return "|D" + super.toPlainText() + String.format("%s|", deadline);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + String.format(" (by: %s)", deadline);
    }


}
