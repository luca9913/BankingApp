package Login;

public class Login {

    private String password;
    private String pwHash;
    private int userID = 0;
    private boolean valid;

    //Methode readFromGUI: liest userID und password aus GUI Eingabe

    //hashed das Passwort
    private String hashen(String password){

        return pwHash;
    }

    //readDatabase vergleicht die Eingabe mit der Datenbank und gibt mit "1" zurück ob ein passender Tupel gefunden wurde.
    private boolean readDatabase(String userID, String pwHash){
        private int user_ID;
        // (SELECT user_id FROM user WHERE pw_HASH == pwHash && user_ID == userID)
        if(user_ID != 0)
            valid = true;
        else
            valid = false;

        return valid;
    }

    //sendToGUI
    private void sendToGUI(boolean valid, int UserID){


    }
}
