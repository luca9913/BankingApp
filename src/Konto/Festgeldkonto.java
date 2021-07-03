package Konto;

import Database.ProdBase;
import Person.Banker;
import Person.Customer;

/**
 * Die Klasse "Festgeldkonto" ist ein spezifiziertes Kontoobjekt und erbt von der Klasse "Konto".
 */
public class Festgeldkonto extends Konto{

    /**
     * Standardkonstruktor der Klasse "Festgeldkonto".
     * @param id Parameter für die ID des Kontoobjektes.
     * @param banker Parameter für das Objekt des zuständigen Bankers des Kontoobjektes.
     * @param owner Parameter für das Objekt des Kunden, welcher das Kontoobjekt besitzt.
     * @param data Parameter für die produktive Datenbank, die übergeben wird.
     */
    public Festgeldkonto(int id, Banker banker, Customer owner, ProdBase data) {

        super("Festgeldkonto", id, banker, owner, data);
        setDispo(0);
        setLimit(10000);
    }
}