package Konto;

import Database.ProdBase;
import Person.Banker;
import Person.Customer;

/**
 * Die Klasse "Girokonto" ist ein spezifiziertes Kontoobjekt und erbt von der Klasse "Konto".
 */
public class Girokonto extends Konto{

    /**
     * Standardkonstruktor der Klasse "Girokonto".
     * @param id Parameter für die ID des Kontoobjektes.
     * @param banker Parameter für das Objekt des zuständigen Bankers des Kontoobjektes.
     * @param owner Parameter für das Objekt des Kunden, welcher das Kontoobjekt besitzt.
     * @param data Parameter für die produktive Datenbank, die übergeben wird.
     */
    public Girokonto(int id, Banker banker, Customer owner, ProdBase data) {
        super("Girokonto", id, banker, owner, data);
        setDispo(-500);
        setLimit(5000);
    }
}