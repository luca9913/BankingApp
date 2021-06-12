package Person;

import java.util.Date;

public class Banker extends Person {

    //Konstruktor
    public Banker(int uid,int id){
        super(uid, id);
    }

    //Funktion, um alle Konten in der GUI zu aktualisieren
    private void syncAccounts(){

    }

    //Funktion, um einen Kunden anzuzeigen
    private void showCustomer(int customerID){

    }

    //Funktion, um die negativen Salden eines Kunden anzuzeigen
    //Offene Frage: Alle Negativen Salden oder nur die negativen Salden eines Kunden
    private void showBalance(int customerID){

    }

    //Zeigt Kontobewegungen (z.B. letzte Überweisungen)
    //Offene Frage: Bewegungen aller Konten oder nur die Kontobewegungen eines Kunden / Kontos?
    private void showAccMovement(int accID){

    }

    //Ändern des Datensatzes einer Person
    private void changePerson(String id, String name, String preName, Date birthDate, int zip, String address){
        this.name = name;
        this.preName = preName;
        this.zip = zip;
        this.address = address;
    }

}
