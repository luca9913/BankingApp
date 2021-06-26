package Person;

import Database.ProdBase;
import java.util.Date;

public abstract class Person {

    //Variablen
    public int id;
    public String name;
    public String preName;
    public String birthDate;
    public int zip;
    public String city;
    public String address;
    public String email;
    public String tel;
    protected ProdBase data;

    //Konstruktor
    Person(int id){
        this.id = id;
        if(data == null){
            data = ProdBase.initialize();
        }
    }

    public int getId() {
        return id;
    }
}
