package Duke.Commands.Parser;

import Duke.Constants.Commands.Commands;
import Duke.Constants.Messages.Errors;
import Duke.Exceptions.WrongPrefixException;
import Duke.TaskManager.TaskManager;
import Duke.UI.Printer;

import java.util.ArrayList;

public class Parser {

    /**
     * Parses the input based on keywords and syntax. Obtains keywords, descriptors and timings from the input.
     * @param input User input for the command Duke is to execute.
     * @return String Array containing parsed information.
     */
    public static ArrayList<String> parseInput(String input) {
        ArrayList<String> results = new ArrayList<>();
        String actionWord = extractActionWord(input)[0];
        String description;
        String timing;
        int typeOfAction = checkTypeOfActionWord(actionWord);
        results.add(actionWord);

        // Returns the most cleaned actionWord for the TaskManager to handle.
        if (typeOfAction == 1 || typeOfAction == -1) {
            return results;
        }

        // Extract Description & Check need to terminate
        description = extractDescription(input, actionWord);
        if (description.equals("")) {
            return new ArrayList<>();
        }
        results.add(description);

        if (typeOfAction == 2) {
            return results;
        }

        timing = extractTiming(input, actionWord);
        if (timing.equals("")) {
            return new ArrayList<>();
        }
        results.add(timing);

        if (typeOfAction == 3) {
            return results;
        } else {
            Printer.printError(Errors.UNKNOWN_ERROR);
            return new ArrayList<>();
        }

    }

    private static String[] extractActionWord(String input) {
        return new String[]{input.split(" ")[0].toUpperCase()};
    }

    private static int checkTypeOfActionWord(String actionWord) {
        if (contains(Commands.SINGLE_WORD_COMMANDS, actionWord)) {
            return 1;
        } else if (contains(Commands.DOUBLE_WORD_COMMANDS, actionWord)) {
            return 2;
        } else if (contains(Commands.TRIPLE_WORD_COMMANDS, actionWord)) {
            return 3;
        } else {
            return -1;
        }
    }

    private static String extractDescription(String input, String actionWord) {
        // First letter of description is at actionWord length + 1 to account for space bar
        int descriptionIndex = actionWord.length() + 1;
        // If the index is out of range, description must be missing.
        if (descriptionIndex >= input.length()) {
            Printer.printError(Errors.NO_DESCRIPTION_ERROR, Errors.NO_DESCRIPTION_SOLUTION);
            return "";
        }

        String description = input.substring(descriptionIndex).strip();
        if (description.isBlank()) {
            Printer.printError(Errors.NO_DESCRIPTION_ERROR, Errors.NO_DESCRIPTION_SOLUTION);
            return "";
        } else {
            // Find if there is a timing command by finding indexOf "/"
            int timingIndex = description.indexOf("/");
            if (timingIndex > -1) {
                description = description.substring(0, timingIndex).strip();
            }
            if (description.isBlank()) {
                Printer.printError(Errors.NO_DESCRIPTION_ERROR, Errors.NO_DESCRIPTION_SOLUTION);
                return "";
            } else {
                return description;
            }
        }
    }

    private static String extractTiming(String input, String actionWord) {
        String prefix;
        int timingIndex;

        prefix = getPrefix(actionWord);

        // TypeOfAction >= 3 but no prefix stored in enum.
        if (prefix.isBlank()) {
            Printer.printError(Errors.NO_PREFIX_REQUIRED);
            return "";
        }

        timingIndex = input.indexOf(prefix);

        // Prefix not found
        if (timingIndex == -1) {
            // If / is not used at all
            if (!input.contains("/")) {
                Printer.printError(Errors.NO_TIMING_ERROR);
                return "";
            } else {
                // Wrong prefix used
                Printer.printError(Errors.WRONG_PREFIX_ERROR, String.format(Errors.PREFIX_ERROR, prefix));
                return "";
            }
        }

        timingIndex += prefix.length();

        // Prefix found but nothing after that
        if (timingIndex >= input.length()) {
            Printer.printError(Errors.NO_TIMING_ERROR);
            return "";
        }
        String timing = input.substring(timingIndex).strip();

        // Prefix found but after strip, nothing found after it
        if (timing.isBlank()) {
            Printer.printError(Errors.NO_TIMING_ERROR);
            return "";
        } else {
            return timing;
        }

    }

    private static boolean contains(String[] array, String actionWord) {
        for (String keyword : array) {
            if (keyword.equals(actionWord)) {
                return true;
            }
        }
        return false;
    }

    private static String getPrefix(String actionWord) {
        switch (actionWord) {
        case Commands.DEADLINE_COMMAND:
            return Commands.DEADLINE_PREFIX;
        case Commands.EVENT_COMMAND:
            return Commands.EVENT_PREFIX;
        default:
            return "";
        }
    }

    /**
     * Parses an input line into format for tasks and adds it into the taskManager list.
     *
     * @param taskManager TaskManager to hold tasks.
     * @param line Line from dump file.
     * @throws WrongPrefixException If user-edited TaskType is not legal.
     */
    public static void parseTasks(TaskManager taskManager, String line) throws WrongPrefixException {
        String[] attributes = line.split("\\|");
        String taskType = attributes[1];
        boolean taskStatus = Boolean.parseBoolean(attributes[2]);
        String description = attributes[3];
        String timing = "";
        if (attributes.length > 4) {
            timing = attributes[4];
        }
        switch (taskType){
        case "T":
            taskManager.addTodo(description, taskStatus);
            break;
        case "E":
            taskManager.addEvent(description, taskStatus, timing);
            break;
        case "D":
            taskManager.addDeadline(description, taskStatus, timing);
            break;
        default:
            throw new WrongPrefixException();
        }
    }
}
