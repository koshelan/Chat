import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MassageTests {
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
    void testMassageToAll(){
        Massage massage = new Massage(nick,text);
        Assertions.assertEquals(nick,massage.getNick());
        Assertions.assertEquals(text,massage.getText());
        Assertions.assertEquals("",massage.getSendToNick());
        Assertions.assertNotNull(massage.getDate());
        massage.setDate(date);
        Assertions.assertEquals(date,massage.getDate());
        Assertions.assertEquals(
                (formatForDateNow.format(date) + "   " + nick + " :  " + text + '\n')
                ,massage.toString());
    }

    @Test
    void testMassageToOneUser(){
        Massage massage = new Massage(nick,sendToNick,text);
        Assertions.assertEquals(nick,massage.getNick());
        Assertions.assertEquals(text,massage.getText());
        Assertions.assertEquals(sendToNick,massage.getSendToNick());
        Assertions.assertNotNull(massage.getDate());
        massage.setDate(date);
        Assertions.assertEquals(date,massage.getDate());
        Assertions.assertEquals(
                (formatForDateNow.format(date) + "   " + nick + " ->  " + sendToNick + " :  " + text + '\n')
                ,massage.toString());
    }
    @Test
    void testMassageWithNullFields(){
        Massage massage = new Massage(null,null,null);
        Assertions.assertNull(massage.getNick());
        Assertions.assertNull(massage.getText());
        Assertions.assertNull(massage.getSendToNick());
        Assertions.assertNotNull(massage.getDate());
        Assertions.assertEquals(
                (formatForDateNow.format(date) + "   " + null + " ->  " + null + " :  " + null + '\n')
                ,massage.toString());
    }

}
