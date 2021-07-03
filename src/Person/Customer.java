package Person;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import Konto.*;
import java.util.Date;
import java.util.Random;

/**
 * Die Klasse "Customer" ist für die Objekte der Kunden da. Sie erbt von der Klasse "Person".
 */
public class Customer extends Person{

    private int mainBanker;
    public ArrayList<Object[]> allrequests;
    public ArrayList<Konto> allaccounts;

    /**
     * Standardkonstruktor der Klasse "Customer" mit einer Variable als Parameter.
     * @param id Dieser Parameter ist die Kunden-ID.
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
     * Standardkonstruktor der Klasse "Customer" mit einem Array als Parameter.
     * @param pdata Das Eingangsparameter ist ein String-Array mit allen Daten des Kunden.
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
     * Fragt alle Freigabeaufträge erneut ab und initalisiert die Konten neu
     */
    public void update(){
        this.allrequests = data.getAllRequests(id);
        initialiseAccounts();
        updateRequests();
    }


    /**
     * Diese Methode initialisiert die Konto-Objekte des Kunden
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
     * Aktualisiert die Freigabeaufträge
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
                    data.updateCustomerData(this);
                    data.deleteRequest((Integer)arr[0]);
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
     * Diese Methode ist für die Füllung der Tabelle "tableTurnover" im Tab Finanzübersicht zuständig.
     * @param index Der ausgewählte Listenindex der Liste "listAccounts1" steht für das Konto, von welchem in der Tabelle die
     * die Umsätze angezeigt werden sollen.
     * @return Es werden die Daten zum Füllen der Tabelle in Form einer TableData zurückgegeben.
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
                    name = "Extern";
                    transfers.set(i, new Object[]{transfer[0].toString(), name, transfer[1], transfer[4], "Begünstigter", "", transfer[5]});
                } else {
                    name = getName((Integer)otheracc.get(0)[5]);
                    transfers.set(i, new Object[]{transfer[0].toString(), name, transfer[1], transfer[4], "Begünstigter", transfer[3], transfer[5]});
                }
            } else if ((Integer)transfer[3] == accid) {
                ArrayList<Object[]> otheracc = data.getData((Integer) transfer[2], "account");
                if (otheracc.size() == 0) {
                    name = "Extern";
                    transfers.set(i, new Object[]{transfer[0].toString(), name, transfer[1], transfer[4], "Absender", "", transfer[5]});
                } else {
                    name = getName((Integer) data.getData((Integer) transfer[2], "account").get(0)[5]);
                    transfers.set(i, new Object[]{transfer[0].toString(), name, transfer[1], transfer[4], "Absender", transfer[2], transfer[5]});
                }
            }
        }
        return new TableData(new String[]{"ID", "Begünstigter", "Betrag", "Verwendungszweck"}, transfers);
    }

    /**
     * Diese Methode erstellt ein Request für den Banker, welche eine Dispoänderung
     * des Kunden enthält.
     * @param key Dieser Parameter enthält das Schlüsselwort für die Request, dass es sich
     * um die Änderung des Disporahmens handelt.
     * @param value Dieser Parameter enthält den Wert der Änderung.
     * @param accID Dieser Parameter enthält die ID des Konto-Objektes, für welches
     * die Änderung gelten soll.
     */
    public void createDispoRequest(String key, String value, int accID){
        this.data.createRequest(key, value, accID, this.id, allaccounts.get(accID).getBanker().getId());
    }

    /**
     * Diese Methode erstellt ein Request für den Banker, welche die Änderung des
     * Überweisungsrahmens des Kunden enthält.
     * @param key Dieser Parameter enthält das Schlüsselwort für die Request, dass es sich
     * um die Änderung des Überweisungsrahmens handelt.
     * @param value Dieser Parameter enthält den Wert der Änderung.
     * @param accID Dieser Parameter enthält die ID des Konto-Objektes, für welches
     * die Änderung gelten soll.
     */
    public void createLimitRequest(String key, String value, int accID){
        this.data.createRequest(key, value, accID, this.id, allaccounts.get(accID).getBanker().getId());
    }

    /**
     * Diese Methode führt eine Überweisung des Kunden von einem in der Liste gewählten Konto auf ein
     * anderes Konto durch.
     * @param selected Dieser Parameter enthält die Zeile des Kontos, von dem überwiesen werden soll.
     * @param receiverid Dieser Parameter enthält die ID des Empfängerkontos.
     * @param betrag Dieser Parameter enthält den Betrag der Überweisung.
     * @param usage Dieser Parameter enthält den Überweisungszweck.
     * @param date Dieser Parameter enthält das Datum der Überweisung.
     * @return Der Rückgabewert ist ein boolean. Ist er "true, dann war die überweisung erfolgreich, wird
     * "false" zurückgegeben, dann war die Überweisung fehlerhaft.
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
     * Diese Methode ist dafür zuständig, die geänderten Benutzerdaten des Kunden als Request
     * an den Bänker zu übermitteln.
     * @param name Parameter für den Namen des Kunden.
     * @param prename Parameter für den Vornamen des Kunden.
     * @param zip Parameter für die PLZ des Kunden.
     * @param city Parameter für die Stadt des Kunden.
     * @param address Parameter für die Adresse des Kunden.
     * @param email Parameter für die Email des Kunden.
     * @param telephone Parameter für die Telefonnummer des Kunden.
     */
    public void changeUserData(String name, String prename, int zip, String city, String address, String email, String telephone){

        if(!name.equals(this.name)){
            data.createRequest("name", name, 0, id, mainBanker);
        }
        if(!prename.equals(this.preName)){
            data.createRequest("prename", prename, 0, id, mainBanker);
        }
        if(zip != this.zip){
            data.createRequest("zip", Integer.toString(zip) , 0, id, mainBanker);
        }
        if(!city.equals(this.city)){
            data.createRequest("city", city, 0, id, mainBanker);
        }
        if(!address.equals(this.address)){
            data.createRequest("address", address, 0, id, mainBanker);
        }
        if(!email.equals(this.email)){
            data.createRequest("email", email, 0, id, mainBanker);
        }
        if(!telephone.equals(this.tel)){
            data.createRequest("telephone", telephone, 0, id, mainBanker);
        }
    }

    /**
     * Diese Methode ist dafür zuständig, eine Anfrage ("Request") für ein neues Konto-Objekt zu erstellen.
     * @param type Der Parameter spezifiziert, welcher Kontotyp erstellt werden soll.
     * @param accID Der Parameter ist die ID des Kunden-Objektes. Er wird gebraucht, damit
     * das Konto für den entsprechenden Kunden angelegt wird.
     */
    public void createAccount(String type, int accID) {
        this.data.createRequest("account", type, accID, this.id, this.mainBanker);
    }

    /**
     * Diese Methode ist dafür zuständig, um ein vorher ausgewähltes Konto zu löschen.
     * @param remove Dieser Parameter übergibt das zu löschende Kontoobjekt.
     * @param rest Dieser Parameter übergibt das Konto-Objekt, welches den Restsaldo des
     * zu löschenden Kontos erhalten soll.
     */
    public void removeAccount(Konto remove, Konto rest){
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        data.insertTransfer(remove.getBalance(), remove.getId(), rest.getId(), "Restbetrag Kontoauflösung", today);
        data.deleteAccount(remove.getId());
    }
}
