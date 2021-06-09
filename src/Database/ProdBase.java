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
                return state.executeUpdate("INSERT INTO banker(prename, name, birthdate, zip, city, address) " +
                        "VALUES(" + person.preName + "," + person.name + "," + person.birthDate + "," + person.zip + "," + person.city + "," + person.address + ")");
            }else{
                return state.executeUpdate("INSERT INTO customer(prename, name, birthdate, zip, city, address) " +
                        "VALUES(" + person.preName + "," + person.name + "," + person.birthDate + "," + person.zip + "," + person.city + "," + person.address + ")");
            }
        }catch(SQLException e){
            System.err.println("Fehler beim Einfügen des neuen Benutzers in die Datenbank.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return 0;
        }
    }

    int createRequest(String description, int accid, int customer, int banker){
        try{
            return state.executeUpdate("INSERT INTO requests(description, account_id, customer_id, banker_id) " +
                    "VALUES(" + description + "," + accid + "," + customer + "," + banker + ");");
        }catch(SQLException e){
            System.err.println("Fehler beim erstellen der Anfrage in der Datenbank.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return 0;
        }
    }

    int insertTransfer(double amount, int sender, int receiver){
        try{
            return state.executeUpdate("INSERT INTO transfer(amount, sender, receiver) " +
                    "VALUES(" + amount + "," + sender + "," + receiver + ");");
        }catch(SQLException e){
            System.err.println("Fehler beim übertragen der Überweisung in die Datenbank.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return 0;
        }
    }

    //Aktualisierungen hinzufügen
    int updatePerson(Person person){
        try {
            if (person.id < 1000) {
                return state.executeUpdate("UPDATE banker SET prename ='" + person.name + "', name ='" + person.preName + "', birthdate ='" + person.birthDate + "', zip ='" + person.zip + "', city ='" + person.city + "', address ='" + person.address + "' WHERE banker_id = " + person.id);
            } else {
                return state.executeUpdate("UPDATE customer SET prename ='" + person.name + "', name ='" + person.preName + "', birthdate ='" + person.birthDate + "', zip ='" + person.zip + "', city ='" + person.city + "', address ='" + person.address + "' WHERE customer_id = " + person.id);
            }
        }catch(SQLException e){
            System.err.println("Fehler beim Aktualisieren der Kundendaten in der Datenbank.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return 0;
        }
    }

    int updateBalance(int id, double balance){
        try {
            return state.executeUpdate("UPDATE account SET balance ='" + balance + "' WHERE account_id = " + id);
        }catch(SQLException e){
            System.err.println("Fehler beim Aktualisieren des Kontostandes in der Datenbank.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return 0;
        }
    }

    int updateAccountData(Konto account){
        try {
            return state.executeUpdate("UPDATE account SET dispo ='" + account.dispo + "', transfer_limit ='" + account.transferlimit + "', owner ='" + account.owner + "', banker_id ='" + account.banker + "' WHERE account_id = " + account.id);
        }catch(SQLException e){
            System.err.println("Fehler beim Aktualisieren des Kontodaten in der Datenbank.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return 0;
        }
    }

    int approveRequest(int request_id){
        try {
            return state.executeUpdate("UPDATE requests SET status = 1 WHERE request_id = " + request_id);
        }catch(SQLException e){
            System.err.println("Fehler beim Aktualisieren des Anfrage-Status in der Datenbank.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return 0;
        }
    }
}