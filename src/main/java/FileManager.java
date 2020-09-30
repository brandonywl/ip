import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

public class FileManager {

    public static String checkDumpMade() {
        String home = System.getProperty("user.dir");
        Path saveFolderPath = Paths.get(home, "data");
        String saveFolder = saveFolderPath.toString();
        Path dumpFilePath = Paths.get(saveFolder, "dump.txt");
        String dumpFile = dumpFilePath.toString();

        if (!Files.exists(dumpFilePath)) {
            File file = new File(saveFolder);
            boolean success = file.mkdirs();
            String message = success ? "Made directories" : "Failed to make directories";
            System.out.println(message);
        }
        return dumpFile;
    }

    public static String[] importTask() {
        String dumpFile = checkDumpMade();
        File file;
        FileReader fr;
        BufferedReader br;
        StringBuffer sb;
        try {
            file = new File(dumpFile);
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            sb = new StringBuffer();
            String nextLine;

            while ((nextLine = br.readLine()) != null) {
                sb.append(nextLine);
                sb.append("\n");
            }
            fr.close();

        } catch (IOException e) {
            e.printStackTrace();
            return new String[] {};
        }

        return sb.toString().split("\n");
    }

    public static void outputTasks(TaskManager tManager) {
        String dumpFile = checkDumpMade();

        String[] outputMessages = tManager.getTasksAsStrings();

        WriteFile writer = new WriteFile(dumpFile);
        try {
            writer.writeToFile(outputMessages);
            System.out.println("Dump successful");
        } catch (IOException e) {
            System.out.println("Failed to dump file");
        }
    }
}
