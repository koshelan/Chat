public class Main {

    final static String SERVER_SETTINGS_FILE = "server_settings.csv";
    final static String SERVER_LOGS_FILE_NAME = "server_logs.log";
    final static String SERVER_MSG_LOGS_FILE_NAME = "file.log";

    public static void main(String[] args) {
        Logger logger = new Log(new FileLogger(SERVER_LOGS_FILE_NAME)).getInstance();
        new MsgLog(new FileLogger(SERVER_MSG_LOGS_FILE_NAME));
        logger.log("Программа запущена : ");
        ServerSettings severSettings = ParseCSV.parseCSV(SERVER_SETTINGS_FILE);
        logger.log("Прочитаны настройки сервера: " + severSettings.toString());
        new Server(severSettings).start();
        logger.log("FINISH");

    }

}
