package GUI.GUI_Login;

import Login.Login;
import Main.Main;
import Person.Banker;
import Person.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GUI_Login extends JFrame implements KeyListener {

    private int attempts = 0;

    private JTextField LOGINNAMETextField;
    private JPasswordField passwordField1;
    private JButton exitButton;
    private JButton loginButton;
    private JPanel panel1;
    private JLabel image;
    private JLabel failedAttempts;

    private Login loginReference; //Login-Instanz mit der auf die Login-Datenbank zugegriffen werden kann

    public GUI_Login(Login loginReference) {
        this.loginReference = loginReference;
        initialize();

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
        // Title Bar Icon
        ImageIcon titleBarImage = new ImageIcon("src/img/Turing Bank Square (32x32).png");
        this.setIconImage(titleBarImage.getImage());

        failedAttempts.setVisible(false);
        setTitle("Turing Banking App");
        setSize(400, 500);
        setResizable(false);

        LOGINNAMETextField.addKeyListener(this);
        passwordField1.addKeyListener(this);
        add(panel1);
    }


    private void loginPressed() {
        System.out.println("Login Pressed");

        //Überprüft, ob userID vorhanden ist
        if (LOGINNAMETextField.getText().length() == 0) {
            System.out.println("Keine Login-ID eingegeben");
            JOptionPane.showMessageDialog(null, "Bitte geben Sie eine Login-ID ein.", "Login-ID Feld leer!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Überprüft, ob sich der String in einen Integer umwandeln lässt
        int userID;
        try {
            userID = Integer.parseInt(LOGINNAMETextField.getText());
        } catch(NumberFormatException e) {
            System.out.println("Fehlerhafte Login-ID");
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Die Login-ID darf ausschließlich aus Zahlen bestehen und hat in der Regel nicht mehr als 8 Stellen.", "Ungültige Login-ID!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Konvertiert char[] in StringBuilder
        StringBuilder passwordStringBuilder = new StringBuilder();
        for(char chars: passwordField1.getPassword()) {
            passwordStringBuilder.append(chars);
        }

        // Überprüft, ob Passwort vorhanden ist
        if (passwordStringBuilder.length() == 0) {
            System.out.println("Kein Passwort eingegeben");
            JOptionPane.showMessageDialog(null, "Bitte geben Sie ein Passwort ein.", "Passwortfeld leer!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Compare data with authbase
        if(loginReference.databaseComparison(userID, passwordStringBuilder.toString())) {
            // Passwort und Nutzername stimmen überein
            System.out.println("Login-ID und Benutzername stimmen überein - Login Fenster schließen");

            //TODO: setVisible(false) benötigt Arbeitsspeicher - bessere Methode implementieren
            this.setVisible(false);
        } else {
            // Fehlerhafte Eingabe -> Erhöhe Fehlversuche
            System.out.println("Login-ID und Benutzername stimmen nicht überein - Failed Attempts anzeigen/erhöhen");
            attempts++;
            failedAttempt(attempts);
        }
    }


    private void failedAttempt(int numberOfFailedAttempts) {
        // TODO: Falls genug Zeit -> Timeout programmieren nach 3 Fehlversuchen
        if (numberOfFailedAttempts != 0) {
            failedAttempts.setVisible(true);
            if (numberOfFailedAttempts == 1) {
                failedAttempts.setText("1 Failed Password Attempt");
            } else {
                failedAttempts.setText(numberOfFailedAttempts + " Failed Password Attempts");
            }
        }
    }


    // Methoden müssen vorhanden sein, da KeyListener diese verlangt
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_ENTER){
            System.out.println("ENTER-Pressed");
            loginPressed();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
