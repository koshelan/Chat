import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {

    public final String RESERVED_SERVER_NICK = "#SERVER#";

    private final Map<String, ServerConnection> clients = new HashMap<>();


    public Server(ServerSettings serverSettings) {

        Logger logger = Log.getInstance();
        ExecutorService executorService = Executors.newCachedThreadPool();//Runtime.getRuntime().availableProcessors());
        Socket clientSocket = null;
        try (ServerSocket serverSocket = new ServerSocket(serverSettings.getPort())) {
            logger.log("Сервер занял порт " + serverSettings.getPort() + " на машине " + serverSettings.getHost());
            end:
            while (true) {
                clientSocket = serverSocket.accept();
                ServerConnection serverConnection = new ServerConnection(clientSocket, this);
                logger.log("Пользователь присоединился");
                executorService.execute(serverConnection);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.log(exception.getMessage());
        } finally {
            try {
                // закрываем подключение
                clientSocket.close();
                System.out.println("Сервер остановлен");
                executorService.shutdown();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    //отправляем сообщение всем клентам
    public void sendMessageToAllClients(Massage msg) {
        MsgLog.getInstance().log(msg.toString());
        clients.forEach((k, v) -> v.sendMsg(msg));
    }

    //отправляем сообщение конкретному кленту
    public void sendMassageTo(Massage msg) {
        MsgLog.getInstance().log(msg.toString());
        clients.get(msg.getSendToNick()).sendMsg(msg);
        clients.get(msg.getNick()).sendMsg(msg);
    }

    // добавляем пользователя в чат
    public void addClient(String nick, ServerConnection serverConnection) {
        clients.put(nick, serverConnection);
    }

    // удаляем клиента из коллекции при выходе из чата
    public void removeClient(ServerConnection client) {
        clients.remove(client.getNick());
        Log.getInstance().log(("клиент ушёл. осталось " + clients.size()));
    }

    //получаем все ники клиентов
    public Set<String> getClientsNicks() {
        return clients.keySet();
    }

    // меняем ключ у соединения с клиентом
    public void changeClientNick(String oldNick, String newNick) {
        clients.put(newNick, clients.get(oldNick));
        clients.remove(oldNick);
    }
}