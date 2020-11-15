import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserGUI {
    private JPanel panel1;
    private JFormattedTextField destinatarioTextField;
    private JFormattedTextField messaggioTextField;
    private JButton sendBtn;
    public JTextArea ricevutiTextArea;
    private Client client;

    public JPanel getPanel1() {
        return panel1;
    }

    public UserGUI(Client client) {
        this.client = client;
        ricevutiTextArea.setEditable(false);

        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = destinatarioTextField.getText() + "| " + messaggioTextField.getText();
                System.out.println(msg);
                append("Tu" + msg.substring(msg.indexOf("|")));
                client.send(msg);
            }
        });
    }

    public void append(String msg) {
        ricevutiTextArea.append(msg + "\n");
    }

    /*
    public static void main(String[] args) {
        JFrame frame = new JFrame("UserGUI");
        frame.setContentPane(new UserGUI(new Client("127.0.0.1", 9876)).panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setSize(500,600);
        frame.setVisible(true);
    }
     */

}
