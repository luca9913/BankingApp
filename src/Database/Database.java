package Database;

import java.sql.*;
import java.nio.file.*;

abstract class Database {
    final String DRIVER = "jdbc:sqlite:";
    final String FOLDER = "src/data/";
    static Connection conn;
    static Statement state;

    boolean executeCustomQuery(String sql){
        try {
            return state.execute(sql); //Gibt true zurück, wenn die Abfrage Ergebnisse liefert. Gibt false zurück, wenn es sich um ein UPDATE handelte oder keine Werte zurückkamen.
        }catch(SQLException e){
            System.err.println("Beim Ausführen der Abfrage ist ein Fehler aufgetreten.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return false;
        }
    }

    String[] rsToStringArray(ResultSet rs){
        try {
            ResultSetMetaData rsmeta = rs.getMetaData();
            int columns = rsmeta.getColumnCount();
            String[] result = new String[columns];
            for (int i = 1; i <= columns; i++) { //Möglichkeit finden Zeilen & Spalten auszulesen
                result[i-1]= rs.getString(i);
            }
            return result;
        }catch(SQLException e){
            System.err.println("Fehler beim Übertragen eines ResultSets in einen StringArray.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return null;
        }
    }

    String[] getTables(){
        try{
            ResultSet rs = state.executeQuery("SELECT name FROM sqlite_master WHERE type = 'table' AND name NOT LIKE 'sqlite_%'");
            return rsToStringArray(rs);
        }catch(SQLException e){
            System.err.println("Fehler beim Auslesen der Tabellen:");
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

    String[] getAuthSet(int uid){
        try {
            ResultSet rs = state.executeQuery("SELECT * FROM user WHERE user_id = " + uid);
            return rsToStringArray(rs);
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

    String[] getAccountData(int accid){
        try {
            ResultSet rs = state.executeQuery("SELECT * FROM account WHERE account_id = " + accid);
            return rsToStringArray(rs);
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
        auth.executeCustomQuery("UPDATE user SET customer_id = 485 WHERE user_id = 1");

        ProdBase prod = ProdBase.initialize(); //Stammdatenbank initialisieren
        String[] prodtables = prod.getTables(); //Tabellen abfragen
        for(int i = 0; i < prodtables.length; i++){ //Tabellennamen ausgeben
            System.out.println("Tabelle " + (i+1) + ": " + prodtables[i]);
        }
        prod.insertAccount("Girokonto", 200, 9999.99, 1, 3);
    }
}