package Duke.UI;

import Duke.Constants.Messages.Messages;
import Duke.TaskManager.TaskManager;
import Duke.TaskTypes.Task;

import java.util.ArrayList;

public class Printer {
    /**
     * Prints a line to isolate Duke's replies.
     */
    public static void printDivider() {
        String line = "------------------------------------------------";
        System.out.println(line);
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
        printMessage(new String[]{"Error!", "", "Unknown Command! Try again!"});
    }

    public static void printError(String message) {
        printMessage(new String[]{"Error!", "", message});
    }

    public static void printErrors(String[] messages) {
        String[] errorMessages = new String[2 + messages.length];
        errorMessages[0] = "Error!";
        errorMessages[1] = "";
        System.arraycopy(messages, 0, errorMessages, 2, messages.length);
        printMessage(errorMessages);
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

    public static void printMessage(ArrayList<String> messages) {
        printDivider();
        for (String message : messages) {
            System.out.println(message);
        }
        printDivider();
    }


    public static void printTasks(ArrayList<Task> tasks) {
        int numOfTasks = tasks.size();
        ArrayList<String> messages = new ArrayList<>();

        if (numOfTasks == 0) {
            messages.add(Messages.EMPTY_TASK_LIST_MESSAGE);
        }
        for (int i = 0; i < numOfTasks; i++) {
            String outputMessage = String.format("%d.\t%s", i + 1, tasks.get(i).toString());
            messages.add(outputMessage);
        }
        messages.add(0, "Here are the tasks we found!\n");
        printMessage(messages);
    }
}
