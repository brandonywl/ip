import Duke.Commands.Parser.Parser;
import Duke.Constants.Commands.Commands;
import Duke.Constants.Messages.Errors;
import Duke.Constants.Messages.Messages;
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
    public static void main(String[] args) {

        // Startup and Initializations
        Scanner sc = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();
        String currentUserInput = "";
        String actionWord;
        String description;
        String timing;
        ArrayList<String> processedUserInput;
        int index;

        // TODO: In CLI, up and down arrow inputs are parsed by the parser.
        // Introduce the bot after startup
        Printer.printIntroduction(Messages.LOGO);
        while (!currentUserInput.equalsIgnoreCase(Commands.EXIT_COMMAND)) {
            ArrayList<String> outputMessages;
            currentUserInput = sc.nextLine().strip();
            processedUserInput = Parser.parseInput(currentUserInput);

            if (processedUserInput.size() == 0) {
                continue;
            }
            // actionWord has not been confirmed if it is correct yet.
            actionWord = processedUserInput.get(0);

            switch (actionWord) {
            case Commands.EXIT_COMMAND:
                break;

            case Commands.LIST_COMMAND:
                Printer.printTasks(taskManager);
                break;

            case Commands.SAVE_COMMAND:
                taskManager.save();
                Printer.printMessage("Done!");
                break;

            case Commands.COMPLETE_COMMAND:
                try {
                    index = Integer.parseInt(processedUserInput.get(1));
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
                index = Integer.parseInt(processedUserInput.get(1));
                if (index < 1 || index > taskManager.getNumberOfTasks()) {
                    Printer.printError(Errors.INDEX_VALUE_ERROR);
                    continue;
                }

                outputMessages = taskManager.deleteTask(index);
                taskManager.save();
                Printer.printMessage(outputMessages);
                break;

            case Commands.TODO_COMMAND:
                description = processedUserInput.get(1);
                outputMessages = taskManager.addTodo(description);
                Printer.printMessage(outputMessages);
                taskManager.save();
                break;

            case Commands.DEADLINE_COMMAND:
                description = processedUserInput.get(1);
                timing = processedUserInput.get(2);
                outputMessages = taskManager.addDeadline(description, timing);
                Printer.printMessage(outputMessages);
                taskManager.save();
                break;

            case Commands.EVENT_COMMAND:
                description = processedUserInput.get(1);
                timing = processedUserInput.get(2);
                outputMessages = taskManager.addEvent(description, timing);
                Printer.printMessage(outputMessages);
                taskManager.save();
                break;

            default:
                Printer.printError();
            }
        }
        Printer.printExitMessage();
    }
}
