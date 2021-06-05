package Person;

import Konto.Depot;
import Konto.Festgeldkonto;
import Konto.Girokonto;
import Konto.Kreditkarte;

import java.util.Date;

public class Customer extends Person{

    //Konstruktor
    public Customer(String id, String name, String preName, Date birthDate, int zip, String address){
        super(id, name, preName, birthDate, zip, address);
    }


    //Ändern des eigenen Datensatzes
    private void changeMyData(String name, String preName, Date birthDate, int zip, String address){
        this.name = name;
        this.preName = preName;
        this.birthDate = birthDate;
        this.zip = zip;
        this.address = address;
    }

    private void createAcc(int choice){
        switch(choice){
            case 0:
                Girokonto konto1 = new Girokonto();
                break;
            case 1:
                Festgeldkonto konto2 = new Festgeldkonto();
                break;
            case 2:
                Kreditkarte konto3 = new Kreditkarte();
                break;
            case 3:
                Depot konto4 = new Depot();
                break;
        }
    }
}
