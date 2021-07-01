package Person;

import java.text.DateFormat;
import java.util.ArrayList;
import Database.ProdBase;
import Konto.*;

import javax.swing.*;
import java.util.Date;
import java.util.Random;

public class Customer extends Person{

    private int mainBanker;
    public ArrayList<Object[]> allrequests;
    public ArrayList<Konto> allaccounts;

    //Konstruktor
    public Customer(int id){
        super(id);
        Object[] pdata = data.getData(id, "customer").get(0);
        if(pdata.length == 10) {
            this.preName = pdata[1].toString();
            this.name = pdata[2].toString();
            this.birthDate = pdata[3].toString();
            this.zip = Integer.parseInt(pdata[4].toString());
            this.city = pdata[5].toString();
            this.address = pdata[6].toString();
            this.email = pdata[7].toString();
            this.tel = pdata[8].toString();
            this.mainBanker = Integer.parseInt(pdata[9].toString());
            update();
        }else if(pdata.length > 10){
            System.err.println("Zu viele Daten angegeben.");
        }else if(pdata.length < 10){
            System.err.println("Zu wenige Daten angegeben.");
        }
    }

    public Customer(String[] pdata){
        super(0);
        if(pdata.length == 10) {
            this.preName = pdata[0].toString();
            this.name = pdata[1].toString();
            this.birthDate = pdata[2].toString();
            this.address = pdata[3].toString();
            this.zip = Integer.parseInt(pdata[4].toString());
            this.city = pdata[5].toString();
            this.email = pdata[6];
            this.tel = pdata[7];
            this.mainBanker = Integer.parseInt(pdata[8].toString());
        }else if(pdata.length > 10){
            System.err.println("Zu viele Daten angegeben.");
        }else if(pdata.length < 10){
            System.err.println("Zu wenige Daten angegeben.");
        }
    }

    public void update(){
        this.allrequests = data.getAllRequests(id);
        initialiseAccounts();
        updateRequests();
    }

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
                    tmpGiro.setLimit((Integer)arr[4]);
                    tmpGiro.setStatus((Integer)arr[7]);
                    allaccounts.add(tmpGiro);
                    break;
                case "Festgeldkonto":
                    Festgeldkonto tmpFest = new Festgeldkonto((Integer)arr[0], new Banker((Integer)arr[6]), this, data);
                    tmpFest.setBalance((Double)arr[2]);
                    tmpFest.setDispo((Double)arr[3]);
                    tmpFest.setLimit((Integer)arr[4]);
                    tmpFest.setStatus((Integer)arr[7]);
                    allaccounts.add(tmpFest);
                    break;
                case "Depot":
                    Depot tmpDepot = new Depot((Integer)arr[0], new Banker((Integer)arr[6]), this, data);
                    tmpDepot.setBalance((Double)arr[2]);
                    tmpDepot.setDispo((Double)arr[3]);
                    tmpDepot.setLimit((Integer)arr[4]);
                    tmpDepot.setStatus((Integer)arr[7]);
                    allaccounts.add(tmpDepot);
                    break;
                case "Kreditkarte":
                    Kreditkarte tmpCredit = new Kreditkarte((Integer)arr[0], new Banker((Integer)arr[6]), this, data);
                    tmpCredit.setBalance((Double)arr[2]);
                    tmpCredit.setDispo((Double)arr[3]);
                    tmpCredit.setLimit((Integer)arr[4]);
                    tmpCredit.setStatus((Integer)arr[7]);
                    allaccounts.add(tmpCredit);
                    break;
            }
        }
    }

    public void updateRequests(){
        for(Object[] arr : allrequests){
            if((Integer)arr[1] == 1){
                int accid = (Integer)arr[3];
                String key = arr[5].toString();
                Object value = arr[7];
                if(accid == 0){
                    switch(key){
                        case "name": this.name = value.toString(); break;
                        case "prename": this.preName = value.toString(); break;
                        case "zip": this.zip = (Integer)value; break;
                        case "city": this.city = value.toString(); break;
                        case "address": this.address = value.toString(); break;
                        case "email": this.email = value.toString(); break;
                        case "telephone": this.tel = value.toString(); break;
                    }
                    data.updateCustomerData(this);
                }else{
                    int index = 0;
                    do{
                        index++;
                    }while(allaccounts.get(index).getId() != accid);

                    switch (key){
                        case "dispo": allaccounts.get(index).setDispo((Double)value); break;
                        case "transferlimit": allaccounts.get(index).setLimit((Integer)value); break;
                    }
                    data.updateAccountData(allaccounts.get(index));
                }
            }else if((Integer)arr[1] == -1){
                System.out.println("Die Anfrage " + arr[0] + " mit Betreff " + arr[5] + " und neuem Wert " + arr[7] + "wurde abgelehnt.");
                data.deleteRequest((Integer)arr[0]);
            }
        }
    }

    public TableData getTransfers(int index){
        Konto acc = allaccounts.get(index);
        int accid = acc.getId();
        ArrayList<Object[]> transfers = acc.getAllTranfers();
        for(int i = 0; i < transfers.size(); i++){
            Object[] transfer = transfers.get(i);
            if ((Integer)transfer[2] == accid) {
                ArrayList<Object[]> otheracc = data.getData((Integer) transfer[3], "account");
                if (otheracc.size() == 0) {
                    name = "Gelöscht";
                    transfers.set(i, new Object[]{transfer[0].toString(), name, transfer[1], "Begünstiger", "-", transfer[4], transfer[5]});
                } else {
                    name = getName((Integer)otheracc.get(0)[5]);
                    transfers.set(i, new Object[]{transfer[0].toString(), name, transfer[1], "Begünstiger", transfer[3], transfer[4], transfer[5]});
                }
            } else if ((Integer)transfer[3] == accid) {
                ArrayList<Object[]> otheracc = data.getData((Integer) transfer[2], "account");
                if (otheracc.size() == 0) {
                    name = "Gelöscht";
                    transfers.set(i, new Object[]{transfer[0].toString(), name, transfer[1], "Absender", "-", transfer[4], transfer[5]});
                } else {
                    name = getName((Integer) data.getData((Integer) transfer[2], "account").get(0)[5]);
                    transfers.set(i, new Object[]{transfer[0].toString(), name, transfer[1], "Absender", transfer[2], transfer[4], transfer[5]});
                }
            }
        }
        return new TableData(new String[]{"ID", "Name", "Betrag"}, transfers);
    }

    public void createDispoRequest(String key, String value, int accID){
        this.data.createRequest(key, value, accID, this.id, allaccounts.get(accID).getBanker().getId());
    }

    public void createLimitRequest(String key, String value, int accID){
        this.data.createRequest(key, value, accID, this.id, allaccounts.get(accID).getBanker().getId());
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

        Customer kunde = new Customer(id);
        for( Object[] reqest : allrequests){
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


    public void createAccount(String type){
        Banker tempBanker = new Banker(this.mainBanker);
        switch(type){
            case "Girokonto":
                Girokonto tmpGiro;
                do{
                    Random rdm = new Random();
                    int number = rdm.nextInt(999999999);
                    tmpGiro = new Girokonto(number, tempBanker, this, this.data);
                }while(this.data.insertAccount(tmpGiro) == false);
                this.data.insertAccount(tmpGiro);
                break;
            case "Festgeldkonto":
                Festgeldkonto tmpFest;
                do{
                    Random rdm = new Random();
                    int number = rdm.nextInt(999999999);
                    tmpFest = new Festgeldkonto(number, tempBanker, this, this.data);
                }while(this.data.insertAccount(tmpFest) == false);
                this.data.insertAccount(tmpFest);
                break;
            case "Depot":
                Depot tmpDepot;
                do{
                    Random rdm = new Random();
                    int number = rdm.nextInt(999999999);
                    tmpDepot = new Depot(number, tempBanker, this, this.data);
                }while(this.data.insertAccount(tmpDepot) == false);
                this.data.insertAccount(tmpDepot);
                break;
            case "Kreditkarte":
                Kreditkarte tmpCredit;
                do{
                    Random rdm = new Random();
                    int number = rdm.nextInt(999999999);
                    tmpCredit = new Kreditkarte(number, tempBanker, this, this.data);
                }while(this.data.insertAccount(tmpCredit) == false);
                this.data.insertAccount(tmpCredit);
                break;
        }
    }

    public void removeAccount(){

    }


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
