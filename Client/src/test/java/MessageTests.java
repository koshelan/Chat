import model.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.text.SimpleDateFormat;
import java.util.Date;
public class MessageTests {
    private static String nick = "nick";
    private static String text = "text";
    private static String sendToNick = "nick2";
    private static Date date;
    private final SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yy hh:mm a");

    @BeforeAll
    static void firstSettings() {
        date = new Date();
    }

    @Test
    void testMessageToAll() {
        Message message = new Message(nick, text);
        Assertions.assertEquals(nick, message.getNick());
        Assertions.assertEquals(text, message.getText());
        Assertions.assertEquals("", message.getSendToNick());
        Assertions.assertNotNull(message.getDate());
        message.setDate(date);
        Assertions.assertEquals(date, message.getDate());
        Assertions.assertEquals(
                (formatForDateNow.format(date) + "   " + nick + " :  " + text + '\n')
                , message.toString());
    }

    @Test
    void testMessageToOneUser() {
        Message message = new Message(nick, sendToNick, text);
        Assertions.assertEquals(nick, message.getNick());
        Assertions.assertEquals(text, message.getText());
        Assertions.assertEquals(sendToNick, message.getSendToNick());
        Assertions.assertNotNull(message.getDate());
        message.setDate(date);
        Assertions.assertEquals(date, message.getDate());
        Assertions.assertEquals(
                (formatForDateNow.format(date) + "   " + nick + " ->  " + sendToNick + " :  " + text + '\n')
                , message.toString());
    }

    @Test
    void testMessageWithNullFields() {
        Message message = new Message(null, null, null);
        Assertions.assertNull(message.getNick());
        Assertions.assertNull(message.getText());
        Assertions.assertNull(message.getSendToNick());
        Assertions.assertNotNull(message.getDate());
        Assertions.assertEquals(
                (formatForDateNow.format(date) + "   " + null + " ->  " + null + " :  " + null + '\n')
                , message.toString());
    }

}
