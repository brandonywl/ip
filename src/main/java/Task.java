/**
 * Task Class used to hold the job description as well as the status of the job.
 */
public class Task {
    private String job;
    private boolean status;
    private String statusString;

    public Task(String job) {
        this.job = job;
        incomplete();
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

    public boolean getStatus() {
        return status;
    }

    public String toPlainText() {
        return String.format("|%b|%s|", this.status, this.job);
    }

    @Override
    public String toString() {
<<<<<<< HEAD
        return String.format("[%s] %s", status, job);
=======
        return String.format("[%s] %s", statusString, job);
>>>>>>> branch-Level-7
    }
}
