package Person;

import java.util.Date;

public class Banker extends Person{

    //Konstruktor
    public Banker(String id, String name, String preName, Date birthDate, int zip, String address){
        super(id, name, preName, birthDate, zip, address);
    }

    //Funktion, um alle Konten in der GUI zu aktualisieren
    private void syncAccounts(){

    }

    //Funktion, um einen Kunden anzuzeigen
    private void showCustomer(int customerID){

    }

    //Funktion, um die aktuellen Kontostände anzuzeigen
    //Offene Frage: Alle Kontostände oder nur der Kontostand eines Kontos
    private void showFinances(int accID){

    }

    //Zeigt Kontobewegungen (z.B. letzte Überweisungen)
    //Offene Frage: Alle Kontobewegungen oder nur die Kontobewegungen eines Kontos
    private void showAccMovement(int accID){

    }

}
