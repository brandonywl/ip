package Duke.Data;

import Duke.Constants.Messages.Errors;
import Duke.TaskManager.TaskManager;
import Duke.UI.Printer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 *  Handles file input and output for Duke.
 */
public class FileManager {
    private String pathStr;
    private Path path;

    public FileManager() {

    }

    public FileManager(String ... relativePath) {
        this.pathStr = getPathStr(relativePath);
        this.path = getPath(relativePath);
    }

    /**
     * Gets a String representation of the path relative to the current directory
     * @param relativePath Desired relative directory
     * @return String representation of the relative directory.
     */
    private String getPathStr(String ... relativePath) {
        String currDir = System.getProperty("user.dir");
        Path saveFolderPath;
        for(String dir : relativePath) {
            saveFolderPath = Paths.get(currDir, dir);
            currDir = saveFolderPath.toString();
        }
        return currDir;
    }

    /**
     * Gets the full path of the directory relative to the current directory.
     * @param relativePath Desired relative directory.
     * @return Full Path of the relative directory.
     */
    public final Path getPath(String ... relativePath) {
        String currDir = System.getProperty("user.dir");
        Path saveFolderPath = Paths.get(currDir);
        for(String dir : relativePath) {
            saveFolderPath = Paths.get(currDir, dir);
            currDir = saveFolderPath.toString();
        }
        return saveFolderPath;
    }

    /**
     * Check if the stored directory exists.
     * @return Whether the directory exists.
     */
    public boolean directoryExists() {
        return Files.exists(path);
    }

    /**
     * Check if a specified directory exists.
     * @param path Full path of the directory to be checked.
     * @return Whether the directory exists.
     */
    public boolean directoryExists(Path path) {
        return Files.exists(path);
    }

    /**
     * Generates a file at the stored location if it does not exist.
     * @return Whether the file currently exists.
     */
    public boolean ensureFileExists(){
        boolean success = true;
        if (!directoryExists()) {
            File file = new File(pathStr);
            success = file.mkdirs();
        }
        return success;
    }

    /**
     * Generates a file at a specified location if it does not exist.
     * @return Whether the file currently exists.
     */
    public boolean ensureFileExists(Path path){
        boolean success = true;
        if (!directoryExists(path)) {
            File file = new File(pathStr);
            success = file.mkdirs();
        }
        return success;
    }

    /**
     * Writes to a file specified be it appended or overwritten.
     * @param textLines Lines to be written to the file.
     * @param appendToFile Whether to append or overwrite.
     * @throws IOException File doesn't exist.
     */
    public void writeToFile(ArrayList<String> textLines, boolean appendToFile) throws IOException {
        FileWriter write = new FileWriter(pathStr, appendToFile);
        PrintWriter print_line = new PrintWriter(write);
        for (String textLine : textLines) {
            print_line.printf(textLine + "\n");
        }
        print_line.close();
    }

    /**
     * Provide a default method for loading the data from the path attribute of the object.
     * @return Data from the file in string format.
     */
    public String readFromFile() {
        return readFromFile(path);
    }

    /**
     * Loads the data from a text file and returns it.
     * @param path Specified full path of the file to read from
     * @return Data from the file in string format.
     */
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

    /**
     * Dumps the current list of tasks into a txt file.
     * @param taskManager Where the tasks are stored.
     */
    public void outputTasks(TaskManager taskManager) {
        if (!ensureFileExists()) {
            Printer.printError(Errors.DUMP_LOCATION_ERROR);
            return;
        }
        ArrayList<String> outputMessages = taskManager.getTasksAsStrings();
        try {
            writeToFile(outputMessages, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
