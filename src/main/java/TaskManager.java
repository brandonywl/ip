import java.io.IOException;
import java.io.File;
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

    public ArrayList<String> addTodo(String task) {
        Todo newTask = new Todo(task);
        addTask(newTask);
        return generateAddTaskMessages();
    }

    public ArrayList<String> addDeadline(String task, String deadline) {
        Deadline newDeadline = new Deadline(task, deadline);
        addTask(newDeadline);
        return generateAddTaskMessages();

    }

    public ArrayList<String> addEvent(String task, String startTime) {
        Event newEvent = new Event(task, startTime);
        addTask(newEvent);
        return generateAddTaskMessages();
    }

    public ArrayList<String> completeTask(int userStipulatedIndex) {
        int index = userStipulatedIndex - 1;
        tasks.get(index).complete();
        ArrayList<String> outputMessage = new ArrayList<>();
        outputMessage.add("Nice! I've marked this task as done:");
        outputMessage.add("\t" + tasks.get(index));
        return outputMessage;
    }

    public ArrayList<String> deleteTask(int userStipulatedIndex) {
        int index = userStipulatedIndex - 1;
        Task remTask = tasks.get(index);
        tasks.remove(index);
        ArrayList<String> outputMessage = new ArrayList<>();
        outputMessage.add("Noted. I have removed this task:");
        outputMessage.add(remTask.toString());
        outputMessage.add(String.format("Now you have %d tasks in the list.", getNumberOfTasks()));
        return outputMessage;
    }

    /**
     * Generates a standard message to print when a new task is added. Retrieves the last task and prints it's
     * information.
     *
     * @return Returns a standard String Array to be printed.
     */
    public ArrayList<String> generateAddTaskMessages() {
        Task latestTask = getLatestTask();
        ArrayList<String> outputMessage = new ArrayList<>();
        outputMessage.add("Got it. I've added this task:");
        outputMessage.add("\t" + latestTask);
        outputMessage.add(String.format("Now you have %d tasks in the list.", getNumberOfTasks()));
        return outputMessage;
    }

    public String[] getTasksAsStrings() {
        String[] outputMessages = new String[getNumberOfTasks()];
        int i = 0;
        for (Task currTask : this.tasks) {
            if (currTask == null) {
                continue;
            }
            String task = currTask.toPlainText();
            outputMessages[i] = task;
            i++;
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
