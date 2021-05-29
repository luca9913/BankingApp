package GUI.GUI_Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GUI_Login extends JFrame {
    private JTextField LOGINNAMETextField;
    private JPasswordField passwordField1;
    private JButton exitButton;
    private JButton loginButton;
    private JPanel panel1;
    private JLabel image;
    private JLabel failedAttempts;

    public GUI_Login() {
        initialize();

        ImageIcon titleBarImage = new ImageIcon("C:\\Users\\aaron\\Desktop\\IntelliJ Workspace\\BankingApp\\src\\img\\Turing Bank Square (mini).png");
        this.setIconImage(titleBarImage.getImage());

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginPressed();
            }
        });
    }


    private void initialize() {
        failedAttempts.setVisible(false);
        setTitle("Turing Banking App");
        setSize(400, 500);
        setResizable(false);

        add(panel1);
    }


    private void loginPressed() {
        // Abgleich mit der Datenbank in Login Klase???

        // Wenn Eingabe fehlerhaft
        failedAttempt(6);
    }

    private void failedAttempt(int numberOfFailedAttempts) {
        if (numberOfFailedAttempts != 0) {
            failedAttempts.setVisible(true);
            failedAttempts.setText(numberOfFailedAttempts + " Failed Password Attempts");
        }
    }

}
