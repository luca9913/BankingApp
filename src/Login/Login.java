package Login;

import Database.AuthBase;
import GUI.GUI_Banker.GUI_Banker;
import GUI.GUI_Customer.GUI_Customer;
import GUI.GUI_Login.GUI_Login;
import Person.Banker;
import Person.Person;
import Person.Customer;

import javax.swing.*;

public class Login {

    private String password;
    private int pwHash;
    private int userID = 0;
    private boolean valid;
    private AuthBase authDatabase;
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

        pwHash = hashen(password); //passwort zu hash umwandeln

        //hash mit Datenbank abgleichen
        if (pwHash == authDatabase.getHash(userID)) {
            if(userID < 1000) {

                // TODO: User-Parameter an GUI_Banker / GUI_Customer muss noch übergeben werden
                // user = new Banker(userID, datenbank.getIdentity(userID));

                GUI_Banker newBankerView = new GUI_Banker();
                newBankerView.setVisible(true);
            } else {
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
