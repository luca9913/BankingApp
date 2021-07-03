package Konto;

import Database.ProdBase;
import Person.Banker;
import Person.Customer;

/**
 * Die Klasse "Depot" ist ein spezifiziertes Kontoobjekt und erbt von der Klasse "Konto".
 */
public class Depot extends Konto{

    /**
     * Standardkonstruktor der Klasse "Depot".
     * @param id Parameter für die ID des Kontoobjektes.
     * @param banker Parameter für das Objekt des zuständigen Bankers des Kontoobjektes.
     * @param owner Parameter für das Objekt des Kunden, welcher das Kontoobjekt besitzt.
     * @param data Parameter für die produktive Datenbank, die übergeben wird.
     */
    public Depot(int id, Banker banker, Customer owner, ProdBase data) {
        super("Depot", id, banker, owner, data);
        setDispo(0);
        setLimit(10000);
    }
}