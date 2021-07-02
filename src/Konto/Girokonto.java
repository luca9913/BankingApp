package Konto;

import Database.ProdBase;
import Person.Banker;
import Person.Customer;

/**
 * Hier Text einfügen
 */
public class Girokonto extends Konto{
    double balance;

    /**
     * Hier Text einfügen
     * @param id
     * @param banker
     * @param owner
     * @param data
     */
    public Girokonto(int id, Banker banker, Customer owner, ProdBase data) {
        super("Girokonto", id, banker, owner, data);
        setDispo(-500);
        setLimit(5000);
    }
}