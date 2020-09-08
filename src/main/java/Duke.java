import java.util.Scanner;


/**
 * Main class that runs the chat bot. Tasks are managed by the TaskManager
 * Duke provides the extraction of action words, descriptions and due dates
 * Duke also provides methods to print useful messages
 */
public class Duke {
    // CONSTANTS

    // Logo
    public static final String LOGO = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";

    // Command list
    public static final String EXIT_COMMAND = "BYE";
    public static final String LIST_COMMAND = "LIST";
    public static final String COMPLETE_COMMAND = "DONE";
    public static final String TODO_COMMAND = "TODO";
    public static final String DEADLINE_COMMAND = "DEADLINE";
    public static final String DEADLINE_PREFIX = "/by";
    public static final String EVENT_COMMAND = "EVENT";
    public static final String EVENT_PREFIX = "/at";

    // Consolidated Command List by Type
    public static final String[] SINGLE_WORD_COMMANDS = new String[]{EXIT_COMMAND, LIST_COMMAND};
    public static final String[] DOUBLE_WORD_COMMANDS = new String[]{COMPLETE_COMMAND, TODO_COMMAND};
    public static final String[] TRIPLE_WORD_COMMANDS = new String[]{DEADLINE_COMMAND, EVENT_COMMAND};

    // Duke's Situational Messages
    public static final String EMPTY_TASK_LIST_MESSAGE = "Seems like it's really empty here :(";

    // Duke Error Messages
    public static final String NO_DESCRIPTION_ERROR = "Sorry! There seems to be a lack of description for the task you want to add!";
    public static final String NO_TIMING_ERROR = "Sorry! There seems to be a lack of timing for the task you want to add!";
    public static final String INDEX_VALUE_ERROR = "Index is not a correct number!";
    public static final String WRONG_PREFIX_ERROR = "You used the wrong prefix!";
    public static final String PREFIX_ERROR = "Please use %s to indicate the timing";

    // Duke Suggested Solutions
    public static final String NO_DESCRIPTION_SOLUTION = "Please add a descriptor after the action word with. Ensure there is a space in the middle.";

    public static void main(String[] args) {

        // Startup and Initializations
        Scanner sc = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();
        String currentUserInput = "";
        String actionWord;
        String description;
        String timing;
        String[] processedUserInput;

        // Introduce the bot after startup
        printIntroduction(LOGO);
        while (!currentUserInput.equalsIgnoreCase(EXIT_COMMAND)) {
            String[] outputMessages;
            currentUserInput = sc.nextLine().strip();
            /*
             * Cases
             * 1. User inputs the correct input (COMMAND DESCRIPTOR TIMING)
             *      - Switch case should handle the event as per normal
             * 2. User inputs only the command (COMMAND)
             *      - If the command does not require descriptor or timing, carry on.
             *      - In the event of tasks, throw descriptor missing
             *      - Worry about timing in case 5.
             * 3. User has multiple spaces between the three keywords
             *      - Remove the null strings in the array
             * 4. User has no spaces between the three keywords
             *      - Treat it as wrong command
             * 5. User is missing timing when required
             *      - In the event of event and deadline, throw timing missing
             * 6. User input wrong command
             *      - Throw unknown command
             */

            // Try to process user input. If exception is thrown, deal with the error and carry on with next loop.
            try {
                processedUserInput = processInput(currentUserInput);
            } catch (NoSuchFieldException | NoInputTimingException | WrongPrefixException e) {
                continue;
            }

            // actionWord has not been confirmed if it is correct yet.
            actionWord = processedUserInput[0];

            switch (actionWord) {
            case EXIT_COMMAND:
                break;

            case LIST_COMMAND:
                printTasks(taskManager);
                break;

            case COMPLETE_COMMAND:
                int index;
                try {
                    index = Integer.parseInt(processedUserInput[1]);
                } catch (NumberFormatException e) {
                    printError(INDEX_VALUE_ERROR);
                    continue;
                }
                outputMessages = taskManager.completeTask(index);
                printMessage(outputMessages);
                break;

            case TODO_COMMAND:
                description = processedUserInput[1];
                outputMessages = taskManager.addTodo(description);
                printMessage(outputMessages);
                break;

            case DEADLINE_COMMAND:
                description = processedUserInput[1];
                timing = processedUserInput[2];
                outputMessages = taskManager.addDeadline(description, timing);
                printMessage(outputMessages);
                break;

            case EVENT_COMMAND:
                description = processedUserInput[1];
                timing = processedUserInput[2];
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

    public static void printErrors(String[] messages){
        String[] errorMessages = new String[2+messages.length];
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
            messages = new String[] {EMPTY_TASK_LIST_MESSAGE};
        }
        for (int i = 0; i < numOfTasks; i++) {
            String outputMessage = String.format("%d.\t%s", i + 1, tasks[i].toString());
            messages[i] = outputMessage;
        }
        printMessage(messages);
    }

    public static String[] processInput(String input) throws NoSuchFieldException,
            NoInputTimingException, IllegalStateException, WrongPrefixException {
        String actionWord = extractActionWord(input)[0];
        String description;
        String timing;

        if (contains(SINGLE_WORD_COMMANDS, actionWord)) {
            return new String[]{actionWord};
        } else if (contains(DOUBLE_WORD_COMMANDS, actionWord)) {
            try {
                description = extractDescription(input, actionWord);

                return new String[]{
                        actionWord,
                        description
                };
            } catch (NoSuchFieldException e) {
                String[] errorMessages = new String[] {
                        NO_DESCRIPTION_ERROR,
                        NO_DESCRIPTION_SOLUTION
                };
                printErrors(errorMessages);
                throw e;
            }
        } else if (contains(TRIPLE_WORD_COMMANDS, actionWord)) {
            try {
                description = extractDescription(input, actionWord);
                timing = extractTiming(input, actionWord);

                return new String[]{
                        actionWord,
                        description,
                        timing
                };
            } catch (NoSuchFieldException e) {
                String[] errorMessages = new String[] {
                        NO_DESCRIPTION_ERROR,
                        NO_DESCRIPTION_SOLUTION
                };
                printErrors(errorMessages);
                throw e;
            } catch (NoInputTimingException e) {
                String[] errorMessages;
                String prefix = getPrefix(actionWord);

                if (!prefix.isBlank()) {
                    errorMessages = new String[] {
                            NO_TIMING_ERROR,
                            String.format(PREFIX_ERROR, prefix)
                    };
                    printErrors(errorMessages);
                } else {
                    printError(NO_TIMING_ERROR);
                }

                throw e;
            } catch (WrongPrefixException e) {
                String[] errorMessages;
                String prefix = getPrefix(actionWord);

                if (!prefix.isBlank()) {
                    errorMessages = new String[] {
                            WRONG_PREFIX_ERROR,
                            String.format("Please use %s to indicate the timing", prefix)
                    };
                    printErrors(errorMessages);
                } else {
                    printError(WRONG_PREFIX_ERROR);
                }

                throw e;
            }
        } else {
            // Unknown command. Throw to switch case to handle.
            return new String[]{actionWord};
        }
    }

    private static String[] extractActionWord(String input) {
        return new String[]{input.split(" ")[0].toUpperCase()};
    }

    private static String extractDescription(String input, String actionWord) throws NoSuchFieldException {
        // First letter of description is at actionWord length + 1 to account for space bar
        int descriptionIndex = actionWord.length() + 1;
        // If the index is out of range, description must be missing.
        if (descriptionIndex >= input.length()) {
            throw new NoSuchFieldException();
        }

        String description = input.substring(descriptionIndex).strip();
        if (description.isBlank()) {
            throw new NoSuchFieldException();
        } else {
            // Find if there is a timing command by finding indexOf "/"
            int timingIndex = description.indexOf("/");
            if (timingIndex > -1) {
                description = description.substring(0, timingIndex).strip();
            }
            if (description.isBlank()) {
                throw new NoSuchFieldException();
            } else {
                return description;
            }
        }
    }

    private static String extractTiming(String input, String actionWord)
            throws NoInputTimingException, WrongPrefixException {
        String prefix;
        int timingIndex;

        prefix = getPrefix(actionWord);

        if (prefix.isBlank()) {
            throw new NoInputTimingException();
        }

        timingIndex = input.indexOf(prefix);

        // Prefix not found
        if (timingIndex == -1) {
            if (!input.contains("/")) {
                throw new NoInputTimingException();
            }
            throw new WrongPrefixException();
        }

        timingIndex += prefix.length();

        // Prefix found but nothing after that
        if (timingIndex >= input.length()) {
            throw new NoInputTimingException();
        }
        String timing = input.substring(timingIndex).strip();

        // Prefix found but after strip, nothing found after it
        if (timing.isBlank()) {
            throw new NoInputTimingException();
        } else {
            return timing;
        }

    }

    public static boolean contains(String[] array, String actionWord) {
        for (String keyword : array) {
            if (keyword.equals(actionWord)) {
                return true;
            }
        }
        return false;
    }

    public static String getPrefix(String actionWord) {
        switch(actionWord) {
        case DEADLINE_COMMAND:
            return DEADLINE_PREFIX;
        case EVENT_COMMAND:
            return EVENT_PREFIX;
        default:
            return "";
        }
    }
}
