package Konto;

import Database.ProdBase;
import Person.Banker;
import Person.Customer;

public class Depot extends Konto{

    public Depot(ProdBase database, String type, int id, Banker banker, Customer owner) {
        super(database, type, id, banker, owner);
    }
}