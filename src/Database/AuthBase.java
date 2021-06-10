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

    private AuthBase(){
        path = Paths.get(FOLDER + "auth.db");
        try{
            conn = DriverManager.getConnection(DRIVER + path);
            state = conn.createStatement();
        }catch(SQLException e){
            System.err.println("Beim Initialisieren der Authentifizierungsdatenbank ist folgender Fehler aufgetreten: ");
            e.printStackTrace();
            System.err.println("Das Programm wird beendet...");
            System.exit(1);
        }
    } //Konstruktor auf private setzen, damit nur eine Instanz erzeugt werden kann

    public static AuthBase initialize(){
        if(path.toString().isEmpty()){
            AuthBase auth = new AuthBase();
            return auth;
        }else{
            System.err.println("Authentifizierungs-Datenbank wurde bereits erstellt. Es ist nur eine Instanz erlaubt!");
            return null;
        }
    }

    //retrieving functions
    public ArrayList<Object[]> getAuthSet(int uid){
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

     public int getHash(int uid){
        try{
            ResultSet rs = state.executeQuery("SELECT pw_hash FROM user WHERE user_id =" + uid);
            if(rs.isClosed()){
                System.err.println("Falsche UserID, no such user.");
                return 0;
            }
            return rs.getInt(1); //possible because there's only one column specified in the SQL-Statement (pw_hash) and the uid is unique in the table user
        }catch(SQLException e){
            System.err.println("Fehler beim Auslesen des Passwort-Hashes.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return 0;
        }
    }

    public Integer getIdentity(int uid){
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
    public boolean insertUser(String pw, int id, String function){
        try {
            int hash = pw.hashCode();
            int rows = 0;

            switch(function){
                case "banker":
                    rows = state.executeUpdate("INSERT INTO user(pw_hash, banker_id) VALUES(" + hash + "," + id +")");
                    break;
                case "customer":
                    rows = state.executeUpdate("INSERT INTO user(pw_hash, customer_id) VALUES(" + hash + "," + id +")");
                    break;
            }

            if(rows == 0){
                throw new SQLException();
            }else {
                return true;
            }
        }catch(SQLException e){
            System.err.println("Fehler beim Einfügen des neuen Benutzers in die Datenbank.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return false;
        }
    }

    //deleting function
    public boolean deleteUser(int id){ //returns boolean because every id only has one account
        try{
            int rows = state.executeUpdate("DELETE FROM user WHERE user_id ='" + id + "' OR customer_id ='" + id + "' OR banker_id ='" + id +"';");
            if(rows == 0){
                throw new SQLException("Kein User zur angegebenen ID gefunden.");
            }else{
                return true;
            }
        }catch(SQLException e){
            System.err.println("Fehler beim Löschen eines Benutzers in der Datenbank.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return false;
        }
    }

    //updating function
    public boolean updateHash(int id, String pw){ //returns boolean because every id only has one account
        try{
            int hash = pw.hashCode();
            int rows = state.executeUpdate("UPDATE user SET pw_hash ='" + hash + "'WHERE user_id ='" + id + "' OR customer_id ='" + id + "' OR banker_id ='" + id +"';");
            if(rows == 0){
                throw new SQLException("Kein User zur angegebenen ID gefunden.");
            }else{
                return true;
            }
        }catch(SQLException e){
            System.err.println("Fehler beim Setzen des neuen Benutzer-Passwortes in der Datenbank.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return false;
        }
    }
}