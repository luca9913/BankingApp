package GUI;

import GUI.GUI_Customer.GUI_Customer;
import GUI.GUI_Customer.GUI_Customer_CreateAccount;
import GUI.GUI_Customer.GUI_Customer_DeleteAccount;

import javax.swing.*;

public class GUI_Customer_Connector extends JFrame {

    //Objekterstellung der GUI
    static GUI_Customer guiCustomer = new GUI_Customer();

    //Objekterstellung der Nebengui´s
    static GUI_Customer_CreateAccount guiCustomerCreateAccount = new GUI_Customer_CreateAccount();
    static GUI_Customer_DeleteAccount guiCustomerDeleteAccount = new GUI_Customer_DeleteAccount();

    //Methode um das Fenster GUI_Customer_CreateAccount zu öffnen
    public static void openCustomer(){
        guiCustomer.setVisible(true);
    }

    //Methode um das Fenster GUI_Customer_DeleteAccount zu öffnen
    public static void closeCustomer(){
        System.exit(0);
    }

    //Methode um das Fenster GUI_Customer_CreateAccount zu öffnen
    public static void openCreate(){
        deactivateCustomerGUI();
        guiCustomerCreateAccount.setVisible(true);
        guiCustomerCreateAccount.toFront();
        guiCustomerCreateAccount.setAlwaysOnTop(true);
        guiCustomerCreateAccount.isFocused();
    }

    //Methode um das Fenster GUI_Customer_DeleteAccount zu öffnen
    public static void openDelete(){
        deactivateCustomerGUI();
        guiCustomerDeleteAccount.setVisible(true);
        guiCustomerDeleteAccount.toFront();
        guiCustomerDeleteAccount.setAlwaysOnTop(true);
        guiCustomerCreateAccount.isFocused();
    }

    //Methode um das Fenster GUI_Customer_CreateAccount mit Abbrechen zu schließen
    public static void closeCreate(){
        activateCustomerGUI();
        guiCustomerCreateAccount.dispose();
    }

    //Methode um das Fenster GUI_Customer_DeleteAccount mit Abbrechen zu schließen
    public static void closeDelete(){
        activateCustomerGUI();
        guiCustomerDeleteAccount.dispose();
    }

    //Methode um das Fenster GUI_Customer_CreateAccount mit X zu schließen
    public static int closeCreateX(){
        activateCustomerGUI();
        return HIDE_ON_CLOSE;
    }

    //Methode um das Fenster GUI_Customer_DeleteAccount mit X zu schließen
    public static int closeDeleteX(){
        activateCustomerGUI();
        return HIDE_ON_CLOSE;
    }

    private static void activateCustomerGUI(){
        guiCustomer.setEnabled(true);
        guiCustomer.setFocusable(true);
    }
    private static void deactivateCustomerGUI(){
        guiCustomer.setEnabled(false);
        guiCustomer.setFocusable(true);
    }
}
