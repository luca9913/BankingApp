package Person;

import Database.AuthBase;
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
    public ProdBase data = ProdBase.initialize();
    public AuthBase auth = AuthBase.initialize();

    //Konstruktor
    Person(int id){
        this.id = id;
    }

    public void closeConnections(){
        data.close();
        auth.close();
    }

    public int getId() {
        return id;
    }
}
