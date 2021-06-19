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
    private AuthBase authDatabase = AuthBase.initialize();
    private Person user = null;

    //hashed das Passwort
    private int hashen(String password){
        return password.hashCode();
    }

    //databaseComparison vergleicht die Eingabe mit der Datenbank und gibt mit "true" zurück ob ein passender Tupel gefunden wurde.
    public boolean databaseComparison(int userID, String password) {

        int pwHash = hashen(password);
        Object[] authSet = authDatabase.getAuthSet(userID).get(1);

        //hash mit Datenbank abgleichen
        if (pwHash == authDatabase.getHash(userID)) {
            System.out.println("Login erfolgreich!");
            System.out.println((Integer)authSet[2]);
            if( (Integer)authSet[2] <= 0){
                System.out.println("Customer-ID (" + userID + ") <= 0 - Banker Login - Banker GUI öffnen");
                // TODO: User-Parameter an GUI_Banker muss noch übergeben werden

                Banker banker = new Banker((Integer)authSet[3]);
                GUI_Banker newBankerView = new GUI_Banker(banker);
                newBankerView.setVisible(true);

            } else {
                System.out.println("Login-ID (" + userID + ") über 1000 - Customer Login - Customer GUI öffnen");

                // TODO: User-Parameter an GUI_Customer muss noch übergeben werden

                //Bitte die GUI_Customer über die Klasse GUI_Customer_Connector mit der Methode openCustomer aufrufen.
                //Bitte vermeiden das Objekt der GUI hier zu erstellen und aufzurufen.
                GUI_Customer_Connector.openCustomer();
            }
            return true;
        }
        return false;


    }

    //sendToGUI
    private void sendToGUI(boolean valid, int UserID){


    }
}
