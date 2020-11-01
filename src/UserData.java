import java.net.Socket;

public class UserData {
    private int id;
    private String username;
    private Socket userSocket;
    private ServerThread serverThread;

    //get
    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public Socket getUserSocket() {
        return userSocket;
    }
    public ServerThread getServerThread() {
        return serverThread;
    }

    //set
    public void setUsername(String username) {
        this.username = username;
    }
    public void setUserSocket(Socket userSocket) {
        this.userSocket = userSocket;
    }
    public void setServerThread(ServerThread serverThread) {
        this.serverThread = serverThread;
    }

    //Costruttore
    public UserData(int id, String username, Socket userSocket, ServerThread serverThread) {
        this.id = id;
        this.username = username;
        this.userSocket = userSocket;
        this.serverThread = serverThread;
    }
}
