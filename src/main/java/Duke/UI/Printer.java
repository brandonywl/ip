package Duke.UI;

import Duke.Constants.Messages.Messages;
import Duke.TaskManager.TaskManager;
import Duke.TaskTypes.Task;

import java.util.ArrayList;
import java.util.Collections;

/**
 *  Provides methods to print onto the CLI.
 */
public class Printer {

    private static void printDivider() {
        String line = "------------------------------------------------";
        System.out.println(line);
    }

    /**
     * Prints out the logo of the chat bot between two dividers.
     *
     * @param logo Logo of the chat bot
     */
    public static void printIntroduction(String logo) {
        printDivider();
        System.out.println("Hello I'm \n" + logo);
        System.out.println("What can I do for you?");
        printDivider();
    }

    /** Prints a message between two dividers
     *
     * @param message Message to be printed
     */
    public static void printMessage(String message) {
        printDivider();
        System.out.println(message);
        printDivider();
    }

    /**
     * Prints messages between two dividers
     * @param messages String Array Type. Allows printing of more than one line between the dividers.
     */
    public static void printMessage(String[] messages) {
        printDivider();
        for (String message : messages) {
            System.out.println(message);
        }
        printDivider();
    }

    /**
     * Prints messages between two dividers
     * @param messages ArrayList Type. Allows printing of more than one line between the dividers.
     */
    public static void printMessage(ArrayList<String> messages) {
        printDivider();
        for (String message : messages) {
            System.out.println(message);
        }
        printDivider();
    }

    /**
     *  Prints a standard error message upon exit command
     */
    public static void printExitMessage() {
        printMessage("Bye. Hope to see you again soon!");
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

    /**
     *  Prints a standard error message of an unknown command.
     */
    public static void printError() {
        printMessage(new String[]{"Error!", "", "Unknown Command! Try again!"});
    }

    /**
     * Prints a specified error message.
     * @param message Error message based on situation.
     */
    public static void printError(String message) {
        printMessage(new String[]{"Error!", "", message});
    }

    /**
     * Provides a overloaded method that allows for multiple error messages as arguments.
     * @param messages Error messages.
     */
    public static void printError(String... messages) {
        ArrayList<String> errors = new ArrayList<>();
        Collections.addAll(errors, messages);
        printMessage(errors);
    }
}
