/**
 * Event task which inherits from Task.
 * Adds the ability to have a timing (at).
 */
public class Event extends Task {
    String at;

    public Event(String job, String at) {
        super(job);
        this.at = at;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + String.format(" (at: %s)", at);
    }
}
