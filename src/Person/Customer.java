package Person;

import java.util.Date;

public class Customer extends Person{

    //Konstruktor
    public Customer(String id, String name, String preName, Date birthDate, int zip, String address){
        super(id, name, preName, birthDate, zip, address);
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

}
