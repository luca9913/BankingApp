package Konto;

import Database.ProdBase;
import Person.Banker;
import Person.Customer;

/**
 * Die Klasse "Kreditkarte" ist ein spezifiziertes Kontoobjekt und erbt von der Klasse "Konto".
 */
public class Kreditkarte extends Konto {

    /**
     * Standardkonstruktor der Klasse "Kreditkarte".
     * @param id Parameter für die ID des Kontoobjektes.
     * @param banker Parameter für das Objekt des zuständigen Bankers des Kontoobjektes.
     * @param owner Parameter für das Objekt des Kunden, welcher das Kontoobjekt besitzt.
     * @param data Parameter für die produktive Datenbank, die übergeben wird.
     */
    public Kreditkarte(int id, Banker banker, Customer owner, ProdBase data) {
        super("Kreditkarte", id, banker, owner, data);
        setDispo(-2500);
        setLimit(15000);
    }
}

