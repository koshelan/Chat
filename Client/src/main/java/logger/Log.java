package logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log implements Logger {

    private static volatile Logger logger = null;
    private static Logger textLogger = null;
    private SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yy hh:mm a");

    public Log(Logger logger) {
        this.textLogger = logger;
        this.logger = this;
    }

    public static Logger getInstance() {
        return logger;
    }

    @Override
    public void log(String text) {
        textLogger.log(formatForDateNow.format(new Date()) + "  " + text+'\n');
    }
}
