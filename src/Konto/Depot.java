package Konto;

import Database.ProdBase;
import Person.Banker;
import Person.Customer;

public class Depot extends Konto{

    public Depot(int id, Banker banker, Customer owner, ProdBase data) {
        super("Depot", id, banker, owner, data);
        setDispo(0);
        setLimit(10000);
    }
}