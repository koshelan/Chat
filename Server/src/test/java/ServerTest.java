import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ServerTest {
final String nick = "nick";
final String otherNick = "otherNick";
final String nickChanged = "Nick!";
private static Server server=null;

    @BeforeEach
    void setUp() {
        server = new Server(new ServerSettings("",0));
    }


    @Test
    void sendMessageToAllClients() {
    }

    @Test
    void sendMassageTo() {
    }

    @Test
    void addClient() {
        ServerConnection client = Mockito.mock(ServerConnection.class);
        when(client.getNick()).thenReturn(nick);
        server.addClient(nick, client);
        Assertions.assertEquals(1,server.getClientsNicks().size());
        server.addClient(otherNick, client);
        Assertions.assertEquals(true,server.getClientsNicks().contains(nick));
        Assertions.assertEquals(true,server.getClientsNicks().contains(otherNick));
        Assertions.assertEquals(2,server.getClientsNicks().size());
    }

    @Test
    void removeClient() {
        ServerConnection client = Mockito.mock(ServerConnection.class);
        when(client.getNick()).thenReturn(nick);
        server.addClient(nick, client);
        server.addClient(otherNick, client);
        Assertions.assertEquals(true,server.getClientsNicks().contains(nick));
        Assertions.assertEquals(true,server.getClientsNicks().contains(otherNick));
        Assertions.assertEquals(2,server.getClientsNicks().size());
        server.removeClient(client);
        Assertions.assertEquals(false,server.getClientsNicks().contains(nick));
        Assertions.assertEquals(true,server.getClientsNicks().contains(otherNick));
        Assertions.assertEquals(1,server.getClientsNicks().size());

    }

    @Test
    void getClientsNicks() {
        ServerConnection client = Mockito.mock(ServerConnection.class);
        when(client.getNick()).thenReturn(nick);
        server.addClient(nick, client);
        server.addClient(otherNick, client);
        Assertions.assertEquals(true,server.getClientsNicks().contains(nick));
        Assertions.assertEquals(true,server.getClientsNicks().contains(otherNick));
        Assertions.assertEquals(2,server.getClientsNicks().size());

    }

    @Test
    void changeClientNick() {
        ServerConnection client = Mockito.mock(ServerConnection.class);
        when(client.getNick()).thenReturn(nick);
        server.addClient(nick, client);
        server.addClient(otherNick, client);
        server.changeClientNick(nick,nickChanged);
        Assertions.assertEquals(false,server.getClientsNicks().contains(nick));
        Assertions.assertEquals(true,server.getClientsNicks().contains(otherNick));
        Assertions.assertEquals(true,server.getClientsNicks().contains(nickChanged));
    }
}