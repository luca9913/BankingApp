package GUI.GUI_Login;

import Login.Login;
import Main.Main;
import Person.Banker;
import Person.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GUI_Login extends JFrame {

    private int attempts = 0;

    private JTextField LOGINNAMETextField;
    private JPasswordField passwordField1;
    private JButton exitButton;
    private JButton loginButton;
    private JPanel panel1;
    private JLabel image;
    private JLabel failedAttempts;

    private Login loginReference; //Login-Instanz mit der auf die Login-Datenbank zugegriffen werden kann
    private Main mainReference;

    public GUI_Login(Login loginReference) {
        this.loginReference = loginReference;
        initialize();

        // Title Bar Icon
        ImageIcon titleBarImage = new ImageIcon("src/img/Turing Bank Square (32x32).png");
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
                System.out.println("BN: " + LOGINNAMETextField.getText());
                System.out.println("PW: " + passwordField1.getPassword().toString());


                // TODO: Eingabe überprüfen! LOGINNAME darf nur Zahlen enthalten, sonst Fehler in Z.71 -> Integer.parseInt(userID);
                loginPressed(LOGINNAMETextField.getText(), passwordField1.getPassword().toString());
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


    private void loginPressed(String userID, String password) {
        System.out.println("Login Pressed");

        if(loginReference.databaseComparison(Integer.parseInt(userID), password)) {
            // Passwort und Nutzername stimmen überein
            this.setVisible(false);
        } else {
            // Fehlerhafte Eingabe -> Erhöhe Fehlversuche
            attempts++;
            failedAttempt(attempts);
        }


    }

    private void failedAttempt(int numberOfFailedAttempts) {
        if (numberOfFailedAttempts != 0) {
            failedAttempts.setVisible(true);
            if (numberOfFailedAttempts == 1) {
                failedAttempts.setText("1 Failed Password Attempt");
            } else {
                failedAttempts.setText(numberOfFailedAttempts + " Failed Password Attempts");
            }
        }
    }

}
