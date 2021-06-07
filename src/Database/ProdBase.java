package Database;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Konto.Konto;
import Person.Person;

public class ProdBase extends Database {

    static Path path = Paths.get("");

    private ProdBase(){
        this.path = Paths.get(FOLDER + "production.db");
        try{
            this.conn = DriverManager.getConnection(DRIVER + path);
            this.state = conn.createStatement();
        }catch(SQLException e){
            System.err.println("Beim Initialisieren der Stammdatenbank ist folgender Fehler aufgetreten: ");
            e.printStackTrace();
            System.err.println("Das Programm wird beendet...");
            System.exit(1);
        }
    } //Konstruktor auf private setzen, damit nur eine Instanz erzeugt werden kann

    public static ProdBase initialize(){
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
            return rsToArrayList(state.executeQuery("SELECT * FROM " + table + " WHERE " + idname + " = " + id)).get(1);
        }catch(SQLException e){
            System.err.println("Fehler beim Auslesen der Daten aus Tabelle: " + table);
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return null;
        }
    }

    ArrayList<Object[]> getAllAccounts(int id){
        try{
            if(id >= 1000) {
                return rsToArrayList(state.executeQuery("SELECT * FROM account WHERE owner = " + id));
            }else{
                return rsToArrayList(state.executeQuery("SELECT * FROM account WHERE banker_id =" + id));
            }
        }catch(SQLException e){
            System.err.println("Fehler beim Ausführen des SQL-Statements.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return null;
        }
    }

    ArrayList<Object[]> getAllRequests(int id){
        try{
            return rsToArrayList(state.executeQuery("SELECT * FROM requests WHERE customer_id ='" + id + "' OR banker_id ='" + id +"'"));
        }catch(SQLException e){
            System.err.println("Fehler beim Auslesen der Aufträge.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return null;
        }
    }

    ArrayList<Object[]> getAllTransfers(int accid){
        try{
            return rsToArrayList(state.executeQuery("SELECT * FROM transfer WHERE receiver ='" + accid + "' OR sender = '" + accid +"'"));
        }catch(SQLException e){
            System.err.println("Fehler beim Auslesen der Überweisungen.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return null;
        }
    }


    //inserting functions
    int insertAccount(Konto account){
        try {
            //return number of inserted rows
            return state.executeUpdate("INSERT INTO account(type, balance, dispo, transfer_limit, owner, banker_id) " +
                    "VALUES(" + account.type + "," + account.dispo + "," + account.balance + "," + account.transferlimit + "," + account.owner.getUid() + "," + account.banker.getUid() + ");");
        }catch(SQLException e){
            System.err.println("Fehler beim Einfügen der neuen Benutzer in die Datenbank.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return 0;
        }
    }

    int insertPerson(Person person){
        try {
            if(person.getClass().toString().contains("Banker")){
                return state.executeUpdate("INSERT INTO banker(banker_id, name, prename, birthdate, zip, city, address) " +
                        "VALUES(" + person.getUid() + "," + person.name + "," + person.preName + "," + person.birthDate + "," + person.zip + "," + person.city + "," + person.address + ")");
            }else{
                return state.executeUpdate("INSERT INTO customer(customer_id, name, prename, birthdate, zip, city, address) " +
                        "VALUES(" + person.getUid() + "," + person.name + "," + person.preName + "," + person.birthDate + "," + person.zip + "," + person.city + "," + person.address + ")");
            }
        }catch(SQLException e){
            System.err.println("Fehler beim Einfügen der neuen Benutzer in die Datenbank.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return 0;
        }
    }

    int createRequest(String description, int accid, int customer, int banker){
        return 0;
    }

    int insertTransfer(double amount, int sender, int receiver){
        return 0;
    }

    //Aktualisierungen hinzufügen
    int updatePerson(){
        return 0;
    }

    int updateAccount(){
        return 0;
    }

    int approveRequest(int order_id){
        return 0;
    }
}