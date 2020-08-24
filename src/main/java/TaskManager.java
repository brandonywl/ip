public class TaskManager {
    private int taskLimit = 100;
//    public String[] tasks = new String[taskLimit];
    public Task[] tasks = new Task[taskLimit];
    public int numberOfTasks = 0;

    public Task[] getTasks(){
        return this.tasks;
    }

    public int getNumberOfTasks(){
        return this.numberOfTasks;
    }

    public void addNumberOfTask(){
        this.numberOfTasks += 1;
    }

    public void addTask(String task) {
        if (getNumberOfTasks() < taskLimit) {
            Task newTask = new Task(task);
            tasks[getNumberOfTasks()] = newTask;
            addNumberOfTask();

        } else {
            System.out.println("Error. Maximum number of tasks hit!");
        }
    }

/*    public void printTasks(){
        if (getNumberOfTasks() > 0){

        }
        else{
            System.out.println("");
        }
    }*/
}
