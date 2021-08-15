import java.io.BufferedWriter;
import java.io.FileWriter;


public class FileLogger implements Logger{

private static Logger logger=null;
private final static String fileName = "server_logs.log";

    private FileLogger() {
    }

    @Override
    public void log(String text) {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(fileName,true))) {
            br.append('\n');
            br.append(text);
        }  catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    public static Logger getInstance() {
        if (logger==null){
            logger = new FileLogger();
        }
        return logger;
    }



}
