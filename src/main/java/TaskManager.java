import java.util.ArrayList;

public class TaskManager {
    private final ArrayList<Task> tasks = new ArrayList<>();

    TaskManager() {
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public Task getLatestTask() {
        return tasks.get(tasks.size() - 1);
    }

    public int getNumberOfTasks() {
        return tasks.size();
    }

    protected void addTask(Task task) {
        tasks.add(task);
    }

    public String[] addTodo(String task) {
        Task newTask = new Task(task);
        addTask(newTask);
        return generateAddTaskMessages();
    }

    public String[] addDeadline(String task, String deadline) {
        Deadline newDeadline = new Deadline(task, deadline);
        addTask(newDeadline);
        return generateAddTaskMessages();

    }

    public String[] addEvent(String task, String startTime) {
        Event newEvent = new Event(task, startTime);
        addTask(newEvent);
        return generateAddTaskMessages();
    }

    public String[] completeTask(int userStipulatedIndex) {
        int index = userStipulatedIndex - 1;
        tasks.get(index).complete();
        return new String[]{
                "Nice! I've marked this task as done:",
                "\t" + tasks.get(index)};
    }

    public String[] deleteTask(int userStipulatedIndex) {
        int index = userStipulatedIndex - 1;
        Task remTask = tasks.get(index);
        tasks.remove(index);
        return new String[] {
                "Noted. I have removed this task:",
                remTask.toString(),
                String.format("Now you have %d tasks in the list.", getNumberOfTasks())
        };
    }

    /**
     * Generates a standard message to print when a new task is added. Retrieves the last task and prints it's
     * information.
     *
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
