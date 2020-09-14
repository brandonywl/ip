import java.io.File;
import java.io.IOException;

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
            Todo newTask = new Todo(task);
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

    public String[] getTasksAsStrings() {
        String[] outputMessages = new String[getNumberOfTasks()];
        int i = 0;
        for (Task currTask : this.tasks) {
            if (currTask == null) {
                continue;
            }
            String task = currTask.toPlainText();
            outputMessages[i++] = task;
        }
        return outputMessages;
    }

    public void outputTasks() {
        String home = System.getProperty("user.dir");
        java.nio.file.Path saveFolderPath = java.nio.file.Paths.get(home, "data");
        String saveFolder = saveFolderPath.toString();
        java.nio.file.Path dumpFilePath = java.nio.file.Paths.get(saveFolder, "dump.txt");
        String dumpFile = dumpFilePath.toString();

        if (!java.nio.file.Files.exists(dumpFilePath)) {
            File file = new File(saveFolder);
            boolean success = file.mkdirs();
            String message = success ? "Made directories" : "Failed to make directories";
            System.out.println(message);
        }

        String[] outputMessages = getTasksAsStrings();

        WriteFile writer = new WriteFile(dumpFile);
        try {
            writer.writeToFile(outputMessages);
            System.out.println("Dump successful");
        } catch (IOException e) {
            System.out.println("Failed to dump file");
        }
    }
}
