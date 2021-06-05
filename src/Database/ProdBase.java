package Database;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdBase extends Database {

    static Path path = Paths.get("");
    PreparedStatement insertaccount;


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

    //retrieving function
    Object[] getData(int id, String table){
        try {
            String idname = table.concat("_id");
            PreparedStatement getData = conn.prepareStatement("SELECT * FROM " + table + " WHERE " + idname + " = " + id);
            result = getData.executeQuery();
            return rsToArrayList(result).get(1);
        }catch(SQLException e){
            System.err.println("Fehler beim Ausführen des SQL-Statements.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return null;
        }
    }

    ArrayList<Object[]> getAllAccounts(int id){
        try{
            if(id >= 1000) {
                result = state.executeQuery("SELECT * FROM account WHERE owner = " + id);
                return rsToArrayList(result);
            }else if(id <= 1000){
                result = state.executeQuery("SELECT * FROM account WHERE banker_id =" + id);
                return rsToArrayList(result);
            }else{
                throw new SQLException("Falsche Angabe für Parameter 'role'");
            }
        }catch(SQLException e){
            System.err.println("Fehler beim Ausführen des SQL-Statements.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return null;
        }
    }

    //Weitere Abfragen hinzufügen (für jede Tabelle)


    //inserting functions
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