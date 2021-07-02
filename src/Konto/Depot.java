package Konto;

import Database.ProdBase;
import Person.Banker;
import Person.Customer;

/**
 * Hier Text einfügen
 */
public class Depot extends Konto{

    /**
     * Hier Text einfügen
     * @param id
     * @param banker
     * @param owner
     * @param data
     */
    public Depot(int id, Banker banker, Customer owner, ProdBase data) {
        super("Depot", id, banker, owner, data);
        setDispo(0);
        setLimit(10000);
    }
}