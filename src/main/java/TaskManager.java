public class TaskManager {
    private int taskLimit = 100;
    public Task[] tasks = new Task[taskLimit];
    public int numberOfTasks = 0;

    public Task[] getTasks() {
        return tasks;
    }

    public Task getLatestTask() {
        return tasks[getNumberOfTasks() - 1];
    }

    public int getNumberOfTasks() {
        return numberOfTasks;
    }

    public void addNumberOfTask() {
        numberOfTasks += 1;
    }

    public void addTask(String task) {
        if (isUnderLimit()) {
            Task newTask = new Task(task);
            tasks[getNumberOfTasks()] = newTask;
            addNumberOfTask();

        } else {
            System.out.println("Error. Maximum number of tasks hit!");
        }
    }

    public void addDeadline(String task, String deadline) {
        if (isUnderLimit()) {
            Deadline newDeadline = new Deadline(task, deadline);
            tasks[getNumberOfTasks()] = newDeadline;
            addNumberOfTask();
        } else {
            System.out.println("Error. Maximum number of tasks hit!");
        }

    }

    public void addEvent(String task, String startTime) {
        if (isUnderLimit()) {
            Event newEvent = new Event(task, startTime);
            tasks[getNumberOfTasks()] = newEvent;
            addNumberOfTask();
        } else {
            System.out.println("Error. Maximum number of tasks hit!");
        }
    }

    public boolean isUnderLimit() {
        return getNumberOfTasks() < taskLimit;
    }

    public void completeTask(int userStipulatedIndex) {
        int index = userStipulatedIndex - 1;
        tasks[index].complete();
    }
}
