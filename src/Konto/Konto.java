package Konto;

import Person.*;
import Database.ProdBase;
import java.util.ArrayList;

/**
 * Die abstrakte Klasse "Konto" ist die Vaterklasse für die Unterklassen der Kontotypen.
 */
public abstract class Konto{

    private int id;
    private String type;
    private double balance = 0;
    private double dispo;
    private int transferlimit;
    private int locked = 0;
    private Customer owner;
    private Banker banker;
    private ProdBase data;
    ArrayList<Object[]> transferList;

    /**
     * Standardkonstruktor der Klasse "Person".
     * @param type Parameter des Konstruktors.
     * @param id Parameter des Konstruktors.
     * @param banker Parameter des Konstruktors.
     * @param owner Parameter des Konstruktors.
     * @param data Parameter des Konstruktors.
     */
    public Konto(String type, int id, Banker banker, Customer owner, ProdBase data)
    {
        if(banker == null || owner == null)
        {
            throw new IllegalArgumentException("Banker und/oder Customer dürfen nicht null sein.");
        }
        this.banker = banker;
        this.owner = owner;
        this.id = id;
        this.type = type;
        this.data = data;
        transferList = data.getAllTransfers(id);
    }

    /**
     * Diese Methode ist der Setter für die Konto-ID.
     * @param id Der Parameter enthält die ID des Kontoobjektes,
     * welche geändert werden soll.
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Diese Methode ist der Setter für den Kontostand.
     * @param balance Der Parameter enthält den Kontostand des Kontoobjektes,
     * welcher geändert werden soll.
     */
    public void setBalance(double balance){
        this.balance = balance;
    }

    /**
     * Diese Methode ist der Setter für den Disporahmen.
     * @param dispo Der Parameter enthält den alten Disporahmen des Kontoobjektes,
     * welcher geändert werden soll.
     */
    public void setDispo(double dispo){
        this.dispo = dispo;
    }

    /**
     * Diese Methode ist der Setter für den Überweisungsrahmen.
     * @param limit Der Parameter enthält den alten Überweisungsrahmen des Kontoobjektes,
     * welcher geändert werden soll.
     */
    public void setLimit(int limit){
        this.transferlimit = limit;
    }

    /**
     * Diese Methode ist der Setter für den Status des Kontoobjektes.
     * @param status Der Parameter enthält den Status, welcher für das Konto-Objekt
     * übernommen werden soll.
     */
    public void setStatus(int status){
        if(status <= 1 && status >= -1) {
            this.locked = status;
        }
    }

    /**
     * Dieser Getter gibt die ID des Kontoobjektes zurück.
     * @return Die ID wird als Integer zurückgegeben.
     */
    public Integer getId(){
        return this.id;
    }

    /**
     * Dieser Getter gibt die Kontoart des Kontoobjektes zurück.
     * @return Die Kontoart wird als String zurückgegeben.
     */
    public String getType(){
        return this.type;
    }

    /**
     * Dieser Getter gibt den Kontostand des Kontoobjektes zurück.
     * @return Der Kontostand wird als Double zurückgegeben.
     */
    public Double getBalance(){
        return this.balance;
    }

    /**
     * Dieser Getter gibt den Disporahmen des Kontoobjektes zurück.
     * @return Der Disporahmen wird als Double zurückgegeben.
     */
    public Double getDispo(){
        return this.dispo;
    }

    /**
     * Dieser Getter gibt den Überweisungsrahmen des Kontoobjektes zurück.
     * @return Der Überweisungsrahmen wird als Integer zurückgegeben.
     */
    public int getLimit(){
        return this.transferlimit;
    }

    /**
     * Dieser Getter gibt den Status des Kontoobjektes zurück.
     * @return Der Status wird als Integer zurückgegeben.
     */
    public Integer getStatus(){
        return this.locked;
    }

    /**
     * Dieser Getter gibt das Kunden-Objekt des Besitzers des Kontoobjektes zurück.
     * @return Es wird das Kunde-Objekt zurückgegeben.
     */
    public Customer getOwner(){
        return this.owner;
    }

    /**
     * Dieser Getter gibt das zugeordnete Banker-Objekt des Kontoobjektes zurück.
     * @return Es wird das Banker-Objekt zurückgegeben.
     */
    public Banker getBanker(){
        return this.banker;
    }

    /**
     * Diese Methode gibt eine Liste mit allen Kontoumsätzen eines Kontoobjektes zurück.
     * @return Es wird eine Objektliste zurückgegeben.
     */
    public ArrayList<Object[]> getAllTranfers(){
        transferList = data.getAllTransfers(id);
        return transferList;
    }

    /**
     * Diese Methode gibt Daten zu einem konkreten Kontoumsatz zurück.
     * @return Die Daten werden als Objektarray zurückgegeben.
     */
    public Object[] getData(){
        return data.getData(id, "transfer").get(1);
    }

    /**
     * Diese Methode prüft, ob eine Überweisung getätigt werden kann, also ob das Konto gedeckt ist.
     * @param id Dieser Parameter enthält die ID des Kontoobjektes, bei dem die Deckung geprüft werden soll.
     * @param amount Dieser Parameter enthält den Betrag der Überweisung.
     * @param usage Dieser Parameter enthält den Überweisungszweck.
     * @param date Dieser Parameter enthält das Datum der Überweisung.
     * @return Der Rückgabewert ist ein boolean. Ist er "true, dann war die überweisung erfolgreich, wird
     * "false" zurückgegeben, dann war die Überweisung fehlerhaft.
     */
    public boolean transfer(int id, double amount, String usage, String date){
        if(transferlimit < amount)
        {
            throw new IllegalArgumentException("Transfer limit ueberschritten");
        }else
        {
            updateBalance(amount * (-1));
            data.updateAccountData(this);
            return data.insertTransfer(amount, id, id, usage, date);
        }
    }

    /**
     * Diese Methode passt den Wert des Kontostandes an und wird zum einzahlen genutzt.
     * @param amount Der Parameter gibt an, um wie viel der Kontostand des kontoobjektes steigen soll.
     */
    public void updateBalance(Double amount){
        balance += amount;
    }
}