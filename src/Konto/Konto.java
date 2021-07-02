package Konto;

import Person.*;
import Database.ProdBase;
import java.util.ArrayList;

/**
 * Hier Text einfügen
 */
public abstract class Konto{
    private int id; //KontoID
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
     * Hier Text einfügen
     * @param type
     * @param id
     * @param banker
     * @param owner
     * @param data
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
     * Hier Text einfügen
     * @param id
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Hier Text einfügen
     * @param balance
     */
    public void setBalance(double balance){
        this.balance = balance;
    }

    /**
     * Hier Text einfügen
     * @param dispo
     */
    public void setDispo(double dispo){
        this.dispo = dispo;
    }

    /**
     * Hier Text einfügen
     * @param limit
     */
    public void setLimit(int limit){
        this.transferlimit = limit;
    }

    /**
     * Hier Text einfügen
     * @param status
     */
    public void setStatus(int status){
        if(status <= 1 && status >= -1) {
            this.locked = status;
        }
    }

    /**
     * Hier Text einfügen
     * @return
     */
    public Integer getId(){
        return this.id;
    }

    /**
     * Hier Text einfügen
     * @return
     */
    public String getType(){
        return this.type;
    }

    /**
     * Hier Text einfügen
     * @return
     */
    public Double getBalance(){
        return this.balance;
    }

    /**
     * Hier Text einfügen
     * @return
     */
    public Double getDispo(){
        return this.dispo;
    }

    /**
     * Hier Text einfügen
     * @return
     */
    public int getLimit(){
        return this.transferlimit;
    }

    /**
     * Hier Text einfügen
     * @return
     */
    public Integer getStatus(){
        return this.locked;
    }

    /**
     * Hier Text einfügen
     * @return
     */
    public Customer getOwner(){
        return this.owner;
    }

    /**
     * Hier Text einfügen
     * @return
     */
    public Banker getBanker(){
        return this.banker;
    }

    /**
     * Diese Methode gibt eine Liste mit allen Kontoumsätzen zurück
     * @return
     */
    public ArrayList<Object[]> getAllTranfers(){
        transferList = data.getAllTransfers(id);
        return transferList;
    }

    /**
     * //gibt Daten zu einem konkreten Umsatz zurück getData: 95
     * @param selected
     * @return
     */
    public Object[] getData(int selected){
        int transferid = (Integer) transferList.get(selected)[0];

        return data.getData(id, "transfer").get(1);
    }

    /**
     * //Überweisen auf ein anderes Konto
     * @param id
     * @param amount
     * @param usage
     * @param date
     * @return
     */
    public boolean transfer(int id, double amount, String usage, String date){
        if(transferlimit < amount)
        {
            throw new IllegalArgumentException("Transfer limit ueberschritten");
        }else
        {
            updateBalance(amount * (-1));
            data.updateAccountData(this);
            //Transfer in die Datenbank schreiben
            return data.insertTransfer(amount, id, id, usage, date);
        }
    }

    /**
     * Diese Methode ... //Funktion um einzuzahlen
     * @param amount
     */
    public void updateBalance(Double amount){
        balance += amount;
    }
}