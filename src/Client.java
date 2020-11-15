import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
    private String clientName = "#";
    private String serverName = "[Nome Server]";
    private int serverPort = -1;
    private Socket localSocket;
    private BufferedReader keyboard;
    private ClientRecive threadRecive;
    private UserGUI usrGUI;
    DataOutputStream outToServer;

    //Getter
    public String getClientName() {
        return clientName;
    }
    public Socket getLocalSocket() {
        return localSocket;
    }

    //Costruttore
    public Client(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
        keyboard = new BufferedReader(new InputStreamReader(System.in));
    }

    //Corpo
    public void connetti() {
        try {
            localSocket = new Socket(serverName, serverPort);

            outToServer = new DataOutputStream(this.localSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(this.localSocket.getInputStream()));

            boolean registered = false;
            do {
                registered = register(localSocket, outToServer, inFromServer);
            } while (!registered);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    //Metodi
    public void print(String string) {
        System.out.println(string);
        usrGUI.append(string);
    }
    public void send(String msg) {
        try {
            outToServer.writeBytes(msg + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String read() throws IOException {
        return keyboard.readLine();
    }
    public boolean validUserName(String userName) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._-]{3,20}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(userName);
        return matcher.find();
    }
    private boolean register(Socket socket, DataOutputStream ots, BufferedReader ifs) {
        String str;

        try {
            do {
                str = ifs.readLine();

                if (str.equals("Server| Insert Username")) {
                    System.out.println(str);

                    do {
                        clientName = JOptionPane.showInputDialog("Insert Username");

                        if(!validUserName(clientName)) {
                            System.out.println("System| Invalid username. Please retry");
                            JOptionPane.showMessageDialog(new JFrame(),
                                    "Wrong Username syntax.",
                                    "Error!",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } while (!validUserName(clientName));

                    ots.writeBytes(clientName + '\n');

                    str = ifs.readLine();
                    if (str.substring(0, str.indexOf(":") + 1).equals("Server| OK:")) {
                        System.out.println(str);

                        threadRecive = new ClientRecive(this);
                    }
                }
                if (str.equals("Server| User taken")) {
                    System.out.println(str);
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Username taken.",
                            "Error!",
                            JOptionPane.ERROR_MESSAGE);
                }

            } while (str.equals("Server| User taken"));

            createUserGUI();

            threadRecive.start();
        }
        catch (IOException e) {
            System.out.println(e);
        }

        return true;
    }
    private void createUserGUI() {
        usrGUI = new UserGUI(this);
        JFrame frame = new JFrame(this.clientName);
        frame.setContentPane(usrGUI.getPanel1());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setSize(500,600);
        frame.setBackground(Color.BLACK);
        frame.setVisible(true);
    }
    public void kill() {
        threadRecive.kill();
    }

    //Main
    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 9876);
        client.connetti();
    }
}
