import java.text.SimpleDateFormat;
import java.util.Date;

public class Massage {
    private final SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yy hh:mm a");
    private final String nik;
    private final Date date;
    private final String text;

    public Massage(String nik, String text) {
        this.nik = nik;
        this.date = new Date();
        this.text = text;
    }

    public String getNik() {
        return nik;
    }

    public Date getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return nik + "   " + formatForDateNow.format(date) +'\n'+
                text;
    }
}
