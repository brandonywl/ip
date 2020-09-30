package Duke.TaskTypes;

/**
 * Duke.TaskTypes.Task Class used to hold the job description as well as the status of the job.
 */
public class Task {
    private final String job;
    private boolean status;
    // TODO: Set a string representation of Status that can be viewed on Unix.
    private String statusString;

    public Task(String job) {
        this.job = job;
        incomplete();
    }

    public Task(String job, boolean status) {
        this(job);
        if (status) {
            complete();
        }
    }

    // Unicode representation of a tick
    public void complete() {
        this.status = true;
        this.statusString = "\u2713";
    }
    // Unicode representation of a cross
    public void incomplete() {
        this.status = false;
        this.statusString = "\u2717";
    }

    public String getJob() {
        return job;
    }

    public String toPlainText() {
        return String.format("|%b|%s|", this.status, this.job);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", statusString, job);
    }
}
