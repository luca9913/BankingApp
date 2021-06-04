package Database;

import java.sql.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;

//TODO: add class or implement ResultMaps (to store ResultSets in Maps with keys and String-Arrays or alike)

abstract class Database {
    final String DRIVER = "jdbc:sqlite:";
    final String FOLDER = "src/data/";
    static Connection conn;
    static Statement state;
    ArrayList<String[]> result; //result[columnname][rowvalues]

    boolean executeCustomQuery(String sql){ //TODO: option for returning Results
        try {
            return state.execute(sql); //Gibt true zurück, wenn die Abfrage Ergebnisse liefert. Gibt false zurück, wenn es sich um ein UPDATE handelte oder keine Werte zurückkamen.
        }catch(SQLException e){
            System.err.println("Beim Ausführen der Abfrage ist ein Fehler aufgetreten.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return false;
        }
    }

    ArrayList<String[]> rsToArrayList(ResultSet rs){
        try {
            //initialize all needed variables and Collections
            ResultSetMetaData rsmeta = rs.getMetaData(); //Get Metadata from the ResultSet
            int columns = rsmeta.getColumnCount(); //Get the column count for the for-loop
            String[] result_row = new String[columns]; //initialize the Array to hold the result of every column for each row
            ArrayList<String[]>result = new ArrayList<String[]>(); //initialize ArrayList which holds the String Arrays for each row

            for(int i = 1; i <= columns; i++){
                result_row[i-1] = rsmeta.getColumnName(i);
            }
            result.add(ArrayUtils.clone(result_row));
            while(rs.next()) { //while there are results left in the Set, fill the result_row Array with the column values
                for (int i = 1; i <= columns; i++) {
                    result_row[i-1] = rs.getString(i);
                }
                if(result.size() <= 10) {
                    result.add(ArrayUtils.clone(result_row));
                }else{
                    result.ensureCapacity(result.size() + 1);
                    result.add(ArrayUtils.clone(result_row));
                }
            }
            return result;
        }catch(SQLException e){
            System.err.println("Fehler beim Übertragen eines ResultSets in einen StringArray.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return null;
        }
    }
}

class AuthBase extends Database{

    static Path path = Paths.get("");
    PreparedStatement insertbanker, insertcustomer;

    private AuthBase(){
        this.path = Paths.get(FOLDER + "auth.db");
        try{
            this.conn = DriverManager.getConnection(DRIVER + path);
            this.state = conn.createStatement();
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

    ArrayList<String[]> getAuthSet(int uid){
        try {
            ResultSet rs = state.executeQuery("SELECT * FROM user WHERE user_id = " + uid);
            result = rsToArrayList(rs);
            return result;
        }catch(SQLException e){
            System.err.println("Fehler beim Ausführen des SQL-Statements.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return null;
        }
    }

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

class ProdBase extends Database{

    static Path path = Paths.get("");
    PreparedStatement insertaccount, insertbanker, insertcustomer, insertrelease, inserttransfer;

    private ProdBase(){
        this.path = Paths.get(FOLDER + "production.db");
        try{
            this.conn = DriverManager.getConnection(DRIVER + path);
            this.state = conn.createStatement();
            insertaccount = conn.prepareStatement("INSERT INTO account(type, balance, dispo, transfer_limit, owner, banker_id) VALUES (?,0,?,?,?,?)");
        }catch(SQLException e){
            System.err.println("Beim Initialisieren der Stammdatenbank ist folgender Fehler aufgetreten: ");
            e.printStackTrace();
            System.err.println("Das Programm wird beendet...");
            System.exit(1);
        }
    } //Konstruktor auf private setzen, damit nur eine Instanz erzeugt werden kann

    static ProdBase initialize(){
        if(path.toString().isEmpty()){
            ProdBase prod = new ProdBase();
            return prod;
        }else{
            System.err.println("Stammdatenbank wurde bereits erstellt. Es ist nur eine Instanz erlaubt!");
            return null;
        }
    }

    ArrayList<String[]> getAccountData(int accid){
        try {
            ResultSet rs = state.executeQuery("SELECT * FROM account WHERE account_id = " + accid);
            return rsToArrayList(rs);
        }catch(SQLException e){
            System.err.println("Fehler beim Ausführen des SQL-Statements.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return null;
        }
    }
    //Weitere Abfragen hinzufügen (für jede Tabelle)

    boolean insertAccount(String type, int dispo, double transferlimit, int oid, int bid){
        try {
            insertaccount.setString(1, type);
            insertaccount.setInt(2, dispo);
            insertaccount.setDouble(3, transferlimit);
            insertaccount.setInt(4, oid);
            insertaccount.setInt(5, bid);
            return insertaccount.execute();
        }catch(SQLException e){
            System.err.println("Fehler beim Einfügen des neuen Benutzers in die Datenbank.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return false;
        }
    }

    //Weitere Eingaben hinzufügen

    //Aktualisierungen hinzufügen
}


class DatabaseTest{

    public static void main(String[] args){
        AuthBase auth = AuthBase.initialize(); //Datenbank initialisieren
        System.out.println(auth.path.toString()); //Pfad ausgeben
        AuthBase test = AuthBase.initialize(); //Private Konstruktor demonstrieren
        ArrayList<String[]> result = auth.getAuthSet(1);
        System.out.println("Es wurde " + result.size() + " Zeilen gefunden.");
        String[] row = result.get(0);
        System.out.println("Die Tabelle hat " + row.length + " Spalten.");
        for(int i = 0; i < row.length; i++){
            System.out.println("Spalte" + (i+1) + ": " + row[i]);
        }
    }
}