import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Client extends JFrame {

    final static String RESERVED_SERVER_NICK = "#SERVER#";
    final static String SIGN_INCORRECT_NICK = "<НИК>";
    final static String CLIENTS_IN_CHAT = "Клиентов в чате = ";

    // следующие поля отвечают за элементы формы
    //    private final JTextField jtfMessage;
    //    private final JTextField jtfName;
    //    private final JTextArea jtaTextAreaMessage;
    // клиентский сокет
    private Socket clientSocket;
    // входящее сообщение
    private ObjectInputStream inMessage;
    // исходящее сообщение
    private ObjectOutputStream outMessage;
    // имя клиента
    private String clientName = "";
    private VisualInterface form;


    public Client(ServerSettings serverSettings,VisualInterface visualInterface) {

        try {
            // подключаемся к серверу
            clientSocket = new Socket(serverSettings.getHost(), serverSettings.getPort());
            inMessage = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
            outMessage = new ObjectOutputStream(clientSocket.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
        form = visualInterface;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // бесконечный цикл
                    while (true) {
                        // считываем сообщение;
                        Massage inMes = (Massage) inMessage.readObject();
                        if (inMes.getNick().equals(RESERVED_SERVER_NICK)) {
                            if (inMes.getText().indexOf(CLIENTS_IN_CHAT) == 0) {
                                form.printUsersQuantity(inMes.getText());
                                continue;
                            }
                            if (inMes.getText().contains(SIGN_INCORRECT_NICK)) {
                                form.printText(inMes.toString());
                                form.correctNick();
                                continue;
                            }
                        }
                        MsgLog.getInstance().log(inMes.toString());
                        // выводим сообщение
                        form.printText(inMes.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.getInstance().log(e.getMessage());
                }

            }
        }).start();
    }

    public void send(String text) throws IOException {
        // формируем сообщение для отправки на сервер
        if (text.equals("/exit")) {
            exitActions();
            form.closeInterface();
        } else {
            int begin;
            int end;
            Massage message;
            System.out.println(text.indexOf("<"));
            if ((begin = text.indexOf("<")) == 0 && (end = text.indexOf(">")) > 0) {
                String sendToNick = text.substring(begin + 1, end);
                String msgText = text.substring(end + 1);
                message = new Massage(clientName, sendToNick, msgText);
            } else {
                message = new Massage(clientName, text);
            }
            // отправляем сообщение
            sendMSG(message);
        }
    }

    public void setName(String text) {
        clientName = text;
    }

    public void exitActions() {
        try {
            // отправляем служебное сообщение, которое является признаком того, что клиент вышел из чата
            outMessage.writeObject(new Massage(clientName, "##session##end##"));
            outMessage.flush();
            outMessage.close();
            inMessage.close();
            clientSocket.close();
        } catch (IOException exc) {
            Log.getInstance().log(exc.getMessage());

        }
    }

    private void sendMSG(Massage message) throws IOException {
        MsgLog.getInstance().log(message.toString());
        outMessage.writeObject(message);
        outMessage.flush();
    }
}
