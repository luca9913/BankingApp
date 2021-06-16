package Person;

import Database.ProdBase;
import java.util.Date;

public abstract class Person {

    //Variablen
    protected int uid;
    public int id;
    public String name;
    public String preName;
    public String birthDate;
    public int zip;
    public String city;
    public String address;
    protected ProdBase datenbank;

    //Konstruktor
    Person(int uid,int id){
        this.uid = uid;
        this.id = id;
    }

    Person(){

    }

    public void update(ProdBase datenbank){
        this.datenbank = datenbank;
    }

    public int getUid() {
        return uid;
    }

    public int getId() {
        return id;
    }
}
