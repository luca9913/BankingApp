package Person;

import java.lang.reflect.Array;
import java.util.*;

import Database.*;
import GUI.HelpMethods;
import Konto.*;
import Person.Customer;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class Banker extends Person {

    public ArrayList<Object[]> allaccounts, allcustomers, relatedrequests, alltransfers;

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

    void update() {
        relatedrequests = data.getAllRequests(id);
        allaccounts = data.getAllAccounts(id);
        createCustomerList();
        getAllTransfers();
    }

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

    public ListModel getCustomerModel() {
        update();
        ArrayList<Object[]> customers = new ArrayList<>(allcustomers.size());
        customers.add(new Object[]{"Alle", -1});
        for (Object[] arr : allcustomers) {
            customers.add(new Object[]{arr[0].toString() + " - " + arr[2].toString(), (Integer) arr[0]});
        }
        return new ListData(customers);
    }

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

    void getAllTransfers() {
        //for each account, data.gettransfers and append them to list
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

    //int selectedAccount is the index of the selected element in the list
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

    public String getBalance(int id) {
        if (id == -1) {
            return "Konto wählen..";
        } else {
            return "Kontostand: " + data.getData(id, "account").get(0)[2].toString() + "€";
        }
    }


    public boolean un_lockAccount(int id, int status) {
        return data.updateAccountBlockage(id, status);
    }

    public boolean deleteAccount(int id) {
        return data.deleteAccount(id);
    }

    public Customer getUserData(int id) {
        Customer user = new Customer(id);
        return user;
    }

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
                modified.setLimit(Double.parseDouble(value.toString()));
                break;
        }
        modified.setId((Integer) accdata[0]);
        return data.updateAccountData(modified);
    }

    public boolean updateUserData(Customer user) {
        return data.updateCustomerData(user);
    }

    String getName(int id) {
        return data.getData(id, "customer").get(0)[2].toString();
    }

    public int getRequestStatus(int id) {
        return (Integer) data.getData(id, "request").get(0)[1];
    }

    public boolean modifyRequest(int id, int status) {
        /*possible actions:
         * approve: 1, decline: -1, (pending: 0)
         */
        return data.updateRequest(id, status);
    }

    public void insertCustomer(String[] pdata) {
        //create new Customer-Object
        Customer customer = new Customer(pdata);

        //call data.insertPerson(Customer-Object)
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

    public class TableData extends AbstractTableModel {
        String[] colnames;
        ArrayList<Object[]> data;

        TableData(String[] colnames, ArrayList<Object[]> data) {
            this.colnames = colnames;
            this.data = data;
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return colnames.length;
        }

        @Override
        public String getColumnName(int column) {
            return colnames[column];
        }

        @Override
        //fill transfer table
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data.get(rowIndex)[columnIndex];
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            data.get(row)[col] = value;
            fireTableDataChanged();
        }

        public void update(ArrayList<Object[]> newdata) {
            this.data = newdata;
            fireTableDataChanged();
        }
    }

    public class ListData extends AbstractListModel implements ComboBoxModel {
        Object selected;
        ArrayList<Object[]> data;

        ListData(ArrayList<Object[]> data) {
            this.data = data;
        }

        @Override
        public void setSelectedItem(Object anItem) {
            selected = anItem;
        }

        @Override
        public Object getSelectedItem() {
            return selected;
        }

        @Override
        public int getSize() {
            return data.size();
        }

        public void addElement(int index, Object[] value) {
            data.add(index, value);
        }

        @Override
        public Object getElementAt(int index) {
            return data.get(index)[0];
        }

        public int getSelectedID(int index) {
            return (Integer) data.get(index)[1];
        }

        public int getStatus(int index) {
            return (Integer) data.get(index)[2];
        }

        public String getDispo(int index) {
            return data.get(index)[3].toString();
        }

        public void setValueAt(int index, int col, Object value) {
            data.get(index)[col] = value;
            fireContentsChanged(this, index, index);
        }

        public void delete(int index) {
            data.remove(index);
            fireContentsChanged(this, index, index);
        }
    }

}
