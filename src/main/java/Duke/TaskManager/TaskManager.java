package Duke.TaskManager;

import Duke.Commands.Parser.Parser;
import Duke.Data.FileManager;
import Duke.Exceptions.WrongPrefixException;
import Duke.TaskTypes.Deadline;
import Duke.TaskTypes.Event;
import Duke.TaskTypes.Task;
import Duke.TaskTypes.Todo;
import Duke.UI.Printer;

import java.util.ArrayList;


public class TaskManager {
    private final ArrayList<Task> tasks = new ArrayList<>();
    private FileManager fileManager = new FileManager();
    private final static String dumpLoc = "data/dump.txt";

    public TaskManager() {
        try {
            fileManager = new FileManager(dumpLoc);
            importTask();
        } catch (WrongPrefixException e) {
            e.printStackTrace();
        }
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

    public void addTodo(String task, boolean status) {
        Todo newTask = new Todo(task, status);
        addTask(newTask);
        generateAddTaskMessages();
    }

    public ArrayList<String> addDeadline(String task, String deadline) {
        Deadline newDeadline = new Deadline(task, deadline);
        addTask(newDeadline);
        return generateAddTaskMessages();
    }

    public void addDeadline(String task, boolean status, String deadline) {
        Deadline newDeadline = new Deadline(task, status, deadline);
        addTask(newDeadline);
        generateAddTaskMessages();
    }

    public ArrayList<String> addEvent(String task, String startTime) {
        Event newEvent = new Event(task, startTime);
        addTask(newEvent);
        return generateAddTaskMessages();
    }

    public void addEvent(String task, boolean status, String startTime) {
        Event newEvent = new Event(task, status, startTime);
        addTask(newEvent);
        generateAddTaskMessages();
    }

    public void findTask(String keyword) {
        ArrayList<Task> subset = new ArrayList<>();
        for (Task task : tasks) {
            String job = task.getJob();
            if (job.contains(keyword)) {
                subset.add(task);
            }
        }
        Printer.printTasks(subset);
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
        fileManager.outputTasks(this);
    }

    public void importTask() throws WrongPrefixException {
        String dumpData = fileManager.readFromFile();
        String[] tasks = dumpData.split("\n");

        for (String line : tasks) {
            Parser.parseTasks(this, line);
        }
    }
}
