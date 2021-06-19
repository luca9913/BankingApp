package Konto;

import Database.ProdBase;
import Person.Banker;
import Person.Customer;

public class Depot extends Konto{

    public Depot(String type, int id, Banker banker, Customer owner) {
        super(type, id, banker, owner);
    }
}