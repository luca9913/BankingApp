package Konto;

import Person.*;
import Database.*;
import Database.ProdBase;

import java.util.ArrayList;

public abstract class Konto{
    public int id; //KontoID
    public String type;
    public double balance = 0;
    public double dispo = -1000;
    public double transferlimit = 10000;
    public int locked;
    public Customer owner;
    public Banker banker;
    protected ProdBase database;

    public Konto(ProdBase database, String type, int id, Banker banker, Customer owner)
    {
        if(banker == null || owner == null)
        {
            throw new IllegalArgumentException("Banker und oder Customer darf net null sein");
        }
        this.banker = banker;
        this.owner = owner;
        this.id = id;
        this.database = database;
        this.type = type;
    }

    public Konto(ProdBase database, String type, double betrag, int id, Banker banker, Customer owner)
    {
        if(banker == null || owner == null)
        {
            throw new IllegalArgumentException("Banker und oder Customer darf net NULL sein");
        }
        if(betrag < 0) {
            throw new IllegalArgumentException("Kein negativer Parameter");
        }else
        {
            this.balance = betrag;
        }
        this.banker = banker;
        this.owner = owner;
        this.id = id;
        this.database = database;
        this.type = type;
    }

    ProdBase data;

    ArrayList<Object[]> transferList;

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

    //Überweisen auf ein anderes Konto TODO: usage und date muss noch in die insertTransfer() aufgenommen werden
    /*public void transfer(int recieverid, double betrag, String usage, String date){
        if(transferlimit < betrag)
        {
            throw new IllegalArgumentException("Transfer limit ueberschritten");
        }else
        {
            //Transfer in die Datenbank schreiben
            data.insertTransfer(betrag, id, recieverid, usage, date); //fehler weil insertTransfer noch nicht fertig
        }
    }*/



    /*public void aendern(String key, double value){
        database.createRequest(key,value, owner.getId(), banker.getId(), owner.getUid());
    }*/

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