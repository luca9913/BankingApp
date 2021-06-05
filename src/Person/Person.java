package Person;

import Database.ProdBase;

import java.util.Date;

public abstract class Person {

    //Variablen
    protected int uid;
    protected int id;
    protected String name;
    protected String preName;
    protected Date birthDate;
    protected int zip;
    protected String address;
    protected ProdBase datenbank;

    //Konstruktor
    Person(int uid,int id){
        this.uid = uid;
        this.id = id;
    }
    public void update(ProdBase datenbank){
        this.datenbank = datenbank;
    }

    public int getUid() {
        return uid;
    }
}
