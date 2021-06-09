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
        int i = 1;
        for(Object[] objarr : accounts){
            customers.add(data.getData((Integer)objarr[0],"customer"));
        }
    }
}

class AdministrationTest{
    public static void main(String[] args){

    }
}