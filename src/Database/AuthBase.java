package Database;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthBase extends Database {

    static Path path = Paths.get("");
    PreparedStatement insertbanker, insertcustomer;

    private AuthBase(){
        path = Paths.get(FOLDER + "auth.db");
        try{
            conn = DriverManager.getConnection(DRIVER + path);
            state = conn.createStatement();
            insertbanker = conn.prepareStatement("INSERT INTO user(pw_hash, banker_id) VALUES (?,?)");
            insertcustomer = conn.prepareStatement("INSERT INTO user(pw_hash, customer_id) VALUES (?,?)");
        }catch(SQLException e){
            System.err.println("Beim Initialisieren der Authentifizierungsdatenbank ist folgender Fehler aufgetreten: ");
            e.printStackTrace();
            System.err.println("Das Programm wird beendet...");
            System.exit(1);
        }
    } //Konstruktor auf private setzen, damit nur eine Instanz erzeugt werden kann

    static AuthBase initialize(){
        if(path.toString().isEmpty()){
            AuthBase auth = new AuthBase();
            return auth;
        }else{
            System.err.println("Authentifizierungs-Datenbank wurde bereits erstellt. Es ist nur eine Instanz erlaubt!");
            return null;
        }
    }

    //retrieving functions

    ArrayList<Object[]> getAuthSet(int uid){
        try {
            result = state.executeQuery("SELECT * FROM user WHERE user_id = " + uid);
            return rsToArrayList(result);
        }catch(SQLException e){
            System.err.println("Fehler beim Auslesen der Authentifizierungs-Daten");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return null;
        }
    }

    String getHash(int uid){
        try{
            ResultSet rs = state.executeQuery("SELECT pw_hash FROM user WHERE user_id =" + uid);
            return rs.getString(1); //possible because there's only one column specified in the SQL-Statement (pw_hash) and the uid is unique in the table user
        }catch(SQLException e){
            System.err.println("Fehler beim Auslesen des Passwort-Hashes.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return null;
        }
    }

    Integer getIdentity(int uid){
        try{
            ResultSet rs = state.executeQuery("SELECT customer_id, banker_id FROM user WHERE user_id =" + uid);
            int id = rs.getInt("customer_id");
            if(id == 0){
                id = rs.getInt("banker_id");
            }
            return id;
        }catch(SQLException e){
            System.err.println("Fehler beim Auslesen der Nutzer-Identität.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return null;
        }
    }

    //inserting function

    boolean insertUser(String pw, int id, String function){
        try {
            int hash = pw.hashCode();

            switch(function){
                case "banker":
                    insertbanker.setInt(1, hash);
                    insertbanker.setInt(2, id);
                    insertbanker.execute();
                    break;
                case "customer":
                    insertcustomer.setInt(1, hash);
                    insertcustomer.setInt(2, id);
                    insertcustomer.execute();
                    break;
            }
            return true;
        }catch(SQLException e){
            System.err.println("Fehler beim Einfügen des neuen Benutzers in die Datenbank.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return false;
        }
    }
}