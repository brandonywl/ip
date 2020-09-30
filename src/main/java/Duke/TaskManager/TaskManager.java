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
            load();
        } catch (WrongPrefixException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Get method for tasks in the TaskManager.
     * @return ArrayList of tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Gets the last task on the TaskManager.
     * @return Latest task added.
     */
    public Task getLatestTask() {
        return tasks.get(tasks.size() - 1);
    }

    /**
     * Gets number of tasks.
     * @return Number of tasks.
     */
    public int getNumberOfTasks() {
        return tasks.size();
    }

    /**
     *  Adds a task into the ArrayList of tasks attribute on TaskManager.
     * @param task Task to be added.
     */
    private void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Adds a To-do task into the ArrayList of tasks attribute on TaskManager.
     * @param task Task to be added
     * @return Completion Message.
     */
    public ArrayList<String> addTodo(String task) {
        Todo newTask = new Todo(task);
        addTask(newTask);
        return generateAddTaskMessages();
    }

    /**
     * Adds a To-do task into the ArrayList of tasks attribute on TaskManager. For loading purposes.
     * @param task Task to be added
     * @param status State of the task upon loading
     */
    public void addTodo(String task, boolean status) {
        Todo newTask = new Todo(task, status);
        addTask(newTask);
    }

    /**
     * Adds a Deadline task into the ArrayList of tasks attribute on TaskManager.
     * @param task Task to be added
     * @param deadline Time to be completed by.
     * @return Completion Message.
     */
    public ArrayList<String> addDeadline(String task, String deadline) {
        Deadline newDeadline = new Deadline(task, deadline);
        addTask(newDeadline);
        return generateAddTaskMessages();
    }

    /**
     * Adds a Deadline task into the ArrayList of tasks attribute on TaskManager. For loading purposes.
     * @param task Task to be added
     * @param status State of the task upon loading
     * @param deadline Time to be completed by.
     */
    public void addDeadline(String task, boolean status, String deadline) {
        Deadline newDeadline = new Deadline(task, status, deadline);
        addTask(newDeadline);
    }

    /**
     * Adds an Event task into the ArrayList of tasks attribute on TaskManager.
     * @param task Task to be added
     * @param startTime Time event is to start at.
     * @return Completion Message.
     */
    public ArrayList<String> addEvent(String task, String startTime) {
        Event newEvent = new Event(task, startTime);
        addTask(newEvent);
        return generateAddTaskMessages();
    }

    /**
     * Adds an Event task into the ArrayList of tasks attribute on TaskManager. For loading purposes.
     * @param task Task to be added
     * @param status State of the task upon loading
     * @param startTime Time event is to start at.
     */
    public void addEvent(String task, boolean status, String startTime) {
        Event newEvent = new Event(task, status, startTime);
        addTask(newEvent);
    }
  
    public void findTask(String keyword) {
        ArrayList<Task> subset = new ArrayList<>();
        for (Task task : tasks) {
            String job = task.getJob().toLowerCase();
            if (job.contains(keyword.toLowerCase())) {
                subset.add(task);
            }
        }
        Printer.printTasks(subset);
    }

    /**
     * Completes a stipulated task
     * @param userStipulatedIndex Index (Starting from 1) to be marked as complete.
     * @return Generated output message.
     */
    public ArrayList<String> completeTask(int userStipulatedIndex) {
        int index = userStipulatedIndex - 1;
        tasks.get(index).complete();
        ArrayList<String> outputMessage = new ArrayList<>();
        outputMessage.add("Nice! I've marked this task as done:");
        outputMessage.add("\t" + tasks.get(index));
        return outputMessage;
    }

    /**
     * Deletes a specific index from the ArrayList.
     * @param userStipulatedIndex Index (Starting from 1) to be deleted.
     * @return Generated output message.
     */
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
     * Generates a standard message when a task is added.
     * @return Generated messages.
     */
    public ArrayList<String> generateAddTaskMessages() {
        Task latestTask = getLatestTask();
        ArrayList<String> outputMessage = new ArrayList<>();
        outputMessage.add("Got it. I've added this task:");
        outputMessage.add("\t" + latestTask);
        outputMessage.add(String.format("Now you have %d tasks in the list.", getNumberOfTasks()));
        return outputMessage;
    }

    /**
     * Converts all tasks into string format for saving.
     * @return ArrayList of tasks in String type.
     */
    public ArrayList<String> getTasksAsStrings() {
        ArrayList<String> outputMessages = new ArrayList<>();
        for (Task currTask : this.tasks) {
            String task = currTask.toPlainText();
            outputMessages.add(task);
        }
        return outputMessages;
    }

    /**
     *  Dumps the current list of tasks into a file.
     */
    public void save() {
        fileManager.outputTasks(this);
    }

    /**
     * Loads the dumped file if any.
     * @throws WrongPrefixException User-edited dump file has a wrong prefix in the file.
     */
    public void load() throws WrongPrefixException {
        String dumpData = fileManager.readFromFile();
        String[] tasks = dumpData.split("\n");

        for (String line : tasks) {
            Parser.parseTasks(this, line);
        }
    }
}
