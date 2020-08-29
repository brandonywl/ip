public class Task {
    String job;
    String status;

    public Task(String job) {
        this.job = job;
        incomplete();
    }

    public void complete() {
        this.status = "\u2713";
    }

    public void incomplete() {
        this.status = "\u2717";
    }

    @Override
    public String toString() {
        return String.format("[T][%s] %s", status, job);
    }
}
