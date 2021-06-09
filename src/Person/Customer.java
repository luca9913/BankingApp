package Person;

import java.util.Date;

public class Customer extends Person{

    //Konstruktor
    public Customer(int uid,int id){
        super(uid,id);
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
