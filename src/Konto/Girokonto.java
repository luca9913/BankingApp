package Konto;

import Database.ProdBase;
import Person.Banker;
import Person.Customer;

public class Girokonto extends Konto{
    double balance;

    public Girokonto(int id, Banker banker, Customer owner, ProdBase data) {
        super("Girokonto", id, banker, owner, data);
    }


    /*Funktion, um zu überweisen
    private void transfer(Girokonto toAcc, double sum){
        toAcc.kontostand += sum;
        kontostand -= sum;
    }*/

    //Funktion um einzuzahlen


    //Funktion um auszuzahlen


    //Funktion, um alle Konten in der GUI zu aktualisieren
    private void syncAccounts(){

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