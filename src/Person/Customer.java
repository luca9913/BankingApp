package Person;

import java.text.DateFormat;
import java.util.ArrayList;
import Database.ProdBase;
import Konto.*;

import java.util.Date;

public class Customer extends Person{

    private int mainBanker;

    //Konstruktor
    public Customer(int id){
        super(id);
        Object[] pdata = data.getData(id, "customer").get(0);
        if(pdata.length == 9) {
            this.preName = pdata[1].toString();
            this.name = pdata[2].toString();
            this.birthDate = pdata[3].toString();
            this.zip = Integer.parseInt(pdata[4].toString());
            this.city = pdata[5].toString();
            this.address = pdata[6].toString();
            this.email = pdata[7].toString();
            this.tel = pdata[8].toString();
            //To-Do: DB anpassen
            //this.mainBanker = Integer.parseInt(pdata[6].toString());
        }else if(pdata.length > 9){
            System.err.println("Zu viele Daten angegeben.");
        }else if(pdata.length < 9){
            System.err.println("Zu wenige Daten angegeben.");
        }
    }

    public Customer(String[] pdata){
        super(0);
        if(pdata.length == 9) {
            this.preName = pdata[0].toString();
            this.name = pdata[1].toString();
            this.birthDate = pdata[2].toString();
            this.address = pdata[3].toString();
            this.zip = Integer.parseInt(pdata[4].toString());
            this.city = pdata[5].toString();
            this.email = pdata[6];
            this.tel = pdata[7];
            this.mainBanker = Integer.parseInt(pdata[8].toString());
        }else if(pdata.length > 9){
            System.err.println("Zu viele Daten angegeben.");
        }else if(pdata.length < 9){
            System.err.println("Zu wenige Daten angegeben.");
        }
    }

    ArrayList<Object[]> allRequests;
    public ArrayList<Konto> allaccounts;

    //initalisiert die Kontoobjekte
    public void initialiseAccounts(){
        ArrayList<Object[]> tmp = data.getAllAccounts(this.id);
        allaccounts = new ArrayList<>(tmp.size());
        for(Object[] arr : tmp){
            switch(arr[1].toString()){
                case "Girokonto":
                    Girokonto tmpGiro = new Girokonto((Integer)arr[0], new Banker((Integer)arr[6]), this, data);
                    tmpGiro.setBalance((Double)arr[2]);
                    tmpGiro.setDispo((Double)arr[3]);
                    tmpGiro.setLimit((Double)arr[4]);
                    tmpGiro.setStatus((Integer)arr[7]);
                    allaccounts.add(tmpGiro);
                    break;
                case "Festgeldkonto":
                    Festgeldkonto tmpFest = new Festgeldkonto((Integer)arr[0], new Banker((Integer)arr[6]), this, data);
                    tmpFest.setBalance((Double)arr[2]);
                    tmpFest.setDispo((Double)arr[3]);
                    tmpFest.setLimit((Double)arr[4]);
                    tmpFest.setStatus((Integer)arr[7]);
                    allaccounts.add(tmpFest);
                    break;
                case "Depot":
                    Depot tmpDepot = new Depot((Integer)arr[0], new Banker((Integer)arr[6]), this, data);
                    tmpDepot.setBalance((Double)arr[2]);
                    tmpDepot.setDispo((Double)arr[3]);
                    tmpDepot.setLimit((Double)arr[4]);
                    tmpDepot.setStatus((Integer)arr[7]);
                    allaccounts.add(tmpDepot);
                    break;
                case "Kreditkarte":
                    Kreditkarte tmpCredit = new Kreditkarte((Integer)arr[0], new Banker((Integer)arr[6]), this, data);
                    tmpCredit.setBalance((Double)arr[2]);
                    tmpCredit.setDispo((Double)arr[3]);
                    tmpCredit.setLimit((Double)arr[4]);
                    tmpCredit.setStatus((Integer)arr[7]);
                    allaccounts.add(tmpCredit);
                    break;
            }
        }
    }



    //Überweisen von ausgewähltem Konto auf ein anderes
    public void transfer(int selected, int recieverid, double betrag, String usage, String date){
        Konto konto = allaccounts.get(selected);
        konto.transfer(recieverid, betrag, usage, date);
    }


    public void changeUserData(String name, String prename, int zip, String city, String address, String email, String telephone){

        if(name != this.name){
            data.createRequest("Name", name, 0, id, mainBanker);
        }
        if(prename != this.preName){
            data.createRequest("Prename", prename, 0, id, mainBanker);
        }
        if(zip != this.zip){
            data.createRequest("Zip", Integer.toString(zip) , 0, id, mainBanker);
        }
        if(city != this.city){
            data.createRequest("City", city, 0, id, mainBanker);
        }
        if(address != this.address){
            data.createRequest("Address", address, 0, id, mainBanker);
        }
        if(email != this.email){
            data.createRequest("Email", email, 0, id, mainBanker);
        }
        if(telephone != this.tel){
            data.createRequest("Telephone", telephone, 0, id, mainBanker);
        }
    }

    public void refreshUserData(){

        allRequests = data.getAllRequests(id);

        Customer kunde = new Customer(id);
        for( Object[] reqest : allRequests){
            if((Integer)reqest[1] == 1){ //1 wird als freigegeben interpretiert
                String value = (String)reqest[6];
                switch((String)reqest[5]){
                    case "Name": this.name = value;
                    case "Prename": this.preName = value;
                    case "Zip": this.zip = Integer.parseInt(value);
                    case "City": this.city = value;
                    case "Address": this.address = value;
                    case "Email": this.email = value;
                    case "Telephone": this.tel = value;
                }
            }
        }
        data.updatePerson(kunde);
    }


    /*public Konto createAccount(){

    }

    public void removeAccount(){

    }*/


    //Funktion, um alle Konten in der GUI zu aktualisieren
    private void syncAccounts(){

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
