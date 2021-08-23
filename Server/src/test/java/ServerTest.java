import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;

class ServerTest {

    @Test
    void sendMessageToAllClients() {
        Server server = Mockito.mock(Server.class);

    }

    @Test
    void sendMassageTo() {
    }

    @Test
    void addClient() {
    }

    @Test
    void removeClient() {
        Set<String> expectedSet = new HashSet<>();
        Server server = Mockito.mock(Server.class);
        Set clients = Mockito.mock(HashSet.class);
        when(clients.)
    }

    @Test
    void getClientsNicks() {
        Set<String> expectedSet = new HashSet<>();

        Server server = Mockito.mock(Server.class);
        when(server.getClientsNicks("172.123.12.19"))
               .thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
    }

    @Test
    void changeClientNick() {
    }
}