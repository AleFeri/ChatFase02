import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientRecive extends Thread {
    Client client;
    String stringFromServer;
    BufferedReader inFromServer;

    //Costruttore
    public ClientRecive(Client client) {
        this.client = client;
    }

    //metodi
    public void run() {
        try {
            inFromServer = new BufferedReader(new InputStreamReader(client.getLocalSocket().getInputStream()));
        } catch (IOException ex) {
            System.out.println("Error: IOInstrauration");
        }

        for(;;) {
            try {
                stringFromServer = inFromServer.readLine();
                client.print(stringFromServer);
                close();
            } catch (IOException ex) {
                System.out.println("Error: Ricezione del messaggio");
            }
        }
    }
    private void close() {
        if(stringFromServer.equals("Server| Exit successful!"))
            client.kill();
    }
    public void kill() {
        this.interrupt();
    }
}
