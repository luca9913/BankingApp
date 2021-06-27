package Database;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

import Konto.Konto;
import Person.Person;

/**
 * Dient dem Zugriff und dem Ausführen von Befehlen in der Datenbank, die die Kundendaten, Überweisungen und sonstige "Produktionsdaten"
 * enthält. Die Datenbank ist in der Datei "/src/data/production.db" gespeichert.
 */
public class ProdBase extends Database {
    /**Pfad zur Datei, in der die Datenbank gespeichert ist.*/
    static Path path = Paths.get("");
    /**Datenbank-Verbindung aus dem Paket java.sql
     * Link:  java.sql.Connection */
    Connection conn;
    /**Datenbank-Statement zur Ausführung von Abfragen
     * Link:  java.sql.Statement*/
    Statement state;
    /**Speicherung der Rückgabe einer Datenbank-Abfrage
     * Link:  java.sql.ResultSet*/
    ResultSet result;

    /**
     * Initialisiert die Datenbank-Verbindung zur Datenbank in der Datei production.db und weist dem Feld 'state' ein SQL-Statement zu
     * das für Abfragen verwendet werden kann. Der Konstruktor ist nicht von außen aufrufbar. Er kann nur einmal über die Methode initialize() aufgerufen werden.
     */
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

    /**
     * Funktion zum Herstellen einer Verbindung zur der Datenbank-Datei.
     * @return ProdBase-Objekt, mit dem gearbeitet werden kann oder 'null', wenn bereits eine Verbindung existiert.
     */
    public static ProdBase initialize(){
        if(path.toString().isEmpty()){ //eventuell entfernen, da mehrere Verbindungen benötigt werden
            ProdBase prod = new ProdBase();
            return prod;
        }else{
            System.err.println("Stammdatenbank wurde bereits erstellt. Es ist nur eine Instanz erlaubt!");
            return null;
        }
    }

    /**
     * Implementierung der abstrakten Klasse zur Abfrage beliebiger SQLite-Statements.
     * @param sql Ein gültiges <a href="https://sqlite.org/index.html">SQLite</a>-Statement
     * @return Eine Array-Liste mit einem Eintrag pro gefundener, gelöschter oder veränderter Zeile. Die Elemente der Array-Liste sind Object-Arrays mit einem Wert für jede Spalte der Tabelle.
     */
    public ArrayList<Object[]> executeCustomQuery(String sql){
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

    //retrieving functions TODO: arrayList zu Object[] ändern
    /**
     * Liest die Zeile mit dem entsprechenden Primär-Schlüssel in der angegeben Tabelle aus.
     * @param id Primär-Schlüssel der jeweiligen Tabelle (customer_id, banker_id, usw.)
     * @param table Tabellenname aus der Datenbank production.db
     * @return Ein Object-Array mit dem entsprechenden Wert aus jeder Spalte der gefundenen Zeile, 'null' wenn keine Zeile mit der entsprechenden id gefunden wurde.
     */
    public ArrayList<Object[]> getData(int id, String table){
        try {
            String idname = table.concat("_id"); //create the primary key name out of the tablename-Argument
            return rsToArrayList(state.executeQuery("SELECT * FROM " + table + " WHERE " + idname + " = " + id)); //return the ArrayList created from the ResultSet gotten by the SQLite-Statement
        }catch(SQLException e){ //catch an SQL-Exception
            //print a custom error message and the Excpetion stacktrace to the error log
            System.err.println("Fehler beim Auslesen der Daten aus Tabelle: " + table);
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Liest alle Bankkoten aus, die einem Kunden gehören oder von einem bestimmten Bänker verwaltet werden.
     * @param id ID-Nummer des Kunden bzw. des Bänkers (Fremd-Schlüssel in der Tabelle 'account')
     * @return Array-Liste mit einem Object-Array für jedes gefundene Konto + einem Object-Array an Stelle 0, der die Spaltennamen enthält.
     */
    public ArrayList<Object[]> getAllAccounts(int id){
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

    /**
     * Liest alle Freigabeaufträge aus, die vom Kunden erstellt wurden bzw. vom Bänker bearbeitet werden.
     * @param id ID-Nummer des Kunden bzw. des Bänkers (Fremd-Schlüssel in der Tabelle 'requests')
     * @return Array-Liste mit einem Object-Array an Stelle 0, der die Spaltennamen enthält und einem Object-Array für jeden gefundenen Auftrag.
     */
    public ArrayList<Object[]> getAllRequests(int id){
        try{
            return rsToArrayList(state.executeQuery("SELECT * FROM request WHERE customer_id ='" + id + "' OR banker_id ='" + id +"'"));
        }catch(SQLException e){
            System.err.println("Fehler beim Auslesen der Aufträge.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Liest alle Überweisungen aus, in denen das angegebene Konto entweder der Sender oder der Empfänger ist.
     * @param accid Die Konto-Nummer, für die die Überweisungen ausgelesen werden sollen.
     * @return Array-Liste mit einem Object-Array an Stelle 0, der die Spaltennamen enthält und einem Object-Array für jede gefundene Überweisung.
     */
    public ArrayList<Object[]> getAllTransfers(int accid){
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
    /**
     * Fügt die Felder eines Konto-Objektes als Werte in die entsprechenden Spalte der Tabelle 'account' in der Datenbank production.db ein.
     * @param account Konto-Objekt, das eingefügt werden soll
     * @return 'true', wenn das Einfügen erfolgreich war. 'false', wenn nicht.
     */
    public boolean insertAccount(Konto account){
        try {
            //return number of inserted rows
            return returnFunction(state.executeUpdate("INSERT INTO account(type, balance, dispo, transfer_limit, owner, banker_id) " +
                    "VALUES(" + account.type + "," + account.dispo + "," + account.balance + "," + account.transferlimit + "," + account.owner.id + "," + account.banker.id + ");"));
        }catch(SQLException e){
            System.err.println("Fehler beim Einfügen der neuen Benutzer in die Datenbank.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Fügt die Felder eines Person-Objektes als Werte in die entsprechende Spalte der Tabelle 'customer' oder 'banker' in der Datenbank production.db ein.
     * @param person Person-Objekt, das eingefügt werden soll.
     * @return 'true', wenn das Einfügen erfolgreich war. 'false', wenn nicht.
     */
    public boolean insertPerson(Person person){
        try {
            int rows;
            if(person.getClass().toString().contains("Banker")){
                rows =  state.executeUpdate("INSERT INTO banker(prename, name, birthdate, zip, city, address) " +
                        "VALUES(" + person.preName + "," + person.name + "," + person.birthDate + "," + person.zip + "," + person.city + "," + person.address + ")");
            }else{
                rows = state.executeUpdate("INSERT INTO customer(prename, name, birthdate, zip, city, address) " +
                        "VALUES(" + person.preName + "," + person.name + "," + person.birthDate + "," + person.zip + "," + person.city + "," + person.address + ")");
            }
            return returnFunction(rows);
        }catch(SQLException e){
            System.err.println("Fehler beim Einfügen des neuen Benutzers in die Datenbank.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Fügt einen neuen Freigabeauftrag in die Tabelle 'requests' der Datenbank production.db ein.
     * @param key Feld, das geändert werden soll (Bsp.: Dispo, Überweisungs-Limit, ...)
     * @param value Neuer Wert für das angegebene Feld
     * @param accid Konto-Nummer des betroffenen Kontos
     * @param customer ID des Kunden
     * @param banker ID des betreuenden Bankers
     * @return
     */
    public boolean createRequest(String key, double value, int accid, int customer, int banker){
        try{
            return returnFunction(state.executeUpdate("INSERT INTO request(customer_id, account_id, banker_id, key, value_new) " +
                    "VALUES(" + customer + "," + accid + "," + "," + banker + key + "," + value + ");"));
        }catch(SQLException e){
            System.err.println("Fehler beim erstellen der Anfrage in der Datenbank.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Fügt eine neue Überweisung in die Tabelle 'transfer' in der Datenbank production.db ein
     * @param amount Betrag, der überwiesen wird
     * @param sender Konto-Nummer des Absenders
     * @param receiver Konto-Nummer des Empfängers
     * @return 'true', wenn das Einfügen erfolgreich war. 'false', wenn nicht.
     */
    public boolean insertTransfer(double amount, int sender, int receiver, String usage, String date){
        try{
            return returnFunction(state.executeUpdate("INSERT INTO transfer(amount, sender, receiver, usage, date) " +
                    "VALUES(" + amount + "," + sender + "," + receiver + "," + usage + "," + date + ");"));
        }catch(SQLException e){
            System.err.println("Fehler beim übertragen der Überweisung in die Datenbank.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return false;
        }
    }

    //updating functions
    /**Aktualisiert die Daten einer Person (Banker oder Kunde) in der entsprechenden Tabelle der Datenbank production.db.
     * @param person Objekt der Klasse Banker oder Customer, das die Daten enthält
     * @return 'true', wenn das Aktualisieren erfolgreich war. 'false', wenn nicht.
     */
    public boolean updatePerson(Person person){
        try {
            if (person.id < 1000) {
                return returnFunction(state.executeUpdate("UPDATE banker SET prename ='" + person.name + "', name ='" + person.preName + "', birthdate ='" + person.birthDate + "', zip ='" + person.zip + "', city ='" + person.city + "', address ='" + person.address + "' WHERE banker_id = " + person.id));
            } else {
                return returnFunction(state.executeUpdate("UPDATE customer SET prename ='" + person.name + "', name ='" + person.preName + "', birthdate ='" + person.birthDate + "', zip ='" + person.zip + "', city ='" + person.city + "', address ='" + person.address + "' WHERE customer_id = " + person.id));
            }
        }catch(SQLException e){
            System.err.println("Fehler beim Aktualisieren der Kundendaten in der Datenbank.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return false;
        }
    }

    /**Verändert den Kontostand eines Kontos.
     * @param id Konto-ID
     * @param balance neuer Kontostand
     * @return 'true', wenn das Aktualisieren erfolgreich war. 'false', wenn nicht.
     */
    public boolean updateBalance(int id, double balance){
        try {
            return returnFunction(state.executeUpdate("UPDATE account SET balance ='" + balance + "' WHERE account_id = " + id));
        }catch(SQLException e){
            System.err.println("Fehler beim Aktualisieren des Kontostandes in der Datenbank.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return false;
        }
    }

    /**(Ent-)sperrt ein Konto.
     * @param accid Konto-ID
     * @param status Kontostatus - 0: offen, 1: gesperrt
     * @return 'true', wenn das Aktualisieren erfolgreich war. 'false', wenn nicht.
     */
    public boolean updateAccountBlockage(int accid, int status){
        try{
            return returnFunction(state.executeUpdate("UPDATE account SET locked =" + status));
        }catch(SQLException e){
            System.err.println("Fehler beim Aktualisieren des Kontostatus in der Datenbank.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return false;
        }
    }

    /**Aktualisiert alle Daten eines Kontos entsprechend der Felder eines Konto-Objektes.
     * @param account Konto-Objekt, das die Daten enthält
     * @return 'true', wenn das Aktualisieren erfolgreich war. 'false', wenn nicht.
     */
   public boolean updateAccountData(Konto account){
        try {
            return returnFunction(state.executeUpdate("UPDATE account SET dispo ='" + account.dispo + "', transfer_limit ='" + account.transferlimit + "', owner ='" + account.owner + "', banker_id ='" + account.banker + "', locked ='" + account.locked + "' WHERE account_id = " + account.id));
        }catch(SQLException e){
            System.err.println("Fehler beim Aktualisieren des Kontodaten in der Datenbank.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return false;
        }
    }

    /**Aktualisiert den Status eines Freigabe-Auftrages.
     * @param request_id Kennnummer des Freigabe-Auftrages
     * @param status neuer Status - 1: Genehmigt, -1: Abgelehnt
     * @return 'true', wenn das Aktualisieren erfolgreich war. 'false', wenn nicht.
     */
    public boolean updateRequest(int request_id, int status){
        try {
            return returnFunction(state.executeUpdate("UPDATE request SET status = " + status + " WHERE request_id = " + request_id));
        }catch(SQLException e){
            System.err.println("Fehler beim Aktualisieren des Anfrage-Status in der Datenbank.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return false;
        }
    }

    //deleting functions

    /**
     * @param id
     * @return
     */
    public boolean deletePerson(int id){
        try{
            int rows;
            if(id < 1000){
                rows = state.executeUpdate("DELETE FROM banker WHERE banker_id =" + id);
            }else{
                rows = state.executeUpdate("DELETE FROM customer WHERE customer_id =" + id);
            }
            return returnFunction(rows);
        }catch(SQLException e){
            System.err.println("Fehler beim Löschen der Person aus der Datenbank.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAccount(int id){
        try{
            return returnFunction(state.executeUpdate("DELETE FROM account WHERE account_id =" + id));
        }catch(SQLException e){
            System.err.println("Fehler beim Löschen des Kontos aus der Datenbank.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return false;
        }
    }

    //helping functions
    boolean returnFunction(int rows){
        if(rows >= 1){
            return true;
        }else{
            return false;
        }
    }
}