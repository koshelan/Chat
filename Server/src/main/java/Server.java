import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {

    public static final String RESERVED_SERVER_NICK = "#SERVER#";
    public static Server server = null;

    private final Map<String, ServerConnection> clients = new HashMap<>();
    private final ServerSettings serverSettings;

    public Server(ServerSettings serverSettings) {
        this.serverSettings = serverSettings;
        this.server = this;
    }

    public void start() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Socket clientSocket = null;
        try (ServerSocket serverSocket = new ServerSocket(serverSettings.getPort())) {
            Log.getInstance().log("Сервер занял порт " + serverSettings.getPort() + " на машине " + serverSettings.getHost());

            while (true) {
                clientSocket = serverSocket.accept();
                ServerConnection serverConnection = new ServerConnection(clientSocket);
                Log.getInstance().log("Пользователь присоединился");
                executorService.execute(serverConnection);
            }
        } catch (Exception exception) {
            Log.getInstance().log(exception.getMessage());
        } finally {
            try {
                // закрываем подключение
                clientSocket.close();
                System.out.println("Сервер остановлен");
                executorService.shutdown();
            } catch (IOException ex) {
                Log.getInstance().log(ex.getMessage());
            }
        }
    }


    public void sendMessageToAllClients(Message msg) {
        MsgLog.getInstance().log(msg.toString());
        clients.forEach((k, v) -> v.sendMsg(msg));
    }

    public void sendMassageTo(Message msg) {
        MsgLog.getInstance().log(msg.toString());
        clients.get(msg.getSendToNick()).sendMsg(msg);
        clients.get(msg.getNick()).sendMsg(msg);
    }


    public void addClient(String nick, ServerConnection serverConnection) {
        clients.put(nick, serverConnection);
    }


    public void removeClient(ServerConnection client) {
        clients.remove(client.getNick());
        Log.getInstance().log(("клиент ушёл. осталось " + clients.size()));
    }


    public Set<String> getClientsNicks() {
        return clients.keySet();
    }


    public void changeClientNick(String oldNick, String newNick) {
        clients.put(newNick, clients.get(oldNick));
        clients.remove(oldNick);
    }

    public static Server getInstance() {
        return server;
    }

}