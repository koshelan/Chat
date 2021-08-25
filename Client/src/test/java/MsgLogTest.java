import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

class MsgLogTest {
    final String text1 = "abc";
    final String text2 = "";
    final String text3 = null;
    static Logger result = null;

    protected static ByteArrayOutputStream output;
    private static PrintStream old;


    @BeforeAll
    static void setUpStreams() {
        old = System.out;
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        Logger l = new Logger() {
            @Override
            public void log(String text) {
                System.out.print(text+" ");
            }
        };
        result = new MsgLog(l);
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
        MsgLog.getInstance().log(text1);
        String result = output.toString();
        Assertions.assertEquals(text1+" ", output.toString());
    }

    @Test
    void LogTest2() {
        MsgLog.getInstance().log(text2);
        Assertions.assertEquals(text2+ " ", output.toString());

    }

    @Test
    void LogTest3() {
        MsgLog.getInstance().log(text3);
        Assertions.assertEquals((text3+" "), output.toString());
    }

    @Test
    void getInstance() {
        Assertions.assertEquals(result, MsgLog.getInstance());
    }
}