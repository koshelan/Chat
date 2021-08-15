import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerConnection implements Runnable{

    private Socket clientSocket;
    private PrintWriter outMessage;
    private Scanner inMessage;
    private Server server;

    private static int clients_count = 0;

    public ServerConnection(Socket socket, Server server) {

        try {
            clients_count++;
            this.server = server;
            this.clientSocket = socket;
            this.outMessage = new PrintWriter(socket.getOutputStream());
            this.inMessage = new Scanner(socket.getInputStream());
            Log.getInstance().log("создано соедниение с клиентом");
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }


    @Override
    public void run() {
        try {

            while (true) {
                // сервер отправляет сообщение
                Log.getInstance().log("запущен поток с клиентом");
                server.sendMessageToAllClients("Новый участник вошёл в чат!");
                server.sendMessageToAllClients("Клиентов в чате = " + clients_count);
//                server.sendMessageToAllClients(new Massage("Server","Новый участник вошёл в чат!").toString());
//                server.sendMessageToAllClients(new Massage("Server",("Клиентов в чате = " + clients_count).toString()).toString());
                break;
            }

            while (true) {
                // Если от клиента пришло сообщение
                if (inMessage.hasNext()) {
                    String clientMessage = inMessage.nextLine();
                    // если клиент отправляет данное сообщение, то цикл прерывается и 
                    // клиент выходит из чата

                    if (clientMessage.equalsIgnoreCase("##session##end##")) {
                        break;
                    }
                    // выводим в консоль сообщение (для теста)
                    System.out.println(clientMessage);
                    // отправляем данное сообщение всем клиентам
                    server.sendMessageToAllClients(clientMessage);
                }
                // останавливаем выполнение потока на 100 мс
                Thread.sleep(100);
            }
        }
        catch (InterruptedException ex) {
            Log.getInstance().log(ex.getMessage());
            ex.printStackTrace();
        }
        finally {
            this.close();
        } 
    }

    // отправляем сообщение
    public void sendMsg(String msg) {
        try {
            outMessage.println(msg);
            outMessage.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void close() {
        // удаляем клиента из списка
        server.removeClient(this);
        clients_count--;
        server.sendMessageToAllClients("Клиентов в чате = " + clients_count);
    }
}
