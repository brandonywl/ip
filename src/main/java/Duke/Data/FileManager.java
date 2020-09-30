package Duke.Data;

import Duke.TaskManager.TaskManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

public class FileManager {
    private String pathStr;
    private Path path;

    public FileManager() {

    }

    public FileManager(String ... relativePath) {
        this.pathStr = getPathStr(relativePath);
        this.path = getPath(relativePath);
    }

    public final String getPathStr(String ... relativePath) {
        String currDir = System.getProperty("user.dir");
        Path saveFolderPath;
        for(String dir : relativePath) {
            saveFolderPath = Paths.get(currDir, dir);
            currDir = saveFolderPath.toString();
        }
        return currDir;
    }

    public final Path getPath(String ... relativePath) {
        String currDir = System.getProperty("user.dir");
        Path saveFolderPath = Paths.get(currDir);
        for(String dir : relativePath) {
            saveFolderPath = Paths.get(currDir, dir);
            currDir = saveFolderPath.toString();
        }
        return saveFolderPath;
    }

    public boolean directoryExists() {
        return Files.exists(path);
    }

    public boolean directoryExists(Path path) {
        return Files.exists(path);
    }

    public boolean ensureFileExists(){
        boolean success = true;
        if (!directoryExists()) {
            File file = new File(pathStr);
            success = file.mkdirs();
        }
        return success;
    }

    public boolean ensureFileExists(Path path){
        boolean success = true;
        if (!directoryExists(path)) {
            File file = new File(pathStr);
            success = file.mkdirs();
        }
        return success;
    }

    public void writeToFile(String textLine, boolean appendToFile) throws IOException {
        FileWriter write = new FileWriter(pathStr, appendToFile);
        PrintWriter print_line = new PrintWriter(write);

        print_line.printf(textLine + "\n");
        print_line.close();
    }

    public void writeToFile(String[] textLines, boolean appendToFile) throws IOException {
        FileWriter write = new FileWriter(pathStr, appendToFile);
        PrintWriter print_line = new PrintWriter(write);

        for(String textLine : textLines) {
            print_line.printf(textLine + "\n");
        }
        print_line.close();
    }

    public String readFromFile() {
        return readFromFile(path);
    }

    public String readFromFile(Path path){
        File file;
        FileReader fr;
        BufferedReader br;
        StringBuffer sb;

        ensureFileExists(path);

        try {
            file = new File(path.toString());
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
            return "";
        }

        return sb.toString();
    }

    public void outputTasks(TaskManager taskManager) {
        directoryExists();
        String[] outputMessages = taskManager.getTasksAsStrings();
        try {
            writeToFile(outputMessages, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
