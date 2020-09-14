/**
 * Deadline task which inherits from Task.
 * Adds the ability to have a deadline (by).
 */
public class Deadline extends Task {
    private String deadline;

    public Deadline(String job, String deadline) {
        super(job);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + String.format("(by: %s)", deadline);
    }


}
