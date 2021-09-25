import logger.Log;
import logger.Logger;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class LogTests {
    final String text1 = "abc";
    final String text2 = "";
    final String text3 = null;


    private final SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yy hh:mm a");

    protected static ByteArrayOutputStream output;
    private static PrintStream old;
    static Logger result = null;

    @BeforeAll
    static void setUpStreams() {
        old = System.out;
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        Logger l = new Logger() {
            @Override
            public void log(String text) {
                System.out.print(text);
            }
        };
        result = new Log(l);
    }

    @AfterAll
    static void cleanUpStreams() {
        System.setOut(old);
    }

    @BeforeEach
    void resetStreams() {
        output.reset();
    }

    @Test
    void LogTest1() {
        Log.getInstance().log(text1);
        Assertions.assertEquals(output.toString().contains(text1), true);
        Assertions.assertEquals(output.toString().length()
                , (formatForDateNow.format(new Date()) + "  " + text1 + '\n').length());
    }

    @Test
    void LogTest2() {
        Log.getInstance().log(text2);
        Assertions.assertEquals(output.toString().length()
                , (formatForDateNow.format(new Date()) + "  " + text2 + '\n').length());
    }

    @Test
    void LogTest3() {
        Log.getInstance().log(text3);
        Assertions.assertEquals(output.toString().length()
                , (formatForDateNow.format(new Date()) + "  " + text3 + '\n').length());
    }

    @Test
    void getInstance() {
        Assertions.assertEquals(result, Log.getInstance());
    }

}
