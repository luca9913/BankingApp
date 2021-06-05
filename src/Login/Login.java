package Login;

import Database.AuthBase;
import Person.Banker;
import Person.Person;
import Person.Customer;

public class Login {

    private String password;
    private int pwHash;
    private int userID = 0;
    private boolean valid;
    private AuthBase datenbank;
    private Person user;

    public Login(AuthBase datenbank, Person user)
    {
        this.user = user;
        this.datenbank = datenbank;
    }
    //hashed das Passwort

    private int hashen(String password){

        return password.hashCode();
    }

    //readDatabase vergleicht die Eingabe mit der Datenbank und gibt mit "true" zurück ob ein passender Tupel gefunden wurde.
    public boolean readDatabase(int userID, String password){
        pwHash = hashen(password); //passwort zu hash umwandeln
        if (pwHash == datenbank.getHash(userID)) //hash mit Datenbank abgleichen
        {
            if(userID < 1000)
            {
                user = new Banker(userID, datenbank.getIdentity(userID));
            }else
            {
                user = new Customer(userID,datenbank.getIdentity(userID));
            }
            return true;
        }
        return false;
    }

    //sendToGUI
    private void sendToGUI(boolean valid, int UserID){


    }
}
