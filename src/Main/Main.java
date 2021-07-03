package Main;

import GUI.GUI_Login.GUI_Login;
import GUI.HelpMethods;
import Login.Login;

/**
 * Dies ist die Main-Klasse der Banking-App.
 */
public class Main {

    /**
     * Diese Methode ist für das einmalige Aufrufen der Login-Klasse
     * bei Programmstart zuständig.
     * @param args Standardmäßiger Parameter der Main-Methode, welcher ein String-Array
     * ist. Er wird von uns nicht genutzt, kann jedoch frü größere bzw. komplexere
     * Anwendungen von Nutzen sein, wenn beim Programmstart System-Parameter mitgegeben
     * werden sollen oder Parameter aus der Kommandozeile.
     */
    public static void main(String[] args) {

        Login log = new Login();
        GUI_Login newView = new GUI_Login(log);
        newView.setVisible(true);

    }
}
