package Duke.Constants.Messages;

/**
 * Provides an enum that holds general error messages used.
 */
public enum Errors {
    ;

    // Duke Suggested Solutions
    public static final String NO_DESCRIPTION_SOLUTION = "Please add a descriptor after the action word with. Ensure there is a space in the middle.";
    // Duke Error Messages
    public static final String NO_DESCRIPTION_ERROR = "Sorry! There seems to be a lack of description for the task you want to add!";
    public static final String NO_TIMING_ERROR = "Sorry! There seems to be a lack of timing for the task you want to add!";
    public static final String INDEX_VALUE_ERROR = "Index is not valid or is not an integer!";
    public static final String WRONG_PREFIX_ERROR = "You used the wrong prefix!";
    public static final String PREFIX_ERROR = "Please use %s to indicate the timing";
    public static final String NO_PREFIX_REQUIRED = "Please check if you require a timing for this command";
    public static final String UNKNOWN_ERROR = "You should not have hit here. Check array in Commands enums.";
    public static final String DUMP_LOCATION_ERROR = "Cannot create dump location. Check for errors.";
}
