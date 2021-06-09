package Konto;

public class Girokonto extends Konto{
    double balance;
    public Girokonto(){
        balance = 0;
    };

    //Funktion, um alle Konten in der GUI zu aktualisieren
    private void syncAccounts(){

    }

    //Funktion, um zu überweisen
    private void transfer(int toAcc, double sum){

    }

    //Funktion um einzuzahlen
    private void zahlungsEingang(double sum){
        balance += sum;
    }

    //Funktion um auszuzahlen
    private void zahlungsAusgang(double sum){
        balance -= sum;
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