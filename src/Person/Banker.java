package Person;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.ArrayList;
import Database.ProdBase;
import Konto.Konto;
import Person.Customer;

public class Banker extends Person {

    public ArrayList<Object[]> allaccounts, filteredaccounts, allcustomers, filteredtransfers, relatedrequests;
    public String[][] dispoaccounts;
    //ArrayList<Konto> allaccounts, filteredaccounts, dispoaccounts;
    //ArrayList<Customer> allcustomers;

    public Banker(int id){
        super(id);
        Object[] pdata = data.getData(id, "banker").get(1);
        this.preName = pdata[1].toString();
        this.name = pdata[2].toString();
        this.birthDate = pdata[3].toString();
        this.zip = (Integer)pdata[4];
        this.city = pdata[5].toString();
        this.address = pdata[6].toString();
        getAllRequests();
        getAllAccounts();
        getDispoAccounts();
        createCustomerList();
    }

    public void getAllRequests(){
        relatedrequests = data.getAllRequests(id);
    }

    public void getAllAccounts(){
        allaccounts = data.getAllAccounts(id);
    }

    public void getDispoAccounts(){
        ArrayList<String[]> tmp = new ArrayList<>(allaccounts.size());
        for(int i = 1; i < allaccounts.size(); i++){
            Object[] obj = allaccounts.get(i);
            Object[] owner = data.getData((Integer)obj[5], "customer").get(1);
            if((Double)(obj[2]) < (Double)obj[3]){ //if balance (index 2) is lower than the allowed dispo (index 3)
                double over = (Double)obj[2] - (Double)obj[3];
                tmp.add(new String[]{obj[0].toString(), owner[1].toString(), obj[3].toString(), String.valueOf(over)});
            }
        }
        tmp.trimToSize();
        dispoaccounts = new String[tmp.size()][4];
        for(int i = 0; i < tmp.size(); i++){
            dispoaccounts[i] = tmp.get(i);
        }
    }

    void createCustomerList(){
        allcustomers = new ArrayList<>(allaccounts.size());
        ArrayList<Integer> ids = new ArrayList<>();
        for(int i = 1; i < allaccounts.size(); i++){
            int customer_id = (Integer) allaccounts.get(i)[5];
            if(ids.contains(customer_id)){
                continue;
            }
            ids.add(customer_id);
        }
        for(int id : ids){
            allcustomers.add(data.getData(id, "customer").get(1));
        }
    }

    //int selectedAccount is the index of the selected element in the list
    public void getAllTransfers(int selectedAccount){
        Object[] acc = allaccounts.get(selectedAccount);
        filteredtransfers = data.getAllTransfers((Integer)acc[0]);
    }

    public void selectUserAccounts(int selected){
        int id = (Integer) allcustomers.get(selected)[0];

        for (Object[] arr : allaccounts) {
            if (arr[5].equals(id)) {
                filteredaccounts.add(arr);
            }
        }
    }

    public String getBalance(String selected){
        String sub_id = selected.substring(0, selected.indexOf("-") - 1).trim();
        int id = Integer.parseInt(sub_id);

        String balance = "";
        for(Object[] arr : allaccounts){
            if(arr[0].equals(id)){
                balance = arr[2].toString();
            }
        }
        return balance;
    }

    public String[] transferData(int selectedTransfer, int acc){
        String[] result = new String[4];
        Object[] tmp = filteredtransfers.get(selectedTransfer);

        if(tmp[2].equals(acc)){
            result[0] = tmp[3].toString();
        }else{
            result[0] = tmp[2].toString();
        }
        result[1] = tmp[0].toString();
        result[2] = tmp[1].toString();
        result[3] = tmp[4].toString();

        return result;
    }

    public boolean un_lockAccount(int selectedAccount, int status){
        int id;
        if(filteredaccounts.isEmpty()){
            id = (Integer)allaccounts.get(selectedAccount)[0];
        }else{
            id = (Integer)filteredaccounts.get(selectedAccount)[0];
        }

        return data.updateAccountBlockage(id, status);
    }

    public boolean deleteAccount(int selectedAccount){
        int id;
        if(filteredaccounts.isEmpty()){
            id = (Integer)allaccounts.get(selectedAccount)[0];
        }else{
            id = (Integer)filteredaccounts.get(selectedAccount)[0];
        }

        return data.deleteAccount(id);
    }

    public String[] getUserData(int selectedUser){
        int id = (Integer)allcustomers.get(selectedUser)[0];
        String[] userdata = new String[9];
        int i = 0;

        for(Object obj : data.getData(id, "customer").get(1)){
            if(obj == null){
                userdata[i] = "null";
            }else{
                userdata[i] = obj.toString();
            }
            i++;
        }
        return userdata;
    }

    public boolean modifyRequest(int selected, int status){
        /*possible actions:
         * approve: 1, decline: -1, (pending: 0)
         */
        int id = (Integer) relatedrequests.get(selected)[0];
        return data.updateRequest(id, status);
    }

    public boolean insertCustomer(String[] pdata){
        //create new Customer-Object
        Customer customer = new Customer(pdata);
        //call data.insertPerson(Customer-Object)
        return data.insertPerson(customer);

    }

}

class BankerTest{
    public static void main(String[] args) {
        ProdBase data = ProdBase.initialize();
        Banker admin = new Banker(1);
        String[] test = admin.getUserData(0);
        for(String str : test) {
            System.out.println(str);
        }
    }
}
