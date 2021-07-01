package Konto;

import Person.*;
import Database.*;
import Database.ProdBase;

import java.util.ArrayList;

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

    public void setId(int id){
        this.id = id;
    }

    public void setBalance(double balance){
        this.balance = balance;
    }

    public void setDispo(double dispo){
        this.dispo = dispo;
    }

    public void setLimit(int limit){
        this.transferlimit = limit;
    }

    public void setStatus(int status){
        if(status <= 1 && status >= -1) {
            this.locked = status;
        }
    }

    public Integer getId(){
        return this.id;
    }

    public String getType(){
        return this.type;
    }

    public Double getBalance(){
        return this.balance;
    }

    public Double getDispo(){
        return this.dispo;
    }

    public int getLimit(){
        return this.transferlimit;
    }

    public Integer getStatus(){
        return this.locked;
    }

    public Customer getOwner(){
        return this.owner;
    }

    public Banker getBanker(){
        return this.banker;
    }

    //gibt Liste aller Umsätze zurück
    public ArrayList<Object[]> getAllTranfers(){
        transferList = data.getAllTransfers(id);
        return transferList;
    }

    //gibt Daten zu einem konkreten Umsatz zurück getData: 95
    public Object[] getData(int selected){
        int transferid = (Integer) transferList.get(selected)[0];

        return data.getData(id, "transfer").get(1);
    }

    //Überweisen auf ein anderes Konto
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

    //Funktion um einzuzahlen
    public void updateBalance(Double amount){
        balance += amount;
    }
}