package Main;

import GUI.GUI_Login.GUI_Login;
import Login.Login;

/**
 * Hier Text einfügen
 */
public class Main {

    /**
     * Hier Text einfügen
     * @param args
     */
    public static void main(String[] args) {

        Login log = new Login();
        GUI_Login newView = new GUI_Login(log);
        newView.setVisible(true);

    }
}
