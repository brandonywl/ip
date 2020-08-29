public class Deadline extends Task {
    protected String deadline;

    public Deadline(String job, String deadline) {
        super(job);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return String.format("[D][%s] %s (by: %s)", status, job, deadline);
    }


}
