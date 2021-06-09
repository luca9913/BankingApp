package Konto;

import Database.ProdBase;
import Person.Banker;
import Person.Customer;

public class Kreditkarte extends Konto {
    double debit;

    public Kreditkarte(ProdBase database, String type, int id, Banker banker, Customer owner) {
        super(database, type, id, banker, owner);
    }

    //public Kreditkarte(){
    //    debit = 0;
    //}
    //Bank bezahlen --> debit auf 0
    private void payBank(double amount) {
        debit -= amount;
    }

    //Sachen kaufen --> debit auf x
    private void buyStuff(double amount) {
        debit += amount;
    }
}

