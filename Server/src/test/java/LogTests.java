import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Date;

public class LogTests {
    String text1="abc";
    String text2="";
    String text3=null;

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
                System.out.print(text);
            }
        };
        new Log(l);
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
    void LogTest1(){
        Log.getInstance().log(text1);
        Assertions.assertEquals(output.toString().contains(text1),true);
        Assertions.assertEquals(output.toString().length(),(19+text1.length()));
    }
    @Test
    void LogTest2(){
        Log.getInstance().log(text2);
        Assertions.assertEquals(output.toString().length(),(19+text2.toString().length()));
    }
    @Test
    void LogTest3(){
        Log.getInstance().log(text3);
        Assertions.assertEquals(output.toString().length(),(19+4));
    }

}
