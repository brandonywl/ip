/**
 * Event task which inherits from Task.
 * Adds the ability to have a timing (at).
 */
public class Event extends Task {
    private String at;

    public Event(String job, String at) {
        super(job);
        this.at = at;
    }

    public Event(String job, boolean status, String at) {
        super(job, status);
        this.at = at;
    }

    public String getTiming() {
        return at;
    }

    @Override
    public String toPlainText() {
        return "|E" + super.toPlainText() + String.format("%s|", at);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + String.format(" (at: %s)", at);
    }
}
