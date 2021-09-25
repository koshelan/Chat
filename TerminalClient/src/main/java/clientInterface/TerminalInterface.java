package clientInterface;


import javax.swing.*;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import model.ServerSettings;
import service.Client;


public class TerminalInterface extends JFrame implements VisualInterface {

    private static final int PRINT_INPUT_MGS_EVERY = 7;
    Client client;
    boolean nickIsCorrect = false;
    private Queue<String> queue = new ConcurrentLinkedQueue<>();
    private boolean doWhile = true;

    public TerminalInterface(ServerSettings serverSettings) {
        this.client = new Client(serverSettings, this);
        while (doWhile) {
            try {
                Thread.sleep(100);
                out();
                FutureTask<String> userText = new FutureTask<>(new ConsoleInputReadTask());
                Thread thread = new Thread(userText);
                thread.setDaemon(true);
                thread.start();
                String m;
                try {
                    m = userText.get(PRINT_INPUT_MGS_EVERY, TimeUnit.SECONDS);
                } finally {
                    thread.interrupt();
                }
                if (m.isEmpty()) {
                    throw new NullPointerException("строка пуста");
                }
                send(m);
                out();
            } catch (Exception e) {
            }

        }
    }

    @Override
    public void printUsersQuantity(String text) {
        queue.add(text);
        queue.add("\n");
    }

    @Override
    public void printText(String text) {
        queue.add(text);
    }

    @Override
    public void closeInterface() {
        System.out.println("close");
        doWhile = false;
        System.exit(0);
    }

    @Override
    public void correctNick() {
        out();
        nickIsCorrect = false;
    }

    private void send(String text) throws IOException {
        if (nickIsCorrect) {
            client.send(text);
        } else {
            nickIsCorrect = true;
            client.setName(text);
            client.send("");
        }
    }

    private void out() {
        while (!queue.isEmpty()) {
            System.out.print(queue.poll());
        }
    }

}
