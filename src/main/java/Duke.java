import java.util.Scanner;


public class Duke {
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

        while (!currentUserInput.equalsIgnoreCase("bye")){
            currentUserInput = sc.nextLine();
            switch (currentUserInput.toUpperCase().split(" ")[0]){
                case "BYE":
                    break;
                case "LIST":
                    printTasks(taskManager);
                    break;
                default:
                    taskManager.addTask(currentUserInput);
                    printMessage("added: " + currentUserInput);
            }
        }

        printExitMessage();

    }

    public static void printDivider(){
        String s = "------------------------------------------------";
        System.out.println(s);
    }

    public static void printIntroduction(String logo){
        printDivider();
        System.out.println("Hello I'm \n" + logo);
        System.out.println("What can I do for you?");
        printDivider();
    }

    public static void printExitMessage(){
        printMessage("Bye. Hope to see you again soon!");
    }

    public static void printMessage(String s){
        printDivider();
        System.out.println(s);
        printDivider();
    }

    public static void printTasks(TaskManager taskManager){
        String[] tasks = taskManager.getTasks();
        int numOfTasks = taskManager.getNumberOfTasks();

        printDivider();
        if (numOfTasks == 0){
            System.out.println();
        }
        for (int i = 0; i < numOfTasks; i++){
            String outputMessage = String.format("%d. %s", i+1, tasks[i]);
            System.out.println(outputMessage);
        }
        printDivider();
    }
}
