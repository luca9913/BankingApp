package Konto;

import Database.ProdBase;
import Person.Banker;
import Person.Customer;

/**
 * Hier Text einfügen
 */
public class Kreditkarte extends Konto {

    /**
     * Hier Text einfügen
     * @param id
     * @param banker
     * @param owner
     * @param data
     */
    public Kreditkarte(int id, Banker banker, Customer owner, ProdBase data) {
        super("Kreditkarte", id, banker, owner, data);
        setDispo(-2500);
        setLimit(15000);
    }
}

