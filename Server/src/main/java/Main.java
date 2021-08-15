import java.text.SimpleDateFormat;
import java.util.Date;


public class Main {
    final static String SERVER_SETTINGS_FILE = "server_settings.csv";
    final static SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yy hh:mm a");


    public static void main(String[] args) {
        Logger logger = FileLogger.getInstance();
        logger.log("Программа запущена : "+ formatForDateNow.format(new Date()));
        String[] columnMapping = {"host", "port"};
        SeverSettings severSettings = ParceCSV.parceCSV(columnMapping,SERVER_SETTINGS_FILE);

    }

}
