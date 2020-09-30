package Duke.Constants.Commands;

public enum Commands {
    ;

    // Command list
    public static final String EXIT_COMMAND = "BYE";
    public static final String LIST_COMMAND = "LIST";
    public static final String SAVE_COMMAND = "SAVE";
    public static final String COMPLETE_COMMAND = "DONE";
    public static final String TODO_COMMAND = "TODO";
    public static final String DELETE_COMMAND = "DELETE";
    public static final String DEADLINE_COMMAND = "DEADLINE";
    public static final String DEADLINE_PREFIX = "/by";
    public static final String EVENT_COMMAND = "EVENT";
    public static final String EVENT_PREFIX = "/at";


    // Consolidated Command List by Type
    public static final String[] SINGLE_WORD_COMMANDS = new String[]{EXIT_COMMAND, LIST_COMMAND, SAVE_COMMAND};
    public static final String[] DOUBLE_WORD_COMMANDS = new String[]{COMPLETE_COMMAND, TODO_COMMAND, DELETE_COMMAND};
    public static final String[] TRIPLE_WORD_COMMANDS = new String[]{DEADLINE_COMMAND, EVENT_COMMAND};
}
