package Konto;

import Database.ProdBase;
import Person.Banker;
import Person.Customer;

public class Festgeldkonto extends Konto{

    public Festgeldkonto(int id, Banker banker, Customer owner, ProdBase data) {

        super("Festgeldkonto", id, banker, owner, data);
        setDispo(0);
        setLimit(10000);
    }

    //Funktion, um alle Konten in der GUI zu aktualisieren
    private void syncAccounts(){

    }

    //Funktion, um einzuzahlen
    /*private void deposit(double sum){
        kontostand += sum;
    }*/

    //Funktion, um die aktuellen Kontostände anzuzeigen
    //Offene Frage: Alle Kontostände oder nur der Kontostand eines Kontos
    private void showFinances(int accID){

    }

    //Zeigt Kontobewegungen (z.B. letzte Einzahlungen)
    //Offene Frage: Alle Kontobewegungen oder nur die Kontobewegungen eines Kontos
    private void showAccMovement(int accID){

    }

}