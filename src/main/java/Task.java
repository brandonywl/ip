public class Task {
    String job;
    String status;

    public Task(String job, boolean status) {
        this.job = job;
        if (status){
            complete();
        }
        else{
            incomplete();
        }
    }

    public Task(String job) {
        this.job = job;
        incomplete();
    }

    public void complete(){
        this.status = "\u2713";
    }

    public void incomplete(){
        this.status = "\u2717";
    }
}
