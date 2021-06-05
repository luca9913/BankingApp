package Login;

import Database.AuthBase;

public class Login {

    private String password;
    private int pwHash;
    private int userID = 0;
    private boolean valid;
    AuthBase datenbank;

    public Login(AuthBase datenbank)//Methode readFromGUI: liest userID und password aus GUI Eingabe
    {
        this.datenbank = datenbank;
    }
    //hashed das Passwort

    private int hashen(String password){

        return password.hashCode();
    }

    //readDatabase vergleicht die Eingabe mit der Datenbank und gibt mit "1" zurück ob ein passender Tupel gefunden wurde.
    public boolean readDatabase(int userID, String password){
        pwHash = hashen(password); //passwort zu hash umwandeln
        if (pwHash == datenbank.getHash(userID)) //hash mit Datenbank abgleichen
        {
            return true;
        }
        return false;
    }

    //sendToGUI
    private void sendToGUI(boolean valid, int UserID){


    }
}
