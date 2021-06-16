package Konto;

import Person.*;
import Database.*;

public class Konto{
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

    public void aendern(String key, double value){
        database.createRequest(key,value, owner.getId(), banker.getId(), owner.getUid());
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
    public void ueberweisung(Konto zielkonto, double betrag){
        if(transferlimit < betrag)
        {
            throw new IllegalArgumentException("Transfer limit ueberschritten");
        }else
        {
            zielkonto.zahlungsEingang(betrag);
        }
    }
    public double anzeigenDispo(){
        return dispo;
    }
}