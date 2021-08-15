
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    List<ServerConnection> clients = new ArrayList<>();

    public Server(ServerSettings serverSettings) {
        Logger logger = Log.getInstance();

        Socket clientSocket = null;
        try (ServerSocket serverSocket = new ServerSocket(serverSettings.getPort())) {
            logger.log("Сервер занял порт " + serverSettings.getPort() + " на машине " + serverSettings.getHost());
            end:
            while (true) {
                    clientSocket = serverSocket.accept();
                    ServerConnection serverConnection = new ServerConnection(clientSocket, this);
                    clients.add(serverConnection);
                    logger.log("Пользователь присоединился");
                    new Thread(serverConnection).start();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            logger.log(exception.getMessage());
        }
        finally {
            try {
                // закрываем подключение
                clientSocket.close();
                System.out.println("Сервер остановлен");
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void sendMessageToAllClients(String msg) {
        for (ServerConnection o : clients) {
            System.out.println(msg);
            o.sendMsg(msg);
        }

    }

    // удаляем клиента из коллекции при выходе из чата
    public void removeClient(ServerConnection client) {
        clients.remove(client);
        Log.getInstance().log(("клиент ушёл. осталось "+clients.size()).toString());
    }


//                    final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
//                    System.out.println("Создалось соединение");
//                    while (socketChannel.isConnected()) {
//                        socketChannel.write(ByteBuffer.wrap(new.getBytes(StandardCharsets.UTF_8)));
//                        int byteCount = socketChannel.read(inputBuffer);
//                        if (byteCount == -1) {
//                            break;
//                        }
//                        inputBuffer.flip();
//                        Massage massage = (Massage) new ObjectInputStream(
//                                new ByteArrayInputStream(inputBuffer.array(), 0, inputBuffer.limit()))
//                                .readObject();
//                        System.out.println(massage);
//                        logger.log(massage.toString());
//
//                        final String text = new String(inputBuffer.array(), 0, byteCount, StandardCharsets.UTF_8);
//                        inputBuffer.clear();
//                        System.out.println("ввод: " + text);
//                        final String result = text.replace(" ", "");
//                        System.out.println("вывод: " + result);
//                        socketChannel.write(ByteBuffer.wrap(result.getBytes(StandardCharsets.UTF_8)));
//                        if (text.equals("off")) {
//                            break end;
//                        }
//
//                    }
//
//                } catch (Exception e) {
//                    System.out.println("Клиент отвалился");
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}