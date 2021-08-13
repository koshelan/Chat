import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.Instant;

public class FileLogger implements Logger{
private static Logger logger=null;

    private FileLogger() {
        String fileName = "server_logs.log";
        try (BufferedWriter br = new BufferedWriter(new FileWriter(fileName,true))) {
            String s;
            while ((s = br.readLine()) != null) {
                result += s;
            }
        }  catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void log(String text) {

    }

    @Override
    public Logger getInstance() {
        if (logger==null){
            logger = new FileLogger();
        }
        return logger;
    }

}
