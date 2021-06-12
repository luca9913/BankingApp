package GUI;

import GUI.GUI_Customer.GUI_Customer_CreateAccount;
import GUI.GUI_Customer.GUI_Customer_DeleteAccount;

import javax.swing.*;

public class HelpMethodsCustomer extends JFrame {

    //Objekterstellung der Nebengui´s
    static GUI_Customer_CreateAccount newCreate = new GUI_Customer_CreateAccount();
    static GUI_Customer_DeleteAccount newDelete = new GUI_Customer_DeleteAccount();

    //Methode um das Fenster GUI_Customer_CreateAccount zu öffnen
    public static void openCreate(){
        newCreate.setVisible(true);
        newCreate.toFront();
        newCreate.setAlwaysOnTop(true);
    }

    //Methode um das Fenster GUI_Customer_DeleteAccount zu öffnen
    public static void openDelete(){
        newDelete.setVisible(true);
        newDelete.toFront();
        newDelete.setAlwaysOnTop(true);
    }

    //Methode um das Fenster GUI_Customer_CreateAccount zu schließen
    public static void closeCreate(){
        newCreate.dispose();
    }

    //Methode um das Fenster GUI_Customer_DeleteAccount zu schließen
    public static void closeDelete(){
        newDelete.dispose();
    }
}
