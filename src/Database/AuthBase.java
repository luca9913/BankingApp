package Database;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Ermöglicht den Zugriff auf die Authentifizierungs-Datenbank und stellt Funktionen zum Abrufen und Einfügen von neuen Daten bereit
 */
public class AuthBase extends Database {

    static Path path = Paths.get("");
    /**Datenbank-Verbindung aus dem Paket java.sql
     * Link: java.sql.Connection */
    Connection conn;
    /**Datenbank-Statement zur Ausführung von Abfragen
     * Link: java.sql.Statement*/
    Statement state;
    /**Speicherung der Rückgabe einer Datenbank-Abfrage
     * Link: java.sql.ResultSet*/
    ResultSet result;

    /**
     * Konstruktor der Authentifizierungs-Datenbank. Es wird eine direkte Verbindung zur Datenbank hergestellt.
     * Der Konstruktor ist private, damit sichergestellt ist, dass nur eine Instanz erzeugt werden kann.
     */
    //Konstruktor auf private setzen, damit nur eine Instanz erzeugt werden kann
    private AuthBase(){
        path = Paths.get(FOLDER + "auth.db");
        try{
            this.conn = DriverManager.getConnection(DRIVER + path);
            this.state = conn.createStatement();
        }catch(SQLException e){
            System.err.println("Beim Initialisieren der Authentifizierungsdatenbank ist folgender Fehler aufgetreten: ");
            e.printStackTrace();
            System.err.println("Das Programm wird beendet...");
            System.exit(1);
        }
    }

    /**
     * Initialisiert die Datenbank, erstellt also eine Datenbank-Instanz, über die auf die Datenbank zugegriffen werden kann
     */
    public static AuthBase initialize(){
        AuthBase auth = new AuthBase();
        return auth;
    }

    /**
     * Schließt die Datenbank, indem die Verbindung getrennt wird.
     */
    public void close(){
        try{
            this.conn.close();
        }catch(SQLException e){
            System.err.println("Beim Schliessen der Datenbank-Verbindung zur Authentifizierungs-Datenbank ist ein Fehler aufgetreten!");
            e.printStackTrace();
        }
    }


    /**
     * Hier Text einfügen
     * @param sql SQLite Statement, welches in der Datenbank ausgeführt werden soll
     * @return Es wird zwischen zwei Szenarien unterschieden: 1.
     */

    ArrayList<Object[]> executeCustomQuery(String sql){
        try {
            //searches for the 'SELECT' string in the statement
            if(Pattern.compile(Pattern.quote("select"), Pattern.CASE_INSENSITIVE).matcher(sql).find()){
                //if the statement is a SELECT statement
                result = state.executeQuery(sql); //save the result into the ResultList
                return rsToArrayList(result); //return the ArrayList containing Objects for each selected row
            }else{
                //if the statement doesn't contain the 'SELECT' string
                int mrows = state.executeUpdate(sql); //save the number of affected rows in a variable
                ArrayList<Object[]> update_result= new ArrayList<>(1); //initialize a new ArrayList with one element
                update_result.add(new Integer[]{mrows}); //add the number of rows as an Integer-Array with 1 entry to the ArrayList
                return update_result; //return the ArrayList containing the number of affected rows
            }
        }catch(SQLException e){ //catch an SQL-Exception
            //print a custom error message and the Exception stacktrace to the error log
            System.err.println("Beim Ausführen der Abfrage ist ein Fehler aufgetreten.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Hier Text einfügen //retrieving functions
     * @param uid User-ID des des Nutzers
     * @return
     */
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

    /**
     * Hier Text einfügen
     * @param uid
     * @return
     */
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

    /**
     * Hier Text einfügen
     * @param uid
     * @return
     */
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

    /**
     * Hier Text einfügen
     * @param login_id
     * @param pw
     * @param user_id
     * @param function
     * @return
     */
    public boolean insertUser(int login_id, String pw, int user_id, String function){
        try {
            int hash = pw.hashCode();
            int rows = 0;

            switch(function){
                case "banker":
                    rows = state.executeUpdate("INSERT INTO user(user_id, pw_hash, banker_id) VALUES(" + login_id + "," + hash + "," + user_id +")");
                    break;
                case "customer":
                    rows = state.executeUpdate("INSERT INTO user(user_id, pw_hash, customer_id) VALUES(" + login_id + "," + hash + "," + user_id +")");
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

    /**
     * Hier Text einfügen//returns boolean because every id only has one account
     * @param id
     * @return
     */
    public boolean deleteUser(int id){
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

    /**
     * Hier Text einfügen
     * @param id
     * @param pw
     * @return
     */
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