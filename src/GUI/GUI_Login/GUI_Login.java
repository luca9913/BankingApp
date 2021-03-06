package GUI.GUI_Login;

import Login.Login;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.Normalizer;

/**
 * Die Klasse ist für das GUI des Login zuständig. Dort werden die Components
 * und Actions verwaltet und weitere Optionen für die GUI festgelegt.
 */
public class GUI_Login extends JFrame implements KeyListener {

    private int attempts = 0;
    private JLabel image;
    private JTextField LOGINNAMETextField;
    private JPasswordField passwordField1;
    private JButton exitButton;
    private JButton loginButton;
    private JPanel panel1;
    private JLabel failedAttempts;

    /**
     * Diese Variable enthält die Login-Instanz, mit welcher auf die Login-Datenbank zugegriffen werden kann.
     */
    private Login loginReference;

    /**
     * Dieser Konstruktor ist für die Actions und weitere Optionen des Login-Gui´s zuständig.
     * @param loginReference Objekt der Klasse Login.java
     */
    public GUI_Login(Login loginReference) {
        this.loginReference = loginReference;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initialize();

        setLocationRelativeTo(null);

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

    /**
     * Die Methode ist für die Initialisierung der GUI_Login und deren Optionen zuständig.
     */
    private void initialize() {
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

    /**
     * Diese Methode fängt Fehler beim Einloggvorgang ab, zum Beispiel wenn die
     * Textfelder leer sind und weitere.
     */
    private void loginPressed() {
        System.out.println("Login Pressed");

        if (LOGINNAMETextField.getText().length() == 0) {
            System.out.println("Keine Login-ID eingegeben");
            JOptionPane.showMessageDialog(null, "Bitte geben Sie eine Login-ID ein.", "Login-ID Feld leer!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int userID;
        try {
            userID = Integer.parseInt(LOGINNAMETextField.getText());
        } catch(NumberFormatException e) {
            System.out.println("Fehlerhafte Login-ID");
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Die Login-ID darf ausschließlich aus Zahlen bestehen und hat in der Regel nicht mehr als 8 Stellen.", "Ungültige Login-ID!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder passwordStringBuilder = new StringBuilder();
        for(char chars: passwordField1.getPassword()) {
            passwordStringBuilder.append(chars);
        }

        if (passwordStringBuilder.length() == 0) {
            System.out.println("Kein Passwort eingegeben");
            JOptionPane.showMessageDialog(null, "Bitte geben Sie ein Passwort ein.", "Passwortfeld leer!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if(loginReference.databaseComparison(userID, passwordStringBuilder.toString())) {
            System.out.println("Login-ID und Benutzername stimmen überein - Login Fenster schließen");
            this.dispose();
        } else {
            System.out.println("Login-ID und Benutzername stimmen nicht überein - Failed Attempts anzeigen/erhöhen");
            attempts++;
            failedAttempt(attempts);
        }
    }

    /**
     * Diese Methode ist für das Aktivieren und die Anzeige der Fehlversuche
     * bei der Passworteingabe beim Login zuständig.
     * @param numberOfFailedAttempts Als Parameter wird die Anzahl an Fehlschlägen
     * des Logins übergeben.
     */
    private void failedAttempt(int numberOfFailedAttempts) {
        passwordField1.setText("");
        LOGINNAMETextField.setText("");
        if (numberOfFailedAttempts != 0) {
            failedAttempts.setVisible(true);
            if (numberOfFailedAttempts == 1) {
                failedAttempts.setText("1 Failed Password Attempt");
            } else {
                failedAttempts.setText(numberOfFailedAttempts + " Failed Password Attempts");
            }
        }
    }

    /**
     * Diese Methode muss vorhanden sein, da der KeyListener diese verlangt.
     * @param e Parameter für das Key-Event.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_ENTER){
            System.out.println("ENTER-Pressed");
            loginPressed();
        }
    }

    /**
     * Diese Methode muss vorhanden sein, da der KeyListener diese verlangt.
     * @param e Parameter für das Key-Event.
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Diese Methode muss vorhanden sein, da der KeyListener diese verlangt.
     * @param e Parameter für das Key-Event.
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
