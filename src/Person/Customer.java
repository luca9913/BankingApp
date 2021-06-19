package Person;

import java.text.DateFormat;
import java.util.ArrayList;
import Database.ProdBase;
import Konto.*;

import java.util.Date;

public class Customer extends Person{

    //Konstruktor
    public Customer(int id){
        super(id);
        Object[] pdata = data.getData(id, "customer").get(1);
        if(pdata.length == 6) {
            this.preName = pdata[0].toString();
            this.name = pdata[1].toString();
            this.birthDate = pdata[2].toString();
            this.address = pdata[3].toString();
            this.zip = Integer.parseInt(pdata[4].toString());
            this.city = pdata[5].toString();
        }else if(pdata.length > 6){
            System.err.println("Zu viele Daten angegeben.");
        }else if(pdata.length < 6){
            System.err.println("Zu wenige Daten angegeben.");
        }
    }

    public Customer(String[] pdata){
        super(0);
        if(pdata.length == 8) {
            this.preName = pdata[0].toString();
            this.name = pdata[1].toString();
            this.birthDate = pdata[2].toString();
            this.address = pdata[3].toString();
            this.zip = Integer.parseInt(pdata[4].toString());
            this.city = pdata[5].toString();
            this.email = pdata[6];
            this.tel = pdata[7];
        }else if(pdata.length > 6){
            System.err.println("Zu viele Daten angegeben.");
        }else if(pdata.length < 6){
            System.err.println("Zu wenige Daten angegeben.");
        }
    }

    Konto konto;
    ProdBase data;
    ArrayList<Object[]> allAccounts;
    public ArrayList<Konto> allaccounts2;


    //erstellt Liste mit Konten des Kunden und gibt diese zurück
    public ArrayList<Object[]> getAllAccounts(){
        allAccounts = data.getAllAccounts(id);
        return allAccounts;
    }

    //initalisiert die Kontoobjekte
    public void initialiseAccounts(){

        for(Object[] arr : getAllAccounts()){
            int accountid = (Integer) arr[0];
            String type = (String) arr[1];
            double balance = (Double) arr[2];
            double dispo = (Double) arr[3];
            double transferLimit = (Double) arr[4];
            int ownerid = (Integer) arr[5];
            int bankerid = (Integer) arr[6];
            int locked = (Integer) arr[7];

            switch(type){
                case "Girokonto":
                    Girokonto tmpGiro = new Girokonto();
                    allaccounts2.add(tmpGiro);
                    break;
                case "Festgeldkonto":
                    Festgeldkonto tmpFest = new Festgeldkonto();
                    allaccounts2.add(tmpFest);
                    break;
                case "Depot":
                    Depot tmpDepot = new Depot();
                    allaccounts2.add(tmpDepot);
                    break;
                case "Kreditkarte":
                    Kreditkarte tmpCredit = new Kreditkarte();
                    allaccounts2.add(tmpCredit);
                    break;
            }

            allaccounts2.add(tmp);
        }
    }



    //Überweisen von ausgewähltem Konto auf ein anderes
    /*public void transfer(int selected, int recieverid, double betrag, String usage, String date){
        int accountid = (Integer) allAccounts.get(selected)[0];
        konto.transfer();
    }*/

    //Benutzerdaten ändern
    public void ChangeUserData(){

    }








    //Funktion, um alle Konten in der GUI zu aktualisieren
    private void syncAccounts(){

    }

    //Funktion, um zu überweisen
    private void transfer(int fromAcc, int toAcc, double sum){

    }

    //Funktion, um die aktuellen Kontostände anzuzeigen
    //Offene Frage: Alle Kontostände oder nur der Kontostand eines Kontos
    private void showFinances(int accID){

    }

    //Zeigt Kontobewegungen (z.B. letzte Überweisungen)
    //Offene Frage: Alle Kontobewegungen oder nur die Kontobewegungen eines Kontos
    private void showAccMovement(int accID){

    }

    //Ändern des eigenen Datensatzes
    private void changeMyData(String name, String preName, int zip, String address){
        this.name = name;
        this.preName = preName;
        this.zip = zip;
        this.address = address;
    }
}
