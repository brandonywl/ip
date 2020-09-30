package Duke.Commands.Parser;

import Duke.Constants.Commands.Commands;
import Duke.Constants.Messages.Errors;
import Duke.Exceptions.NoInputTimingException;
import Duke.Exceptions.WrongPrefixException;
import Duke.TaskManager.TaskManager;
import Duke.UI.Printer;

public class Parser {
    public static String[] processInput(String input) throws NoSuchFieldException,
            NoInputTimingException, IllegalStateException, WrongPrefixException {
        String actionWord = extractActionWord(input)[0];
        String description;
        String timing;

        if (contains(Commands.SINGLE_WORD_COMMANDS, actionWord)) {
            return new String[]{actionWord};
        } else if (contains(Commands.DOUBLE_WORD_COMMANDS, actionWord)) {
            try {
                description = extractDescription(input, actionWord);

                return new String[]{
                        actionWord,
                        description
                };
            } catch (NoSuchFieldException e) {
                String[] errorMessages = new String[]{
                        Errors.NO_DESCRIPTION_ERROR,
                        Errors.NO_DESCRIPTION_SOLUTION
                };
                Printer.printErrors(errorMessages);
                throw e;
            }
        } else if (contains(Commands.TRIPLE_WORD_COMMANDS, actionWord)) {
            try {
                description = extractDescription(input, actionWord);
                timing = extractTiming(input, actionWord);

                return new String[]{
                        actionWord,
                        description,
                        timing
                };
            } catch (NoSuchFieldException e) {
                String[] errorMessages = new String[]{
                        Errors.NO_DESCRIPTION_ERROR,
                        Errors.NO_DESCRIPTION_SOLUTION
                };
                Printer.printErrors(errorMessages);
                throw e;
            } catch (NoInputTimingException e) {
                String[] errorMessages;
                String prefix = getPrefix(actionWord);

                if (!prefix.isBlank()) {
                    errorMessages = new String[]{
                            Errors.NO_TIMING_ERROR,
                            String.format(Errors.PREFIX_ERROR, prefix)
                    };
                    Printer.printErrors(errorMessages);
                } else {
                    Printer.printError(Errors.NO_TIMING_ERROR);
                }

                throw e;
            } catch (WrongPrefixException e) {
                String[] errorMessages;
                String prefix = getPrefix(actionWord);

                if (!prefix.isBlank()) {
                    errorMessages = new String[]{
                            Errors.WRONG_PREFIX_ERROR,
                            String.format("Please use %s to indicate the timing", prefix)
                    };
                    Printer.printErrors(errorMessages);
                } else {
                    Printer.printError(Errors.WRONG_PREFIX_ERROR);
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
        switch (actionWord) {
        case Commands.DEADLINE_COMMAND:
            return Commands.DEADLINE_PREFIX;
        case Commands.EVENT_COMMAND:
            return Commands.EVENT_PREFIX;
        default:
            return "";
        }
    }

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
