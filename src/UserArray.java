import java.net.Socket;
import java.util.ArrayList;

public class UserArray {
    private static ArrayList<UserData> userArray = new ArrayList<>();

    //Getter
    public static ArrayList<UserData> getUserArray() {
        return userArray;
    }
    public static UserData get(int pos) {
        return userArray.get(pos);
    }
    public static String getUserName(int pos) {
        return userArray.get(pos).getUsername();
    }
    public static Socket getUserSocket(int pos) {
        return userArray.get(pos).getUserSocket();
    }
    public static Socket getUserSocket(String userName) {
        return userArray.get(getUserPos(userName)).getUserSocket();
    }
    public static int getUserPos(String Name) {
        int pos = -1;

        for (int i = 0; i < userArray.size(); i++) {
            if (userArray.get(i).getUsername().equals(Name)) {
                pos = i;
                break;
            }
        }

        return pos;
    }
    public static int getUserPos(Socket socket) {
        int pos = -1;

        for (int i = 0; i < userArray.size(); i++) {
            if (userArray.get(i).getUserSocket().equals(socket)) {
                pos = i;
                break;
            }
        }

        return pos;
    }
    public static UserData getbyId(int userId) {
        for(UserData u: userArray) {
            if(u.getId() == userId)
                return u;
        }

        return null;
    }

    //Costruttore
    public UserArray() {
        userArray = new ArrayList<>();
    }

    //Metodi
    public static boolean insert(UserData userData) {
        if(userData.equals(null) || userData.getUserSocket().equals(null) || userData.getUsername().equals(null))
            return false;
        return userArray.add(userData);
    }
    public static boolean remove(String userName) {
        for(UserData u: userArray) {
            if(u.getUsername().equals(userName)) {
                userArray.remove(u);
                return true;
            }
        }
        return false;
    }
    public static String searchUser(String message) {
        return message.substring(0, message.indexOf('|'));
    }
    public static boolean isRegistered(String username) {
        for(UserData u: userArray) {
            if(u.getUsername().equals(username))
                return true;
        }
        return false;
    }
    public static String nameList() {
        String str = "";

        for(UserData u: userArray) {
            str += u.getUsername();
            if(userArray.indexOf(u) != userArray.size() - 1)
                str += ", ";
        }

        return str;
    }
    public static boolean remove(UserData user) {
        return userArray.remove(user);
    }
}