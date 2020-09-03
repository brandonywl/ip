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
            currentUserInput = sc.nextLine().strip();
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
                // TODO: Error catch for no index of number
                int index = Integer.parseInt(currentUserInput.split(" ")[1]);
                outputMessages = taskManager.completeTask(index);
                printMessage(outputMessages);
                break;

            case TODO_COMMAND:
                if (descriptionIndex == 0) {
                    printError("There can't be no description!");
                    break;
                }
                description = currentUserInput.substring(descriptionIndex);
                outputMessages = taskManager.addTodo(description);
                printMessage(outputMessages);
                break;

            case DEADLINE_COMMAND:
                timingIndex = currentUserInput.lastIndexOf(DEADLINE_PREFIX);
                if (timingIndex == 0){
                    printError("Please put a deadline!");
                }
                if (timingIndex == descriptionIndex || descriptionIndex == 0){
                    printError("There can't be no description!");
                }
                description = currentUserInput.substring(descriptionIndex, timingIndex);
                // Remove "/by " by adding 4 to the starting index
                timing = currentUserInput.substring(timingIndex + 4);
                outputMessages = taskManager.addDeadline(description, timing);
                printMessage(outputMessages);
                break;

            case EVENT_COMMAND:
                timingIndex = currentUserInput.lastIndexOf(EVENT_PREFIX);
                if (timingIndex == 0){
                    printError("Please put a deadline!");
                }
                if (timingIndex == descriptionIndex || descriptionIndex == 0){
                    printError("There can't be no description!");
                }
                description = currentUserInput.substring(descriptionIndex, timingIndex);
                // Remove "/at " by adding 3 to the starting index
                timing = currentUserInput.substring(timingIndex + 4);
                outputMessages = taskManager.addEvent(description, timing);
                printMessage(outputMessages);
                break;

            default:
                printError();
            }
        }

        printExitMessage();

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
     *
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
     *
     * @param message Message to be printed.
     */
    public static void printMessage(String message) {
        printDivider();
        System.out.println(message);
        printDivider();
    }

    /**
     * Specialized printMessage function to throw an error message
     */
    public static void printError() {
        printMessage(new String[] {"Error!","","Unknown Command! Try again!"});
    }

    public static void printError(String message) {
        printMessage(new String[] {"Error!", "", message});
    }

    /**
     * General purpose method to print multiple strings between two dividers.
     *
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
     *
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
