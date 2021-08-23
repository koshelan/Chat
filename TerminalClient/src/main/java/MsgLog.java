public class MsgLog implements Logger {

    private static Logger logger = null;
    private static Logger textLogger = null;

    public MsgLog(Logger logger) {
        this.textLogger = logger;
        this.logger = this;
    }

    public static Logger getInstance() {
        return logger;
    }

    @Override
    public void log(String text) {
        textLogger.log(text);
    }
}
