import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;


public class ServerConnection implements Runnable {

    private static int clients_count = 0;
    private Socket clientSocket;
    private ObjectOutputStream outMessage;
    private ObjectInputStream inMessage;
    //private Server server;
    private String nick;

    public ServerConnection(Socket socket) {

        try {
            clients_count++;
            //this.server = server;
            this.clientSocket = socket;
            this.outMessage = new ObjectOutputStream(socket.getOutputStream());
            this.inMessage = new ObjectInputStream(socket.getInputStream());
            Log.getInstance().log("создано соедниение с клиентом");
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }


    @Override
    public void run() {
        try {
            Log.getInstance().log("запущен поток с клиентом");
            nick = getUniqueNick();
            Server.getInstance().addClient(nick, this);
            //while (true) {
                // сервер отправляет сообщение
                Server.getInstance().sendMessageToAllClients(
                        new Message(Server.RESERVED_SERVER_NICK, "Новый участник <" + nick + "> вошёл в чат!"));
                Server.getInstance().sendMessageToAllClients(
                        new Message(Server.RESERVED_SERVER_NICK, "Клиентов в чате = " + clients_count));
            //    break;
            //}

            while (true) {
                // Если от клиента пришло сообщение
                Message clientMessage = (Message) inMessage.readObject();

                if (clientMessage.getText().equalsIgnoreCase("##session##end##")) {
                    System.out.println(clientMessage);
                    break;
                }
                if (!clientMessage.getNick().equals(nick)) {
                    changeNick(clientMessage.getNick());
                } else {

                    clientMessage.setDate(new Date());
                    if (("").equals(clientMessage.getSendToNick())) {
                        Server.getInstance().sendMessageToAllClients(clientMessage);
                    } else {
                        sendMessageToClient(clientMessage);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            this.closeConnection();
        }
    }

    public void sendMsg(Message msg) {
        try {
            outMessage.writeObject(msg);
            outMessage.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.getInstance().log(ex.getMessage());
        }
    }

    public String getNick() {
        return nick;
    }

    private void sendMessageToClient(Message clientMessage) {
        if (Server.getInstance().getClientsNicks().contains(clientMessage.getSendToNick())) {
            Server.getInstance().sendMassageTo(clientMessage);
        } else {
            sendMsg(new Message(Server.RESERVED_SERVER_NICK
                    , "получатель с ником <" + clientMessage.getSendToNick() + "> не найден"));
        }
    }

    private void changeNick(String clientNick) throws IOException, ClassNotFoundException {
        if (Server.getInstance().getClientsNicks().contains(clientNick) || clientNick.equals(Server.RESERVED_SERVER_NICK) ||
                clientNick.isEmpty()) {
            sendMsg(new Message(
                    Server.RESERVED_SERVER_NICK,
                    "Ваш новый <НИК> " + clientNick + " уже зарегистрирован в чате"));
            changedNickSuccessfully(getUniqueNick());
        } else {
            changedNickSuccessfully(clientNick);
        }
    }

    private void changedNickSuccessfully(String clientNick) {
        Server.getInstance().sendMessageToAllClients(
                new Message(
                        Server.RESERVED_SERVER_NICK,
                        "участник " + nick + " переименовался и тепер " + clientNick));
        Server.getInstance().changeClientNick(nick, clientNick);
        nick = clientNick;
    }

    private String getUniqueNick() throws IOException, ClassNotFoundException {
        sendMsg(new Message(Server.RESERVED_SERVER_NICK, "Введите ваш уникальный <НИК>"));
        while (true) {
            String clientNick = ((Message) inMessage.readObject()).getNick();
            if (Server.getInstance().getClientsNicks().contains(clientNick) || clientNick.equals(Server.RESERVED_SERVER_NICK)) {
                sendMsg(
                        new Message(
                                Server.RESERVED_SERVER_NICK,
                                "Такой <НИК> уже зарегистрирован в чате"
                        )
                );
            } else {
                return clientNick;
            }
        }
    }

    private void closeConnection() {
        Server.getInstance().removeClient(this);
        clients_count--;
        if (nick != null) {
            Server.getInstance().sendMessageToAllClients(
                    new Message(Server.RESERVED_SERVER_NICK, "Клиент " + nick + " Покинул чат "));
            Server.getInstance().sendMessageToAllClients(
                    new Message(Server.RESERVED_SERVER_NICK, "Клиентов в чате = " + clients_count));
        }
    }

}
