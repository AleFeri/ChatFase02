import java.io.DataOutputStream;
import java.io.IOException;

public class ClientSend extends Thread {
    Client client;
    String userString;
    DataOutputStream outToServer;

    //Costruttore
    public ClientSend(Client client) {
        this.client = client;
    }

    //metodi
    public void run() {
        try {
            outToServer = new DataOutputStream(client.getLocalSocket().getOutputStream());
        } catch (IOException ex) {
            System.out.println("Error: IOInstrauration");
        }

        for(;;) {
            try {
                userString = client.read();
                outToServer.writeBytes(userString + '\n');
            } catch (IOException ex) {
                System.out.println("Error: Read user message");
            }
        }
    }
    public void kill() {
        this.interrupt();
    }
}
