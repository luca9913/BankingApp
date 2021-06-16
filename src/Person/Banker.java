package Person;

import java.util.Date;
import java.util.ArrayList;
import Database.ProdBase;
import Konto.Konto;

public class Banker extends Person {

    ProdBase data;
    ArrayList<Object[]> allaccounts, allcustomers, filteredaccounts, filteredtransfers;

    public Banker(int id, ProdBase data){
        super(id, id);
        this.data = data;
        getAllAccounts();
        createCustomerList();
    }

    public void getAllAccounts(){
        allaccounts = data.getAllAccounts(id);
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
        return true;
    }

}

class BankerTest{
    public static void main(String[] args) {
        ProdBase data = ProdBase.initialize();
        Banker admin = new Banker(1, data);
        admin.getAllTransfers(1);
        System.out.println(admin.transferData(1, 3)[3]);
    }
}
