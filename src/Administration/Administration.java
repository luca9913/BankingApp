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

    void createCustomerList(ArrayList<Object[]>accounts){
        customers = new ArrayList<>();
        ArrayList<Integer> added = new ArrayList<>();
        for(int i = 1; i < accounts.size(); i++){
            Object[] objarr = accounts.get(i);
            if(i >= 10){
                customers.ensureCapacity(i);
                added.ensureCapacity(i);
            }
            objarr = data.getData((Integer) objarr[5], "customer");
            if(!(added.contains(objarr[0]))) {
                customers.add(objarr);
                added.add((Integer)objarr[0]);
            }
        }
    }
}

class AdministrationTest{
    public static void main(String[] args) {
        ProdBase data = ProdBase.initialize();
        Administration test = new Administration(1, data);
        test.getAllAccounts(test.banker_id);
        System.out.println(test.accounts.get(1)[5]);
        test.createCustomerList(test.accounts);
        for(Object[] obj : test.customers) {
            System.out.println("Prename: " + obj[1] + " Name: " + obj[2]);
        }
    }
}