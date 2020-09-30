package Duke.TaskTypes;

/**
 * Base class of Tasks
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

    /**
     * Gets the job from the task.
     * @return Job description of the task.
     */
    public String getJob() {
        return job;
    }

    /**
     * Set status of the task to complete
     * Set string representation to a tick (UTF-8)
     */
    public void complete() {
        this.status = true;
        this.statusString = "\u2713";
    }

    /**
     * Set status of the task to incomplete
     * Set string representation to a cross (UTF-8)
     */
    public void incomplete() {
        this.status = false;
        this.statusString = "\u2717";
    }

    /**
     * Convert the string to plain-text format for storing in a txt file.
     * @return Task in String format.
     */
    public String toPlainText() {
        return String.format("|%b|%s|", this.status, this.job);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", statusString, job);
    }
}
