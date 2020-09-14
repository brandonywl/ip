import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class WriteFile {
    private String path;
    private boolean appendToFile = false;

    public WriteFile(String path) {
        this.path = path;
    }
    public WriteFile(String path, boolean append) {
        this.path = path;
        this.appendToFile = append;
    }

    public void writeToFile(String textLine) throws IOException {
        FileWriter write = new FileWriter(path, appendToFile);
        PrintWriter print_line = new PrintWriter(write);

        print_line.printf(textLine + "\n");
        print_line.close();
    }

    public void writeToFile(String[] textLines) throws IOException {
        FileWriter write = new FileWriter(path, appendToFile);
        PrintWriter print_line = new PrintWriter(write);

        for(String textLine : textLines) {
            print_line.printf(textLine + "\n");
        }
        print_line.close();
    }
}
