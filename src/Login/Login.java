package Login;

import Database.*;
import GUI.GUI_Banker.GUI_Banker;
import GUI.GUI_Customer_Connector;
import Person.Banker;
import Person.Person;
import Person.Customer;

/**
 * Hier Text einfügen
 */
public class Login {

    private String password;
    private String pwHash;
    private int userID = 0;
    private boolean valid;
    private AuthBase authDatabase = AuthBase.initialize();
    private Person user = null;

    /**
     * Diese Methode hasht das Passwort
     * @param password
     * @return
     */
    private int hashen(String password){
        return password.hashCode();
    }

    /**
     * //databaseComparison vergleicht die Eingabe mit der Datenbank und gibt mit "true" zurück ob ein passender Tupel gefunden wurde.
     * @param userID
     * @param password
     * @return
     */
    public boolean databaseComparison(int userID, String password) {

        int pwHash = hashen(password);
        Object[] authSet = authDatabase.getAuthSet(userID).get(0);

        if (pwHash == authDatabase.getHash(userID)) {
            System.out.println("Login erfolgreich!");
            System.out.println((Integer)authSet[2]);
            if( (Integer)authSet[2] <= 0){
                System.out.println("Customer-ID (" + userID + ") <= 0 - Banker Login - Banker GUI öffnen");
                Banker banker = new Banker((Integer)authSet[3]);
                GUI_Banker newBankerView = new GUI_Banker(banker);
                newBankerView.setVisible(true);
                authDatabase.close();
            } else {
                System.out.println("Login-ID (" + userID + ") über 1000 - Customer Login - Customer GUI öffnen");
                Customer customer = new Customer((Integer)authSet[2]);
                GUI_Customer_Connector.openCustomer(customer);
            }
            return true;
        }
        return false;
    }
}
