public class Client02 {
    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 9876);
        client.connetti();
    }
}
