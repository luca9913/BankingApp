package Administration;


import java.util.ArrayList;
import Database.ProdBase;

public class Administration{

    ProdBase data;
    int banker_id;
    ArrayList<Object[]> allaccounts, allcustomers, filteredaccounts, filteredtransfers;

    public Administration(int id, ProdBase data){
        this.banker_id = id;
        this.data = data;
        getAllAccounts();
        createCustomerList();
    }

    public void getAllAccounts(){
        allaccounts = data.getAllAccounts(banker_id);
    }

    public void selectUserAccounts(int selected){
        int id = (Integer) allcustomers.get(selected+1)[0];

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

    public void getAllTransfers(int selectedAccount){
        filteredtransfers = data.getAllTransfers((Integer)allaccounts.get(selectedAccount)[0]);
    }

    public String[] transferData(int selectedTransfer){
        return null;
    }

}

class AdministrationTest{
    public static void main(String[] args) {
        ProdBase data = ProdBase.initialize();
        Administration admin = new Administration(1, data);
        admin.getAllAccounts();
    }
}