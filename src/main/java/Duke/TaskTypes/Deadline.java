package Duke.TaskTypes;

/**
 * Extends Tasks to have a Deadline type
 * Adds ability to have a deadline (by) timing.
 */
public class Deadline extends Task {
    private final String deadline;

    public Deadline(String job, String deadline) {
        super(job);
        this.deadline = deadline;
    }

    public Deadline(String job, boolean status, String deadline) {
        super(job, status);
        this.deadline = deadline;
    }

    /**
     * Converts the task to a plain-text format to be stored in a txt file.
     * @return Plain-text format Task.
     */
    @Override
    public String toPlainText() {
        return "|D" + super.toPlainText() + String.format("%s|", deadline);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + String.format(" (by: %s)", deadline);
    }


}
