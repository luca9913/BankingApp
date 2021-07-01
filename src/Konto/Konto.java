package Konto;

import Person.*;
import Database.*;
import Database.ProdBase;

import java.util.ArrayList;

public abstract class Konto{
    private int id; //KontoID
    private String type;
    private double balance = 0;
    private double dispo = -500;
    private int transferlimit = 10000;
    private int locked;
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
        return this.getBanker();
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

    //gibt Empfängers / Senders eines Umsatzes zurück
    public Object[] getDataName (int selected){
        int transferid = (Integer) transferList.get(selected)[0];

        for(Object[] transfer : transferList){
            if(id == (Integer) transfer[2])
                if(transferid == (Integer) transfer[0])
                    return data.executeCustomQuery("SELECT prename, name FROM transfer,account,customer WHERE sender = account_id AND owner = customer_id AND transfer_id = " + transferid ).get(1);
            else
                if(transferid == (Integer) transfer[0])
                    return data.executeCustomQuery("SELECT prename, name FROM transfer,account,customer WHERE sender = account_id AND owner = customer_id AND transfer_id = " + transferid ).get(1);
        }
        return null;
    }

    //aktualisiert die Balance anhand der Überweisungen
    public void refreshBalance(){
        for(Object[] transfer : transferList){
            if(id == (Integer) transfer[2])
                zahlungsAusgang((Integer)transfer[1]);
            else
                zahlungsEingang((Integer) transfer[1]);
        }
        data.updateBalance(id, balance);
    }

    //Überweisen auf ein anderes Konto
    public void transfer(int recieverid, double betrag, String usage, String date){
        if(transferlimit < betrag)
        {
            throw new IllegalArgumentException("Transfer limit ueberschritten");
        }else
        {
            //Transfer in die Datenbank schreiben
            data.insertTransfer(betrag, id, recieverid, usage, date); //fehler weil insertTransfer noch nicht fertig
        }
    }



    public void aendern(String key, String value){
        data.createRequest(key, value, owner.getId(), banker.getId(), owner.getId());
    }


    public void aufloesen(Konto zielkonto){
        if(balance < 0)
        {
            throw new IllegalStateException("Konto isch im Minus um "+ balance +" Euro");
        }else
        {
            zielkonto.zahlungsEingang(zahlungsAusgang(balance));
        }
    }

    public double aufloesen() {
        if (balance < 0) {
            throw new IllegalStateException("Konto isch im Minus");
        }
        return balance;
    }

    //Funktion um einzuzahlen
    public void zahlungsEingang(double betrag){
        if(betrag <= 0)
        {
            throw new IllegalArgumentException("Kein negativer Parameter oder 0 möglich");
        }
        balance += betrag;
    }

    //Funktion um auszuzahlen
    public double zahlungsAusgang(double betrag){
        if(betrag <= 0)
        {
            throw new IllegalArgumentException("Kein negativer Parameter oder 0 möglich");
        }
        if(dispo > balance - betrag)
        {
            double ueberzug = (balance - betrag -dispo) * -1;
            throw new IllegalArgumentException("Dispo ueberzogen um "+ ueberzug + " Euro");
        }else
        {
            balance -= betrag;
            return betrag;
        }
    }



    public double anzeigenDispo(){
        return dispo;
    }
}