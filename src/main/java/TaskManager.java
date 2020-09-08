public class TaskManager {
    private final int taskLimit;
    private int numberOfTasks;
    protected Task[] tasks;

    TaskManager(int taskLimit){
        this.taskLimit = taskLimit;
        tasks = new Task[taskLimit];
        numberOfTasks = 0;
    }

    TaskManager(){
        this(100);
    }

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

    protected void addTask(Task task) {
        tasks[getNumberOfTasks()] = task;
        addNumberOfTask();
    }

    public String[] addTodo(String task) {
        if (isUnderLimit()) {
            Task newTask = new Task(task);
            addTask(newTask);
            return generateAddTaskMessages();

        } else {
            return new String[] {"Error. Maximum number of tasks hit!"};
        }
    }

    public String[] addDeadline(String task, String deadline) {
        if (isUnderLimit()) {
            Deadline newDeadline = new Deadline(task, deadline);
            addTask(newDeadline);
            return generateAddTaskMessages();

        } else {
            return new String[] {"Error. Maximum number of tasks hit!"};
        }

    }

    public String[] addEvent(String task, String startTime) {
        if (isUnderLimit()) {
            Event newEvent = new Event(task, startTime);
            addTask(newEvent);
            return generateAddTaskMessages();

        } else {
            return new String[] {"Error. Maximum number of tasks hit!"};
        }
    }

    public boolean isUnderLimit() {
        return getNumberOfTasks() < taskLimit;
    }

    public String[] completeTask(int userStipulatedIndex) {
        int index = userStipulatedIndex - 1;
        tasks[index].complete();
        return new String[]{
                "Nice! I've marked this task as done:",
                "\t" + tasks[index]};
    }

    /**
     * Generates a standard message to print when a new task is added. Retrieves the last task and prints it's
     * information.
     * @return Returns a standard String Array to be printed.
     */
    public String[] generateAddTaskMessages() {
        Task latestTask = getLatestTask();
        return new String[]{
                "Got it. I've added this task:",
                "\t" + latestTask,
                String.format("Now you have %d tasks in the list.", getNumberOfTasks())
        };
    }


}
