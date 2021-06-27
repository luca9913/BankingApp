package GUI;

import GUI.GUI_Customer.GUI_Customer;
import GUI.GUI_Customer.GUI_Customer_CreateAccount;
import GUI.GUI_Customer.GUI_Customer_DeleteAccount;
import GUI.GUI_Login.GUI_Login;
import Login.Login;
import Person.*;

import javax.swing.*;

/**
 * Die Klasse ist für die Steuerung (Öffnung, Schließung, etc.) für die drei GUIs der Customer zuständig.
 */
public class GUI_Customer_Connector extends JFrame {

    /**Statische Variable für das Kundenobjekt.*/
    public static Customer kunde;
    /**Statische Objekterstellung des Hauptfensters für den Customer.*/
    public static GUI_Customer guiCustomer;

    /**Statische Objekterstellung des Nebenfensters CreateAccount für den Customer.*/
    static GUI_Customer_CreateAccount guiCustomerCreateAccount = new GUI_Customer_CreateAccount();
    /**Statische Objekterstellung des Nebenfensters DeleteAccount für den Customer.*/
    static GUI_Customer_DeleteAccount guiCustomerDeleteAccount = new GUI_Customer_DeleteAccount();

    /**Diese Methode dient dazu, das Fenster GUI_Customer zu öffnen.
     * Die Methode ist statisch und liefert keinen Rückgabewert.
     */
    public static void openCustomer(Customer customer){
        kunde = customer;
        guiCustomer = new GUI_Customer();
        guiCustomer.setEnabled(true);
        guiCustomer.setVisible(true);
    }

    /**Diese Methode dient dazu, das Fenster GUI_Customer zu schließen.
     * Die Methode ist statisch und liefert keinen Rückgabewert.
     */
    public static void closeCustomer(){
        System.exit(0);
    }

    /**Diese Methode dient dazu, das Fenster GUI_Customer_CreateAccount zu öffnen.
     * Die Methode ist statisch und liefert keinen Rückgabewert.
     */
    public static void openCreate(){
        guiCustomerCreateAccount.setVisible(true);
        guiCustomerCreateAccount.toFront();
        guiCustomerCreateAccount.setAlwaysOnTop(true);
        guiCustomerCreateAccount.isFocused();
        deactivateCustomerGUI();
    }

    /**Diese Methode dient dazu, das Fenster GUI_Customer_DeleteAccount zu öffnen.
     * Die Methode ist statisch und liefert keinen Rückgabewert.
     */
    public static void openDelete(){
        guiCustomerDeleteAccount.setVisible(true);
        guiCustomerDeleteAccount.toFront();
        guiCustomerDeleteAccount.setAlwaysOnTop(true);
        guiCustomerCreateAccount.isFocused();
        deactivateCustomerGUI();
    }

    /**Diese Methode dient dazu, das Fenster GUI_Customer_CreateAccount bei Betätigung des Buttons Abbrechen zu schließen.
     * Die Methode ist statisch und liefert keinen Rückgabewert.
     */
    //Methode um das Fenster GUI_Customer_CreateAccount mit Abbrechen zu schließen
    public static void closeCreate(){
        activateCustomerGUI();
        guiCustomerCreateAccount.dispose();
    }

    /**Diese Methode dient dazu, das Fenster GUI_Customer_DeleteAccount bei Betätigung des Buttons Abbrechen zu schließen.
     * Die Methode ist statisch und liefert keinen Rückgabewert.
     */
    public static void closeDelete(){
        activateCustomerGUI();
        guiCustomerDeleteAccount.dispose();
    }

    /**Diese Methode dient dazu, das Fenster GUI_Customer wieder zu aktivieren, bevor das Nebenfenster geschlossen wird.
     * Die Methode ist statisch und liefert keinen Rückgabewert.
     */
    private static void activateCustomerGUI(){
        guiCustomer.setEnabled(true);
        guiCustomer.setFocusable(true);
    }

    /**Diese Methode dient dazu, das Fenster GUI_Customer wieder zu deaktivieren, wenn das Nebenfenster geöffnet wird.
     * Die Methode ist statisch und liefert keinen Rückgabewert.
     */
    private static void deactivateCustomerGUI(){
        guiCustomer.setEnabled(false);
        guiCustomer.setFocusable(false);
    }
}
