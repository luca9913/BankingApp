package Konto;

import Database.ProdBase;
import Person.Banker;
import Person.Customer;

public class Depot extends Konto{

    public Depot(int id, Banker banker, Customer owner) {
        super("Depot", id, banker, owner);
    }
}