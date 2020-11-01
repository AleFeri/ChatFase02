import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class ServerThread extends Thread {
    Server server;
    UserData user;
    String stringFromUser;
    BufferedReader inFromClient;
    DataOutputStream outToClient;

    //Costruttore
    public ServerThread(UserData userData, Server server) {
        this.server = server;
        this.user = userData;
        try {
            inFromClient = new BufferedReader(new InputStreamReader(user.getUserSocket().getInputStream()));
            outToClient = new DataOutputStream(user.getUserSocket().getOutputStream());
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    //Metodi
    public void run() {
        try {
            registerUser();
            exe();
        }
        catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
    public void registerUser() {
        try {
            for (;;) {
                outToClient.writeBytes("Server| Insert Username\n");
                stringFromUser = inFromClient.readLine();

                if (!UserArray.isRegistered(stringFromUser)) {
                    String userName = stringFromUser;
                    user.setUsername(userName);
                    server.print("User: " + userName + " Joined");

                    UserArray.insert(user);

                    outToClient.writeBytes("Server| OK: Write 'Server| HELP' to get server's commands\n");

                    break;
                } else {
                    outToClient.writeBytes("Server| User taken\n");
                }
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
    public void exe() {
        for(;;) {
            try {
                stringFromUser = inFromClient.readLine();

                String reciver = UserArray.searchUser(stringFromUser);

                sendTo(reciver, stringFromUser.substring(stringFromUser.indexOf("|") + 1));

            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
    public void close(String msg) {
        if(msg.equals(" EXIT")) {
            if(UserArray.remove(user.getUsername())) {
                try {
                    outToClient = new DataOutputStream(user.getUserSocket().getOutputStream());

                    outToClient.writeBytes("Server| Exit successful!\n");
                } catch (IOException e) {
                    System.out.println(e);
                }
            }

            this.interrupt();
        }
    }
    private void sendTo(String reciver, String msg) {
        if(isForServer(reciver, msg)) {
            return ;
        }
        try {
            if (reciver.contains(",")) { //Multi Utente
                String[] recivers = reciver.split(", ");

                for(String str: recivers) {
                    sendTo(str, msg);
                }

            } else { //Mono Utente
                if (!UserArray.isRegistered(reciver)) { //Reciver inesistente
                    outToClient = new DataOutputStream(user.getUserSocket().getOutputStream());

                    outToClient.writeBytes("Server| User " + reciver + " doesn't exist\n");
                }
                if (!reciver.equals(user.getUsername())) { //Reciver esistente
                    Socket reciverSocket = UserArray.getUserSocket(reciver);
                    outToClient = new DataOutputStream(reciverSocket.getOutputStream());

                    stringFromUser = user.getUsername() + "|" + msg;

                    outToClient.writeBytes(stringFromUser + '\n');
                } else { //Scrive a se stesso
                    outToClient = new DataOutputStream(user.getUserSocket().getOutputStream());

                    outToClient.writeBytes("Server| Self Writing is not allowed!\n");
                }
            }
        }
        catch (IOException e) {
            server.print(e.toString());
        }
    }
    private boolean isForServer(String reciver, String msg) {
        try {
            if (reciver.equals("Server")) {
                outToClient = new DataOutputStream(user.getUserSocket().getOutputStream());

                if (msg.equals(" HELP")) {
                    outToClient.writeBytes("Server| Write 'Server| LIST' to get user's list. 'Server| EXIT' to exit from server\n");
                }
                else if (msg.equals(" LIST")) {
                    outToClient.writeBytes(UserArray.nameList() + '\n');
                }
                else if (msg.equals(" EXIT")) {
                    close(msg);
                }

                return true;
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }

        return false;
    }
}
