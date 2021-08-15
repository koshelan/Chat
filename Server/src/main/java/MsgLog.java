import java.text.SimpleDateFormat;
import java.util.Date;

public class MsgLog implements Logger{
    private static Logger logger = null;
    private static Logger textLogger= null;

    public MsgLog(Logger logger) {
        this.textLogger = logger;
        this.logger = this;
    }

    @Override
    public void log(String text) {
        textLogger.log(text);
    }

    public static Logger getInstance() {
        return logger;
    }
}
