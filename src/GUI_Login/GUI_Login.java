package GUI_Login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI_Login extends JFrame {
    private JTextField LOGINNAMETextField;
    private JPasswordField passwordField1;
    private JButton exitButton;
    private JButton loginButton;
    private JPanel panel1;
    private JLabel image;

    public GUI_Login() {
        initialize();


        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void initialize() {
        setTitle("Turing Banking App");
        setSize(400, 500);
        setResizable(false);



        add(panel1);
        /*
        add(loginButton);
        add(passwordField1);
        add(loginButton);
        add(exitButton);

         */
    }


/*
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUI_Login window = new GUI_Login();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

 */
}
