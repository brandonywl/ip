import Duke.Commands.Parser.Parser;
import Duke.Constants.Commands.Commands;
import Duke.Constants.Messages.Errors;
import Duke.Constants.Messages.Messages;
import Duke.Exceptions.NoInputTimingException;
import Duke.Exceptions.WrongPrefixException;
import Duke.TaskManager.TaskManager;
import Duke.UI.Printer;

import java.util.ArrayList;
import java.util.Scanner;



/**
 * Main class that runs the chat bot. Tasks are managed by the Duke.TaskManager.TaskManager
 * Duke provides the extraction of action words, descriptions and due dates
 * Duke also provides methods to print useful messages
 */
public class Duke {
    // CONSTANTS

    public static void main(String[] args) {

        // Startup and Initializations
        Scanner sc = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();
        String currentUserInput = "";
        String actionWord;
        String description;
        String timing;
        String[] processedUserInput;
        int index;

        // TODO: In CLI, up and down arrow inputs are parsed by the parser.
        // Introduce the bot after startup
        Printer.printIntroduction(Messages.LOGO);
        while (!currentUserInput.equalsIgnoreCase(Commands.EXIT_COMMAND)) {
            ArrayList<String> outputMessages;
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
                processedUserInput = Parser.processInput(currentUserInput);
            } catch (NoSuchFieldException | NoInputTimingException | WrongPrefixException e) {
                continue;
            }

            // actionWord has not been confirmed if it is correct yet.
            actionWord = processedUserInput[0];

            switch (actionWord) {
            case Commands.EXIT_COMMAND:
                break;

            case Commands.LIST_COMMAND:
                Printer.printTasks(taskManager);
                break;

            case Commands.SAVE_COMMAND:
                taskManager.outputTasks();
                Printer.printMessage("Done!");
                break;

            case Commands.COMPLETE_COMMAND:
                try {
                    index = Integer.parseInt(processedUserInput[1]);
                    if (index < 1 || index > taskManager.getNumberOfTasks()) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    Printer.printError(Errors.INDEX_VALUE_ERROR);
                    continue;
                }
                outputMessages = taskManager.completeTask(index);
                Printer.printMessage(outputMessages);
                break;

            case Commands.DELETE_COMMAND:
                try {
                    index = Integer.parseInt(processedUserInput[1]);
                    if (index < 1 || index > taskManager.getNumberOfTasks()) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    Printer.printError(Errors.INDEX_VALUE_ERROR);
                    continue;
                }

                outputMessages = taskManager.deleteTask(index);
                Printer.printMessage(outputMessages);
                break;

            case Commands.TODO_COMMAND:
                description = processedUserInput[1];
                outputMessages = taskManager.addTodo(description);
                Printer.printMessage(outputMessages);
                taskManager.outputTasks();
                break;

            case Commands.DEADLINE_COMMAND:
                description = processedUserInput[1];
                timing = processedUserInput[2];
                outputMessages = taskManager.addDeadline(description, timing);
                Printer.printMessage(outputMessages);
                taskManager.outputTasks();
                break;

            case Commands.EVENT_COMMAND:
                description = processedUserInput[1];
                timing = processedUserInput[2];
                outputMessages = taskManager.addEvent(description, timing);
                Printer.printMessage(outputMessages);
                taskManager.outputTasks();
                break;

            default:
                Printer.printError();
            }
        }
        Printer.printExitMessage();

    }

}
