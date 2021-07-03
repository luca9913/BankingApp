package Login;

import Database.*;
import GUI.GUI_Banker.GUI_Banker;
import GUI.GUI_Customer_Connector;
import Person.Banker;
import Person.Person;
import Person.Customer;

/**
 * Dies ist die Login Klasse, welche Methoden für die Login-GUI enthält.
 */
public class Login {

    private AuthBase authDatabase = AuthBase.initialize();
    private Person user = null;

    /**
     * Diese Methode hasht das eingegebene Passwort der Login-GUI.
     * @param password Dieser parameter enthäkt das zu hashende Passwort.
     * @return Es wird das gehashte Passwort zurückgegeben.
     */
    private int hashen(String password){
        return password.hashCode();
    }

    /**
     * Diese Methode gleicht die eingegebene User-ID und das eingegebene Passwort mit der Datenbank ab.
     * @param userID Dieser Parameter enthält die User-ID aus dem entsprechenden Textfeld.
     * @param password Dieser Parameter enthält das Passwort aus dem entsprechenden Textfeld.
     * @return Wenn der Datenbankabgleich erfolgreich war, dann wird "true" zurückgegeben,
     * andernfalls wird "false" zurück gegeben.
     */
    public boolean databaseComparison(int userID, String password) {

        int pwHash = hashen(password);
        try {
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
        } catch (Exception e) {
            return false;
        }
    }
}
