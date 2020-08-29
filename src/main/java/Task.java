/**
 * Task Class used to hold the job description as well as the status of the job.
 */
public class Task {
    String job;
    String status;

    public Task(String job) {
        this.job = job;
        incomplete();
    }

    // Unicode representation of a tick
    public void complete() {
        this.status = "\u2713";
    }
    // Unicode representation of a cross
    public void incomplete() {
        this.status = "\u2717";
    }

    @Override
    public String toString() {
        return String.format("[T][%s] %s", status, job);
    }
}
