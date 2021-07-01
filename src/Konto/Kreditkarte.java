package Konto;

import Database.ProdBase;
import Person.Banker;
import Person.Customer;

public class Kreditkarte extends Konto {
    double debit;

    public Kreditkarte(int id, Banker banker, Customer owner, ProdBase data) {
        super("Kreditkarte", id, banker, owner, data);
        setDispo(-2500);
        setLimit(15000);
    }

    //Bank bezahlen --> debit auf 0
    private void payBank(double amount) {
        debit -= amount;
    }

    //Sachen kaufen --> debit auf x
    private void buyStuff(double amount) {
        debit += amount;
    }
}

