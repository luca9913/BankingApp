package Person;

import java.text.DateFormat;
import java.util.ArrayList;
import Database.ProdBase;
import java.util.Date;

public class Customer extends Person{

    //Konstruktor
    public Customer(int uid,int id){
        super(uid,id);
    }

    public Customer(String[] pdata){
        if(pdata.length == 6) {
            this.preName = pdata[0];
            this.name = pdata[1];
            this.birthDate = pdata[2];
            this.address = pdata[3];
            this.zip = Integer.parseInt(pdata[4]);
            this.city = pdata[5];
        }else if(pdata.length > 6){
            System.err.println("Zu viele Daten angegeben.");
        }else if(pdata.length < 6){
            System.err.println("Zu wenige Daten angegeben.");
        }
    }

    ProdBase data;
    ArrayList<Object[]> allAccounts;

    //erstellt Liste mit Konten des Kunden und gibt diese zurück
    public ArrayList<Object[]> getAllAccounts(){
        allAccounts = data.getAllAccounts(id);
        return allAccounts;
    }






    //Funktion, um alle Konten in der GUI zu aktualisieren
    private void syncAccounts(){

    }

    //Funktion, um zu überweisen
    private void transfer(int fromAcc, int toAcc, double sum){

    }

    //Funktion, um die aktuellen Kontostände anzuzeigen
    //Offene Frage: Alle Kontostände oder nur der Kontostand eines Kontos
    private void showFinances(int accID){

    }

    //Zeigt Kontobewegungen (z.B. letzte Überweisungen)
    //Offene Frage: Alle Kontobewegungen oder nur die Kontobewegungen eines Kontos
    private void showAccMovement(int accID){

    }

    //Ändern des eigenen Datensatzes
    private void changeMyData(String name, String preName, int zip, String address){
        this.name = name;
        this.preName = preName;
        this.zip = zip;
        this.address = address;
    }
}
