import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    String serverName = "[Server Name]";
    int serverPort = -1;
    String stringFromUser;
    BufferedReader inFromClient;
    DataOutputStream outToClient1;
    DataOutputStream outToClient2;

    //Costruttore
    public Server(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
    }

    //Metodi
    public void power_up() {
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);

            for(int i = 0; true; i++) {
                System.out.println("Attesa client n" + i);
                Socket client = serverSocket.accept();
                System.out.println("Socket| " + client + " accepted");

                UserData clientData = new UserData(i, "", client, null);
                clientData.setServerThread(new ServerThread(clientData, this));

                clientData.getServerThread().start();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    public void print(String str) {
        System.out.println(str);
    }

    //Main
    public static void main(String[] args) {
        Server server = new Server("127.0.0.1", 9876);
        server.power_up();
    }
}

