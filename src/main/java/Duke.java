import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";

        printIntroduction(logo);
/*        Scanner sc = new Scanner(System.In);
        String currentUserInput = "";

        while (!currentUserInput.equalsIgnoreCase("bye")){
            currentUserInput = sc.nextLine();
            switch (currentUserInput){

            }
        }*/

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
}
