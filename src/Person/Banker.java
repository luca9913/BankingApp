package Person;

import java.util.*;
import GUI.HelpMethods;
import Konto.*;
import javax.swing.*;

/**
 * Die Klasse "Banker" ist für die Objekte der Kunden da. Sie erbt von der Klasse "Person".
 */
public class Banker extends Person {

    public ArrayList<Object[]> allaccounts, allcustomers, relatedrequests, alltransfers;

    /**
     * Standardkonstruktor der Klasse "Customer" mit einer Variable als Parameter.
     * @param id Parameter für den Konstruktor.
     */
    public Banker(int id) {
        super(id);
        Object[] pdata = data.getData(id, "banker").get(0);
        this.preName = pdata[1].toString();
        this.name = pdata[2].toString();
        this.birthDate = pdata[3].toString();
        this.zip = (Integer) pdata[4];
        this.city = pdata[5].toString();
        this.address = pdata[6].toString();
        update();
    }

    /**
     * Aktualisiert die Daten des Bankers durch einen erneuten Datenbankabgleich
     */
    void update() {
        relatedrequests = data.getAllRequests(id);
        allaccounts = data.getAllAccounts(id);
        createCustomerList();
        getAllTransfers();
    }

    /**
     * Erstellt eine ArrayList mit allen Kundenobjekten und speichert diese in <code>allcustomers</code>
     */
    void createCustomerList() {
        allcustomers = new ArrayList<>(allaccounts.size());
        ArrayList<Integer> ids = new ArrayList<>(allaccounts.size());
        for (int i = 0; i < allaccounts.size(); i++) {
            int customer_id = (Integer) allaccounts.get(i)[5];
            if (ids.contains(customer_id)) {
                continue;
            }
            ids.add(customer_id);
        }
        for (int id : ids) {
            allcustomers.add(data.getData(id, "customer").get(0));
        }
        allcustomers.trimToSize();
    }

    /**
     * Erstellt eine ArrayList mit allen Kunden und gibt diese als Modell für eine Liste aus
     * @return Modell für die Kundenliste
     */
    public ListModel getCustomerModel() {
        update();
        ArrayList<Object[]> customers = new ArrayList<>(allcustomers.size());
        customers.add(new Object[]{"Alle", -1});
        for (Object[] arr : allcustomers) {
            customers.add(new Object[]{arr[0].toString() + " - " + arr[2].toString(), (Integer) arr[0]});
        }
        return new ListData(customers);
    }

    /**
     * Erstellt ein Modell für die Tabelle mit den Freigabeaufträgen
     * @return Freigabeaufträge im TableData Format
     */
    public TableData getRequestModel() {
        update();
        ArrayList<Object[]> requests = new ArrayList<>(relatedrequests.size());
        for (Object[] arr : relatedrequests) {
            Object[] tmp = new Object[6];
            String name = getName((Integer) arr[2]);
            tmp[0] = arr[0];
            if ((Integer) arr[3] == 0) {
                tmp[1] = name;
            } else {
                tmp[1] = name + " - Konto-Nr.: " + arr[3];
            }
            tmp[2] = arr[5];
            tmp[3] = arr[6];
            tmp[4] = arr[7];
            tmp[5] = arr[1];

            requests.add(tmp);
        }
        return new TableData(new String[]{"ID", "Name", "Betreff", "Alter Wert", "Neuer Wert"}, requests);
    }

    /**
     * Erstellt ein Modell für die Tabelle mit den Konten im Dispo
     * @return Konten im Dispo im TableData Format
     */
    public TableData getDispoModel() {
        update();
        ArrayList<Object[]> dispoaccounts = new ArrayList<>(allaccounts.size());
        for (int i = 0; i < allaccounts.size(); i++) {
            Object[] obj = allaccounts.get(i);
            Object[] owner = data.getData((Integer) obj[5], "customer").get(0);
            if ((Double) (obj[2]) < (Double) obj[3]) { //if balance (index 2) is lower than the allowed dispo (index 3)
                double over = (Double) obj[2] - (Double) obj[3];
                if (dispoaccounts.size() == 10) {
                    dispoaccounts.ensureCapacity(dispoaccounts.size() + 1);
                }
                dispoaccounts.add(new String[]{obj[0].toString(), owner[1].toString(), obj[3].toString(), String.valueOf(over)});
            }
        }
        dispoaccounts.trimToSize();
        return new TableData(new String[]{"Konto-ID", "Name", "Dispo", "überzogen"}, dispoaccounts);
    }

    /**
     * Erstellt ein Modell für die Tabelle mit den Konten, die zu einem bestimmten Kunden gehören.
     * @param customerid Kunden-ID zur Identifizierung der zum Kunden zugehörigen Konten
     * @return Kundenkonten im TableData Format
     */
    public ListData getAccountModel(int customerid) {
        update();
        ArrayList<Object[]> accounts = new ArrayList<>(allaccounts.size());
        for (Object[] arr : allaccounts) {
            if ((Integer) arr[5] == customerid || customerid == -1) {
                accounts.add(new Object[]{arr[0].toString() + " - " + arr[1].toString(), (Integer) arr[0], arr[7], arr[3]});
            }
        }
        accounts.trimToSize();
        return new ListData(accounts);
    }

    /**
     * Ruft alle Transferns eines Kunden in der Datenbank ab und speichert sie in einer ArrayList <code>alltransfers</code>
     */
    void getAllTransfers() {
        alltransfers = new ArrayList<>();
        boolean duplicate = false;
        for (Object[] arr : allaccounts) {
            ArrayList<Object[]> transfers = data.getAllTransfers((Integer) arr[0]);
            for (Object[] t : transfers) {
                for (Object[] r : alltransfers) {
                    if (t[0] == r[0]) {
                        duplicate = true;
                    }
                }
                if (duplicate == true) {
                    continue;
                }
                alltransfers.add(t);
            }
        }
    }

    /**
     * Erstellt ein Modell für die Tabelle mit den zu einem Konto zugehörigen Transfers/Umsätzen
     * @param ids IDs der Transfers des Kontos
     * @return Transfers/Umsätze im TableData Format
     */
    public TableData getTransferModel(int[] ids) {
        update();
        ArrayList<Object[]> transfers = new ArrayList<>(alltransfers.size());
        ArrayList<Integer> added = new ArrayList<>(alltransfers.size());
        String[] colnames;
        String name;
        if (ids[0] == -1) {
            colnames = new String[]{"ID", "Sender", "Empfänger", "Betrag"};
            for (int id = 1; id < ids.length; id++) {
                for (Object[] arr : alltransfers) {
                    if (((Integer) arr[2] == ids[id] || (Integer) arr[3] == ids[id]) && !added.contains((Integer) arr[0])) {
                        if (data.getData((Integer) arr[2], "account").size() == 0) {
                            transfers.add(new Object[]{arr[0].toString(), arr[2] + " - Gelöscht", getName((Integer) data.getData((Integer) arr[3], "account").get(0)[5]), arr[1], arr[4], arr[5]});
                        } else if (data.getData((Integer) arr[3], "account").size() == 0) {
                            transfers.add(new Object[]{arr[0].toString(), getName((Integer) data.getData((Integer) arr[2], "account").get(0)[5]), arr[3] + " - Gelöscht", arr[1], arr[4], arr[5]});
                        } else {
                            transfers.add(new Object[]{arr[0].toString(), getName((Integer) data.getData((Integer) arr[2], "account").get(0)[5]), getName((Integer) data.getData((Integer) arr[3], "account").get(0)[5]), arr[1], arr[4], arr[5]});
                        }
                        added.add((Integer) arr[0]);
                    }
                }
            }
        } else {
            colnames = new String[]{"ID", "Sender/Empfänger", "Betrag"};
            for (int id : ids) {
                for (Object[] arr : data.getAllTransfers(id)) {
                    if ((Integer) arr[2] == id) {
                        if (data.getData((Integer) arr[3], "account").size() == 0) {
                            name = "Gelöscht";
                        } else {
                            name = getName((Integer) data.getData((Integer) arr[3], "account").get(0)[5]);
                        }
                        transfers.add(new Object[]{arr[0].toString(), arr[3].toString() + " - " + name, arr[1], arr[4], arr[5], "out"});
                    } else if ((Integer) arr[3] == id) {
                        if ((Integer) data.getData((Integer) arr[3], "account").size() == 0) {
                            name = "Gelöscht";
                        } else {
                            name = getName((Integer) data.getData((Integer) arr[2], "account").get(0)[5]);
                        }
                        transfers.add(new Object[]{arr[0].toString(), arr[2].toString() + " - " + name, arr[1], arr[4], arr[5], "in"});
                    }
                }
            }
        }

        return new TableData(colnames, transfers);
    }

    /**
     * Gibt den Kontostand des Kontos aus
     * @param id Konto-ID zur Identifizierung des Kontos
     * @return Text, mit Kontostand, falls vorhanden, ansonsten mit dem Hinweis, dass ein Konto gewählt werden soll.
     */
    public String getBalance(int id) {
        if (id == -1) {
            return "Konto wählen..";
        } else {
            return "Kontostand: " + data.getData(id, "account").get(0)[2].toString() + "€";
        }
    }

    /**
     * Ändert den Status eines Kontos
     * @param id ID des Kontos
     * @param status Zu setztender Status des Kontos
     * @return Bei Erfolg wird true, bei Misserfolg false zurückgegeben
     */
    public boolean un_lockAccount(int id, int status) {
        return data.updateAccountBlockage(id, status);
    }

    /**
     * Löscht ein Konto
     * @param id ID des Kontos
     * @return Bei Erfolg wird true, bei Misserfolg false zurückgegeben
     */
    public boolean deleteAccount(int id) {
        return data.deleteAccount(id);
    }

    /**
     * Gibt ein bestimmtes Kundenobjekt zurück
     * @param id ID des Kunden
     * @return Objekt des Kunden
     */
    public Customer getUserData(int id) {
        Customer user = new Customer(id);
        return user;
    }

    /**
     * Aktualisiert die Daten des Kontos beispielsweise das Dispo
     * @param id ID des Kontos
     * @param col Ausgewählter Datensatz
     * @param value Zu setztender Wert
     * @return Bei Erfolg wird true, bei Misserfolg false zurückgegeben
     */
    public boolean updateAccData(int id, int col, Object value) {
        Konto modified;
        Object[] accdata = data.getData(id, "account").get(0);
        switch (accdata[1].toString()) {
            case "Depot":
                modified = new Depot(id, this, new Customer((Integer) accdata[5]), data);
                break;
            case "Festgeldkonto":
                modified = new Festgeldkonto(id, this, new Customer((Integer) accdata[5]), data);
                break;
            case "Kreditkarte":
                modified = new Kreditkarte(id, this, new Customer((Integer) accdata[5]), data);
                break;
            default:
                modified = new Girokonto(id, this, new Customer((Integer) accdata[5]), data);
                break;
        }
        switch (col) {
            case 3:
                modified.setDispo(Double.parseDouble(value.toString()));
                break;
            case 4:
                modified.setLimit(Integer.parseInt(value.toString()));
                break;
        }
        modified.setId((Integer) accdata[0]);
        return data.updateAccountData(modified);
    }

    /**
     * Aktualisiert die Kundendaten
     * @param user Kundenobjekt
     * @return Bei Erfolg wird true, bei Misserfolg false zurückgegeben
     */
    public boolean updateUserData(Customer user) {
        return data.updateCustomerData(user);
    }

    /**
     * Gibt den Status des Freigabeauftrages zurück
     * @param id Indentifikationsnummer
     * @return Aktueller Status des Freigabeauftrages
     */
    public int getRequestStatus(int id) {
        return (Integer) data.getData(id, "request").get(0)[1];
    }

    /**
     * Setzt einen neuen Status für einen Freigabeauftrag
     * @param id ID des Freigabeauftrags
     * @param status neuer Status des Freigabeauftrags
     * @return Bei Erfolg wird true, bei Misserfolg false zurückgegeben
     */
    public boolean modifyRequest(int id, int status) {
        return data.updateRequest(id, status);
    }

    /**
     * Erstellt mit Hilfe der übergebenen Daten einen neuen Kunden und verknüpft diesen mit der Authentifizierungsdatenbank
     * @param pdata Daten des neuen Kunden als String Array
     */
    public void insertCustomer(String[] pdata) {
        Customer customer = new Customer(pdata);

        data.insertPerson(customer);

        int user_id = (Integer)data.executeCustomQuery("SELECT MAX(customer_id) FROM customer").get(0)[0];

        HelpMethods h = new HelpMethods();
        String newPassword = h.generatePassword();
        Integer newLoginID;


        Boolean success = false;
        do {
            newLoginID = h.generateLoginID();
            success = auth.insertUser(newLoginID, newPassword, user_id,"customer");
        } while (success == false);

        JOptionPane.showMessageDialog(null,"Der neue Kunde wurde erfolgreich angelegt: \nLogin-ID: " + newLoginID + "\nPasswort: " + newPassword + "\nDas Passwort ist vorläufig und wird dem Kunden per Post zugestellt. Beim ersten Login wird er aufgefordert das Passwort aus Sicherheitsgründen zu ändern!","Fehlerhafte Eingabe(n)", JOptionPane.CANCEL_OPTION);
    }

    /**
     * Verwaltet die Daten in einer Liste
     */
    public class ListData extends AbstractListModel implements ComboBoxModel {
        Object selected;
        ArrayList<Object[]> data;

        ListData(ArrayList<Object[]> data) {
            this.data = data;
        }

        /**
         * Setzt ein Element in der Liste als ausgewählt
         * @param anItem Element, dass als ausgewählt markiert werden soll
         */
        @Override
        public void setSelectedItem(Object anItem) {
            selected = anItem;
        }

        /**
         * Gibt das ausgewählte Element der Liste zurück
         * @return Ausgewähltes Element als Object
         */
        @Override
        public Object getSelectedItem() {
            return selected;
        }

        /**
         * Gibt die Größe der Liste zurück
         * @return Größe der Liste
         */
        @Override
        public int getSize() {
            return data.size();
        }

        /**
         * Fügt der Liste ein Element hinzu
         * @param index Index, an dem das neue Element hinzugefügt werden soll
         * @param value Wert des neuen Elements
         */
        public void addElement(int index, Object[] value) {
            data.add(index, value);
        }

        /**
         * Gibt den Wert eines bestimmten Elements an einem vorgegebenen Index aus
         * @param index Index des auszugebenden Elements
         * @return Wert des Elements am gegebenen Index
         */
        @Override
        public Object getElementAt(int index) {
            return data.get(index)[0];
        }

        /**
         * Gibt die ID des Elements am gegebenen Index in der List zurück
         * @param index Index, von dem die ID ausgegeben werden soll
         * @return ID als Integer
         */
        public int getSelectedID(int index) {
            return (Integer) data.get(index)[1];
        }

        /**
         * Gibt den Status des Elements am gegebenen Index in der List zurück
         * @param index Index, von dem der Status ausgegeben werden soll
         * @return Status als Integer
         */
        public int getStatus(int index) {
            return (Integer) data.get(index)[2];
        }

        /**
         * Gibt den Dispo des Elements am gegebenen Index in der List zurück
         * @param index Index, von dem der Dispo ausgegeben werden soll
         * @return ID als Integer
         */
        public String getDispo(int index) {
            return data.get(index)[3].toString();
        }

        /**
         * Setzt einen Wert an einem bestimmten Index.
         * @param index Index, an dem der Wert gesetzt werden soll
         * @param col Spalte, in der der Wert gesetzt werden soll
         * @param value Wert, der gesetzt werden soll
         */
        public void setValueAt(int index, int col, Object value) {
            data.get(index)[col] = value;
            fireContentsChanged(this, index, index);
        }

        /**
         * Löscht ein Element an einem bestimmten Index aus der Liste
         * @param index Index, dessen Element gelöscht werden soll
         */
        public void delete(int index) {
            data.remove(index);
            fireContentsChanged(this, index, index);
        }
    }

}
