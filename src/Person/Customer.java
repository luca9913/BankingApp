package Person;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import Konto.*;
import java.util.Date;
import java.util.Random;

/**
 * Hier Text einfügen
 */
public class Customer extends Person{

    private int mainBanker;
    public ArrayList<Object[]> allrequests;
    public ArrayList<Konto> allaccounts;

    /**
     * Hier Text einfügen
     * @param id Parameter für den Konstruktor.
     */
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

    /**
     * Hier Text einfügen
     * @param pdata Parameter für den Konstruktor.
     */
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

    /**
     * Hier Text einfügen
     */
    public void update(){
        this.allrequests = data.getAllRequests(id);
        initialiseAccounts();
        updateRequests();
    }


    /**
     * Diese Methode initialisiert die Konto-Objekte
     */
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

    /**
     * Hier Text einfügen
     */
    public void updateRequests(){
        for(Object[] arr : allrequests){
            if((Integer)arr[1] == 1){
                int accid = (Integer)arr[3];
                String key = arr[5].toString();
                Object value = arr[7];
                if(accid == 0){
                    switch (key) {
                        case "name" -> this.name = value.toString();
                        case "prename" -> this.preName = value.toString();
                        case "zip" -> this.zip = (Integer) value;
                        case "city" -> this.city = value.toString();
                        case "address" -> this.address = value.toString();
                        case "email" -> this.email = value.toString();
                        case "telephone" -> this.tel = value.toString();
                        case "account" -> {
                            Random rnd = new Random();
                            Konto newacc;
                            int id;
                            switch (value.toString()) {
                                case "Girokonto":
                                    id = 400000000 + rnd.nextInt(99999999);
                                    newacc = new Girokonto(id, new Banker(1 + rnd.nextInt(19)), this, data);
                                    data.insertAccount(newacc);
                                    allaccounts.add(newacc);
                                    break;
                                case "Festgeldkonto":
                                    id = 300000000 + rnd.nextInt(99999999);
                                    newacc = new Festgeldkonto(id, new Banker(1 + rnd.nextInt(19)), this, data);
                                    data.insertAccount(newacc);
                                    allaccounts.add(newacc);
                                    break;
                                case "Depot":
                                    id = 100000000 + rnd.nextInt(99999999);
                                    newacc = new Depot(id, new Banker(1+ rnd.nextInt(19)), this, data);
                                    data.insertAccount(newacc);
                                    allaccounts.add(newacc);
                                    break;
                                case "Kreditkarte":
                                    id = 200000000 + rnd.nextInt(99999999);
                                    newacc = new Kreditkarte(id, new Banker(1 + rnd.nextInt(19)), this, data);
                                    data.insertAccount(newacc);
                                    allaccounts.add(newacc);
                                    break;
                            }
                        }
                    }
                    data.deleteRequest((Integer)arr[0]);
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
                    data.deleteRequest((Integer)arr[0]);
                    data.updateAccountData(allaccounts.get(index));
                }
            }else if((Integer)arr[1] == -1){
                System.out.println("Die Anfrage " + arr[0] + " mit Betreff " + arr[5] + " und neuem Wert " + arr[7] + "wurde abgelehnt.");
                data.deleteRequest((Integer)arr[0]);
            }
        }
    }

    /**
     * Hier Text einfügen
     * @param index
     * @return
     */
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
                    transfers.set(i, new Object[]{transfer[0].toString(), name, transfer[1], transfer[4], "Begünstiger", "-", transfer[5]});
                } else {
                    name = getName((Integer)otheracc.get(0)[5]);
                    transfers.set(i, new Object[]{transfer[0].toString(), name, transfer[1], transfer[4], "Begünstiger", transfer[3], transfer[5]});
                }
            } else if ((Integer)transfer[3] == accid) {
                ArrayList<Object[]> otheracc = data.getData((Integer) transfer[2], "account");
                if (otheracc.size() == 0) {
                    name = "Gelöscht";
                    transfers.set(i, new Object[]{transfer[0].toString(), name, transfer[1], transfer[4], "Absender", "-", transfer[5]});
                } else {
                    name = getName((Integer) data.getData((Integer) transfer[2], "account").get(0)[5]);
                    transfers.set(i, new Object[]{transfer[0].toString(), name, transfer[1], transfer[4], "Absender", transfer[2], transfer[5]});
                }
            }
        }
        return new TableData(new String[]{"ID", "Name", "Betrag", "Verwendungszweck"}, transfers);
    }

    /**
     * Hier Text einfügen
     * @param key
     * @param value
     * @param accID
     */
    public void createDispoRequest(String key, String value, int accID){
        this.data.createRequest(key, value, accID, this.id, allaccounts.get(accID).getBanker().getId());
    }

    /**
     * Hier Text einfügen
     * @param key
     * @param value
     * @param accID
     */
    public void createLimitRequest(String key, String value, int accID){
        this.data.createRequest(key, value, accID, this.id, allaccounts.get(accID).getBanker().getId());
    }

    /**
     * Diese Methode sorgt für die Überweisung ...... to be continued
     * @param selected
     * @param receiverid
     * @param betrag
     * @param usage
     * @param date
     * @return
     */
    public boolean transfer(int selected, int receiverid, double betrag, String usage, String date){
        Konto konto = allaccounts.get(selected);
        Object[] tmp = data.getData(receiverid, "account").get(0);
        Konto receiver = null;
        switch(tmp[1].toString()){
            case "Girokonto":
                receiver = new Girokonto((Integer)tmp[0], new Banker((Integer)tmp[6]), new Customer((Integer)tmp[5]), data);
                receiver.setDispo((Double)tmp[3]);
                receiver.setLimit((Integer)tmp[4]);
                receiver.setBalance((Double)tmp[2]);
                break;
            case "Festgeldkonto":
                receiver = new Festgeldkonto((Integer)tmp[0], new Banker((Integer)tmp[6]), new Customer((Integer)tmp[5]), data);
                receiver.setDispo((Double)tmp[3]);
                receiver.setLimit((Integer)tmp[4]);
                receiver.setBalance((Double)tmp[2]);
                break;
            case "Depot":
                receiver = new Depot((Integer)tmp[0], new Banker((Integer)tmp[6]), new Customer((Integer)tmp[5]), data);
                receiver.setDispo((Double)tmp[3]);
                receiver.setLimit((Integer)tmp[4]);
                receiver.setBalance((Double)tmp[2]);
                break;
            case "Kreditkarte":
                receiver = new Kreditkarte((Integer)tmp[0], new Banker((Integer)tmp[6]), new Customer((Integer)tmp[5]), data);
                receiver.setDispo((Double)tmp[3]);
                receiver.setLimit((Integer)tmp[4]);
                receiver.setBalance((Double)tmp[2]);
                break;
        }
        receiver.updateBalance(betrag);
        data.updateAccountData(receiver);
        return konto.transfer(receiverid, betrag, usage, date);
    }

    /**
     * Hier Text einfügen
     * @param name
     * @param prename
     * @param zip
     * @param city
     * @param address
     * @param email
     * @param telephone
     */
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

    /**
     * Hier Text einfügen
     * @param type
     * @param accID
     */
    public void createAccount(String type, int accID) {
        this.data.createRequest("account", type, accID, this.id, this.mainBanker);
    }

    /**
     * Hier Text einfügen
     * @param remove
     * @param rest
     */
    public void removeAccount(Konto remove, Konto rest){
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        data.insertTransfer(remove.getBalance(), remove.getId(), rest.getId(), "Restbetrag Kontoauflösung", today);
        data.deleteAccount(remove.getId());
    }
}
