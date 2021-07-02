package Konto;

import Database.ProdBase;
import Person.Banker;
import Person.Customer;

/**
 * Hier Text einfügen
 */
public class Festgeldkonto extends Konto{

    /**
     * Hier Text einfügen
     * @param id
     * @param banker
     * @param owner
     * @param data
     */
    public Festgeldkonto(int id, Banker banker, Customer owner, ProdBase data) {

        super("Festgeldkonto", id, banker, owner, data);
        setDispo(0);
        setLimit(10000);
    }
}