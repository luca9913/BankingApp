package Login;

import Database.AuthBase;

public class Login {

    private String password;
    private int pwHash;
    private int userID = 0;
    private boolean valid;
    AuthBase datenbankb;

    public Login(AuthBase datenbank)//Methode readFromGUI: liest userID und password aus GUI Eingabe

    //hashed das Passwort

    private int hashen(String password){

        return password.hashCode();
    }

    //readDatabase vergleicht die Eingabe mit der Datenbank und gibt mit "1" zurück ob ein passender Tupel gefunden wurde.
    public boolean readDatabase(int userID, String password){
        pwHash = hashen(password);
        // (SELECT user_id FROM user WHERE pw_HASH == pwHash && user_ID == userID)

        return valid;
    }

    //sendToGUI
    private void sendToGUI(boolean valid, int UserID){


    }
}
