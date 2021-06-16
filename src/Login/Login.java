package Login;

import Database.*;
import GUI.GUI_Banker.GUI_Banker;
import GUI.GUI_Customer.GUI_Customer;
import GUI.GUI_Customer_Connector;
import GUI.GUI_Login.GUI_Login;
import Person.Banker;
import Person.Person;
import Person.Customer;

import javax.swing.*;

public class Login {

    private String password;
    private String pwHash;
    private int userID = 0;
    private boolean valid;
    private AuthBase authDatabase;
    private ProdBase data;
    private Person user = null;

    public Login(AuthBase authDatabase)
    {
        this.authDatabase = authDatabase;
    }

    //hashed das Passwort
    private int hashen(String password){
        return password.hashCode();
    }

    //databaseComparison vergleicht die Eingabe mit der Datenbank und gibt mit "true" zurück ob ein passender Tupel gefunden wurde.
    public boolean databaseComparison(int userID, String password) {

        int pwHash = hashen(password);

        //hash mit Datenbank abgleichen
        if (pwHash == authDatabase.getHash(userID)) {
            System.out.println("Login erfolgreich!");
            if(userID < 1000) {
                System.out.println("Login-ID (" + userID + ") unter 1000 - Banker Login - Banker GUI öffnen");
                // TODO: User-Parameter an GUI_Banker / GUI_Customer muss noch übergeben werden
                // user = new Banker(userID, datenbank.getIdentity(userID));

                //Öffnet die Customer-GUI über die Klasse GUI_Customer_Connector
                GUI_Customer_Connector.openCustomer();

            } else {
                System.out.println("Login-ID (" + userID + ") über 1000 - Customer Login - Customer GUI öffnen");

                // user = new Customer(userID,datenbank.getIdentity(userID));
                GUI_Customer newCustomerView = new GUI_Customer();
                newCustomerView.setVisible(true);
            }
            return true;
        }
        return false;


    }

    //sendToGUI
    private void sendToGUI(boolean valid, int UserID){


    }
}
