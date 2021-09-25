package logger;

public class MsgLog implements Logger {
    private static volatile Logger logger = null;
    private static Logger textLogger = null;

    public MsgLog(Logger logger) {
        textLogger = logger;
        MsgLog.logger = this;
    }

    public static Logger getInstance() {
        return logger;
    }

    @Override
    public void log(String text) {
        textLogger.log(text);
    }
}

