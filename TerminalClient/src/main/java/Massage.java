import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Massage implements Serializable {

    private final SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yy hh:mm a");
    private final String nick;
    private final String text;
    private final String sendToNick;
    private Date date;

    public Massage(String nick, String text) {
        this.nick = nick;
        this.date = new Date();
        this.text = text;
        this.sendToNick = "";
    }

    public Massage(String nick, String sendToNik, String text) {
        this.nick = nick;
        this.date = new Date();
        this.text = text;
        this.sendToNick = sendToNik;
    }

    public String getSendToNick() {
        return sendToNick;
    }

    public String getNick() {
        return nick;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return sendToNick.equals("") ? formatForDateNow.format(date) + "   " + nick + " :  " + text + '\n' :
               formatForDateNow.format(date) + "   " + nick + " ->  " + sendToNick + " :  " + text + '\n';
    }
}