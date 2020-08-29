import java.util.Scanner;


/**
 * Main class that runs the chat bot. Tasks are managed by the TaskManager
 * Duke provides the extraction of action words, descriptions and due dates
 * Duke also provides methods to print useful messages
 */
public class Duke {

    public static final String EXIT_COMMAND = "BYE";
    public static final String LIST_COMMAND = "LIST";
    public static final String COMPLETE_COMMAND = "DONE";
    public static final String TODO_COMMAND = "TODO";
    public static final String DEADLINE_COMMAND = "DEADLINE";
    public static final String EVENT_COMMAND = "EVENT";
    public static final String LOGO = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";
    public static final String DEADLINE_PREFIX = "/by";
    public static final String EVENT_PREFIX = "/at";

    public static void main(String[] args) {

        // Startup and Initializations
        Scanner sc = new Scanner(System.in);
        String currentUserInput = "";
        TaskManager taskManager = new TaskManager();
        String actionWord;
        String description;
        String timing;
        int timingIndex;
        int descriptionIndex;

        // Introduce the bot after startup
        printIntroduction(LOGO);
            while (!currentUserInput.equalsIgnoreCase(EXIT_COMMAND)) {
            currentUserInput = sc.nextLine();
            // actionWord will always be the first word in the sentence
            actionWord = currentUserInput.toUpperCase().split(" ")[0];
            // Find the first " ". If it does not exist, the command would not use the index anyway. If it does
            // + 1 to remove the space and get the first index of the description.
            descriptionIndex = currentUserInput.indexOf(" ") + 1;

            String[] outputMessages;
            switch (actionWord) {
            case EXIT_COMMAND:
                break;

            case LIST_COMMAND:
                printTasks(taskManager);
                break;

            case COMPLETE_COMMAND:
                // Number of task will be after done. Hence index is 1.
                int index = Integer.parseInt(currentUserInput.split(" ")[1]);
                taskManager.completeTask(index);
                Task tempTask = taskManager.tasks[index - 1];
                outputMessages = new String[]{
                        "Nice! I've marked this task as done:",
                        "\t" + tempTask.toString()};
                printMessage(outputMessages);
                break;

            case TODO_COMMAND:
                description = currentUserInput.substring(descriptionIndex);
                taskManager.addTask(description);

                outputMessages = generateAddTaskMessages(taskManager);
                printMessage(outputMessages);
                break;

            case DEADLINE_COMMAND:
                timingIndex = currentUserInput.lastIndexOf(DEADLINE_PREFIX);

                description = currentUserInput.substring(descriptionIndex, timingIndex);
                // Remove "/by " by adding 4 to the starting index
                timing = currentUserInput.substring(timingIndex + 4);
                taskManager.addDeadline(description, timing);

                outputMessages = generateAddTaskMessages(taskManager);
                printMessage(outputMessages);
                break;

            case EVENT_COMMAND:
                timingIndex = currentUserInput.lastIndexOf(EVENT_PREFIX);

                description = currentUserInput.substring(descriptionIndex, timingIndex);
                // Remove "/at " by adding 3 to the starting index
                timing = currentUserInput.substring(timingIndex + 4);
                taskManager.addEvent(description, timing);

                outputMessages = generateAddTaskMessages(taskManager);
                printMessage(outputMessages);
                break;

            default:
                printMessage("Unknown Command! Try again!");
            }
        }

        printExitMessage();

    }

    /**
     * Generates a standard message to print when a new task is added. Retrieves the last task and prints it's
     * information.
     * @param taskManager TaskManager Object.
     * @return Returns a standard String Array to be printed.
     */
    public static String[] generateAddTaskMessages(TaskManager taskManager) {
        Task latestTask = taskManager.getLatestTask();
        return new String[]{
                "Got it. I've added this task:",
                "\t" + latestTask.toString(),
                String.format("Now you have %d tasks in the list.", taskManager.getNumberOfTasks())
        };
    }

    /**
     * Prints a line to isolate Duke's replies.
     */
    public static void printDivider() {
        String s = "------------------------------------------------";
        System.out.println(s);
    }

    /**
     * Prints the introduction given a text-based logo.
     * @param logo String that represents the logo of the chat-bot.
     */
    public static void printIntroduction(String logo) {
        printDivider();
        System.out.println("Hello I'm \n" + logo);
        System.out.println("What can I do for you?");
        printDivider();
    }

    /**
     * Prints an exit message.
     */
    public static void printExitMessage() {
        printMessage("Bye. Hope to see you again soon!");
    }

    /**
     * General purpose method to print a string between two dividers.
     * @param message Message to be printed.
     */
    public static void printMessage(String message) {
        printDivider();
        System.out.println(message);
        printDivider();
    }

    /**
     * General purpose method to print multiple strings between two dividers.
     * @param messages An array of messages to be printed
     */
    public static void printMessage(String[] messages) {
        printDivider();
        for (String message : messages) {
            System.out.println(message);
        }
        printDivider();
    }

    /**
     * Method to print all tasks stored inside the Task Manager
     * @param taskManager TaskManager Object used to retrieve a list of all tasks as well as number of tasks.
     */
    public static void printTasks(TaskManager taskManager) {
        Task[] tasks = taskManager.getTasks();
        int numOfTasks = taskManager.getNumberOfTasks();
        String[] messages = new String[numOfTasks];

        if (numOfTasks == 0) {
            System.out.println();
        }
        for (int i = 0; i < numOfTasks; i++) {
            String outputMessage = String.format("%d.\t%s", i + 1, tasks[i].toString());
            messages[i] = outputMessage;
        }
        printMessage(messages);
    }
}
