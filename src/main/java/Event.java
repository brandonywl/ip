public class Event extends Task{
    String at;

    public Event(String job, String at) {
        super(job);
        this.at = at;
    }

    @Override
    public String toString() {
        return String.format("[E][%s] %s (at: %s)", status, job, at);
    }
}
