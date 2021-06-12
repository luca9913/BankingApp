package Administration;


import java.lang.reflect.Array;
import java.util.ArrayList;
import Database.ProdBase;

public class Administration{

    ProdBase data;
    int banker_id;
    ArrayList<Object[]> accounts;
    ArrayList<Object[]> customers;

    Administration(int id, ProdBase data){
        this.banker_id = id;
        this.data = data;
    }

    public void getAllAccounts(int banker_id){
        accounts = data.getAllAccounts(banker_id);
    }

    public ArrayList<String> selectUserAccounts(String selected){
        String sub_id = selected.substring(0, selected.indexOf("-") - 1).trim();
        int id = Integer.parseInt(sub_id);

        ArrayList<String> account_info = new ArrayList<>();
        for(Object[] arr : accounts){
            if(arr[5].equals(id)){
                account_info.add(arr[0] + " - " + arr[1]);
            }
        }

        return account_info;
    }

    public String getBalance(String selected){
        String sub_id = selected.substring(0, selected.indexOf("-") - 1).trim();
        int id = Integer.parseInt(sub_id);

        String balance = "";
        for(Object[] arr : accounts){
            if(arr[0].equals(id)){
               balance = arr[2].toString();
            }
        }
        return balance;
    }

    void createCustomerList(ArrayList<Object[]>accounts){
        customers = new ArrayList<>(accounts.size());
        ArrayList<Integer> ids = new ArrayList<>();
        for(int i = 1; i < accounts.size(); i++){
            int customer_id = (Integer)accounts.get(i)[5];
            if(ids.contains(customer_id)){
                continue;
            }
            ids.add(customer_id);
        }
        for(int id : ids){
            customers.add(data.getData(id, "customer").get(1));
        }

    }

    public ArrayList<String[]> getAllTransfers(int accid){
        ArrayList<Object[]> transfers_raw = data.getAllTransfers(accid);

        ArrayList<String[]> transfers = new ArrayList<>();
        for(int i = 1; i < transfers_raw.size(); i++){
            String[] tmp = new String[3];
            Object[] tr = transfers_raw.get(i);
            if(tr[2].equals(accid)){
                tmp[0] = tr[3].toString();
                tmp[1] = "out";
            }else{
                tmp[0] = tr[2].toString();
                tmp[1] = "in";
            }
            tmp[2] = tr[1].toString();
            transfers.add(tmp);
        }
        return transfers;
    }
}

class AdministrationTest{
    public static void main(String[] args) {
        ProdBase data = ProdBase.initialize();
        Administration admin = new Administration(1, data);
        admin.getAllAccounts(1);
        System.out.println(admin.getBalance("1 - Girokonto"));
    }
}