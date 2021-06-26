package Person;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.ArrayList;
import Database.*;
import Konto.Konto;
import Person.Customer;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class Banker extends Person {

    public ArrayList<Object[]> allaccounts, allcustomers, relatedrequests, alltransfers;
    private AuthBase authBase;
    //ArrayList<Konto> allaccounts, dispoaccounts;
    //ArrayList<Customer> allcustomers;
    //TODO: add methods to return a DataModel for Tables, List and the ComboBox, the goal is to separate the data handling and the displaying completely

    public Banker(int id, AuthBase auth){
        super(id);
        this.authBase = auth;
        Object[] pdata = data.getData(id, "banker").get(0);
        this.preName = pdata[1].toString();
        this.name = pdata[2].toString();
        this.birthDate = pdata[3].toString();
        this.zip = (Integer)pdata[4];
        this.city = pdata[5].toString();
        this.address = pdata[6].toString();
        update();
    }

    void update(){
        relatedrequests = data.getAllRequests(id);
        allaccounts = data.getAllAccounts(id);
        createCustomerList();
        getAllTransfers();
    }

    void createCustomerList(){
        allcustomers = new ArrayList<>(allaccounts.size());
        ArrayList<Integer> ids = new ArrayList<>(allaccounts.size());
        for(int i = 0; i < allaccounts.size(); i++){
            int customer_id = (Integer) allaccounts.get(i)[5];
            if(ids.contains(customer_id)){
                continue;
            }
            ids.add(customer_id);
        }
        for(int id : ids){
            allcustomers.add(data.getData(id, "customer").get(0));
        }
        allcustomers.trimToSize();
    }

    public ListModel getCustomerModel(){
        ArrayList<Object[]> customers = new ArrayList<>(allcustomers.size());
        customers.add(new Object[]{"Alle", -1});
        for(Object[] arr : allcustomers){
            customers.add(new Object[]{arr[0].toString() + " - " + arr[1].toString(), (Integer)arr[0]});
        }
        return new ListData(customers);
    }

    public TableData getRequestModel() {
        update();
        ArrayList<Object[]> requests = new ArrayList<>(relatedrequests.size());
        for(Object[] arr : relatedrequests){
            Object[] tmp = new Object[6];
            String name = getName((Integer)arr[2]);
            tmp[0] = arr[0];
            if((Integer)arr[3] == 0){
                tmp[1] = name;
            }else{
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


    public TableData getDispoModel(){
        ArrayList<Object[]> dispoaccounts = new ArrayList<>(allaccounts.size());
        for(int i = 0; i < allaccounts.size(); i++){
            Object[] obj = allaccounts.get(i);
            Object[] owner = data.getData((Integer)obj[5], "customer").get(0);
            if((Double)(obj[2]) < (Double)obj[3]){ //if balance (index 2) is lower than the allowed dispo (index 3)
                double over = (Double)obj[2] - (Double)obj[3];
                if(dispoaccounts.size() == 10){
                    dispoaccounts.ensureCapacity(dispoaccounts.size() + 1);
                }
                dispoaccounts.add(new String[]{obj[0].toString(), owner[1].toString(), obj[3].toString(), String.valueOf(over)});
            }
        }
        dispoaccounts.trimToSize();
        return new TableData(new String[]{"Konto-ID", "Name", "Dispo", "überzogen"}, dispoaccounts);
    }

    public ListData getAccountModel(int customerid){
            ArrayList<Object[]> accounts = new ArrayList<>(allaccounts.size());
            accounts.add(0, new Object[]{"Alle", -1});
            for (Object[] arr : allaccounts) {
                if ((Integer) arr[5] == customerid || customerid == -1) {
                    accounts.add(new Object[]{arr[0].toString() + " - " + arr[1].toString(), (Integer) arr[0]});
                }
            }
            accounts.trimToSize();
            return new ListData(accounts);
    }

    void getAllTransfers(){
        //for each account, data.gettransfers and append them to list
        alltransfers = new ArrayList<>();
        for(Object[] arr : allaccounts){
            alltransfers.addAll(data.getAllTransfers((Integer)arr[0]));
        }
        //TODO: check for IDs and delete when already added
    }

    //int selectedAccount is the index of the selected element in the list
    public TableData getTransferModel(int[] ids){
        if(ids.length == 1 && ids[0] == -1){
            return new TableData(new String[]{"ID", "Name", "Betrag"}, alltransfers);
        }else {
            ArrayList<Object[]> transfers = new ArrayList<>(alltransfers.size());
            for (int id : ids) {
                for (Object[] arr : alltransfers) {
                    if ((Integer) arr[2] == id) {
                        transfers.add(new Object[]{arr[0].toString(), getName((Integer) data.getData((Integer) arr[3], "account").get(0)[5]), arr[1], arr[4], arr[5]});
                    } else {
                        transfers.add(new Object[]{arr[0].toString(), getName((Integer) data.getData((Integer) arr[2], "account").get(0)[5]), arr[1], arr[4], arr[5]});
                    }
                }
            }
            return new TableData(new String[]{"ID", "Name", "Betrag"}, transfers);
        }
    }

    public String getBalance(String selected){
        String sub_id = selected.substring(selected.indexOf(":") + 1, selected.indexOf("-") - 1).trim();
        int id = Integer.parseInt(sub_id);

        String balance = "";
        for(Object[] arr : allaccounts){
            if(arr[0].equals(id)){
                balance = arr[2].toString();
            }
        }
        return balance;
    }


    public boolean un_lockAccount(int id, int status){
        return data.updateAccountBlockage(id, status);
    }

    public boolean deleteAccount(int id){
        return data.deleteAccount(id);
    }

    public String[] getUserData(int id){
        String[] userdata = new String[9];
        int i = 0;

        for(Object obj : data.getData(id, "customer").get(0)){
            if(obj == null){
                userdata[i] = "null";
            }else{
                userdata[i] = obj.toString();
            }
            i++;
        }
        return userdata;
    }

    String getName(int id){
        return data.getData(id, "customer").get(0)[2].toString();
    }

    public int getRequestStatus(int id){
        return (Integer)data.getData(id, "request").get(0)[1];
    }

    public boolean modifyRequest(int id, int status){
        /*possible actions:
         * approve: 1, decline: -1, (pending: 0)
         */
        return data.updateRequest(id, status);
    }

    public boolean insertCustomer(String[] pdata){
        //create new Customer-Object
        Customer customer = new Customer(pdata);
        //call data.insertPerson(Customer-Object)
        return data.insertPerson(customer);

    }

    public class TableData extends AbstractTableModel{
        String[] colnames;
        ArrayList<Object[]> data;

        TableData(String[] colnames, ArrayList<Object[]> data){
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
        public String getColumnName(int column){
            return colnames[column];
        }

        @Override
        //fill transfer table
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data.get(rowIndex)[columnIndex];
        }

        @Override
        public void setValueAt(Object value, int row, int col){
            data.get(row)[col] = value;
            fireTableDataChanged();
        }

        public void update(ArrayList<Object[]> newdata){
            this.data = newdata;
            fireTableDataChanged();
        }
    }

    public class ListData extends AbstractListModel implements ComboBoxModel{
        Object selected;
        ArrayList<Object[]> data;

        ListData(ArrayList<Object[]> data){
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

        @Override
        public Object getElementAt(int index) {
            return data.get(index)[0];
        }

        public int getSelectedID(int index){
            return (Integer)data.get(index)[1];
        }
    }

}

class BankerTest{
    public static void main(String[] args) {
    }
}
