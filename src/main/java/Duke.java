import java.util.Scanner;


public class Duke {

    public static final String EXIT_COMMAND = "BYE";
    public static final String LIST_COMMAND = "LIST";
    public static final String COMPLETE_COMMAND = "DONE";
    public static final String TODO_COMMAND = "TODO";
    public static final String DEADLINE_COMMAND = "DEADLINE";
    public static final String EVENT_COMMAND = "EVENT";

    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";

        printIntroduction(logo);
        Scanner sc = new Scanner(System.in);
        String currentUserInput = "";
        TaskManager taskManager = new TaskManager();
        String actionWord;
        String description;
        String timing;
        int timingIndex;
        int descriptionIndex;


        while (!currentUserInput.equalsIgnoreCase("bye")) {
            currentUserInput = sc.nextLine();
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
                int index = Integer.parseInt(currentUserInput.split(" ")[1]);
                taskManager.completeTask(index);
                Task tempTask = taskManager.tasks[index - 1];
                outputMessages = new String[] {
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
                    timingIndex = currentUserInput.lastIndexOf("/by");

                    description = currentUserInput.substring(descriptionIndex, timingIndex);
                    // Remove /by by adding 3 to the starting index
                    timing = currentUserInput.substring(timingIndex + 3);
                    taskManager.addDeadline(description, timing);

                    outputMessages = generateAddTaskMessages(taskManager);
                    printMessage(outputMessages);
                    break;

                case EVENT_COMMAND:
                    timingIndex = currentUserInput.lastIndexOf(("/at"));

                    description = currentUserInput.substring(descriptionIndex, timingIndex);
                    // Remove /at by adding 3 to the starting index
                    timing = currentUserInput.substring(timingIndex + 3);
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

    public static String[] generateAddTaskMessages(TaskManager taskManager) {
        Task latestTask = taskManager.getLatestTask();
        return new String[] {
                "Got it. I've added this task:",
                "\t" + latestTask.toString(),
                String.format("Now you have %d tasks in the list.", taskManager.getNumberOfTasks())
        };
    }

    public static void printDivider() {
        String s = "------------------------------------------------";
        System.out.println(s);
    }

    public static void printIntroduction(String logo) {
        printDivider();
        System.out.println("Hello I'm \n" + logo);
        System.out.println("What can I do for you?");
        printDivider();
    }

    public static void printExitMessage() {
        printMessage("Bye. Hope to see you again soon!");
    }

    public static void printMessage(String s) {
        printDivider();
        System.out.println(s);
        printDivider();
    }

    public static void printMessage(String[] s) {
        printDivider();
        for (String message : s) {
            System.out.println(message);
        }
        printDivider();
    }

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
