import java.io.BufferedWriter;
import java.io.FileWriter;


public class FileLogger implements Logger {

    final String fileName;

    public FileLogger(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void log(String text) {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(fileName, true))) {
            br.append(text);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
