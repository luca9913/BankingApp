package GUI.GUI_Login;

import Login.Login;
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
    private Login loginObjekt; //Logininstantz mit der auf die Login-Datenbank zugegriffen werden kann

    public GUI_Login(Login loginObjekt) {
        this.loginObjekt = loginObjekt;
        initialize();

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


    private void loginPressed(String uid, String password) {
        if(loginObjekt.readDatabase(Integer.parseInt(uid), password))
        {
            System.exit(0);
        }else { // wenn eingabe fehlerhaft
            attempts++;
            failedAttempt(attempts);
        }
    }

    private void failedAttempt(int numberOfFailedAttempts) {
        if (numberOfFailedAttempts != 0) {
            failedAttempts.setVisible(true);
            failedAttempts.setText(numberOfFailedAttempts + " Failed Password Attempts");
        }
    }

}
