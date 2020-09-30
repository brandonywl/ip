package Duke.TaskTypes;

/**
 * Extends Tasks to have an Event type.
 * Adds ability to hold a timing
 */
public class Event extends Task {
    private final String at;

    public Event(String job, String at) {
        super(job);
        this.at = at;
    }

    public Event(String job, boolean status, String at) {
        super(job, status);
        this.at = at;
    }

    /**
     * Converts the task to a plain-text format to be stored in a txt file.
     * @return Plain-text format Task.
     */
    @Override
    public String toPlainText() {
        return "|E" + super.toPlainText() + String.format("%s|", at);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + String.format(" (at: %s)", at);
    }
}
