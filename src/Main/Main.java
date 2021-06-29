package Main;


import GUI.GUI_Customer_Connector;
import Database.*;
import GUI.GUI_Login.GUI_Login;
import GUI.GUI_Banker.*;
import GUI.GUI_Customer.GUI_Customer;
import GUI.HelpMethods;
import Person.Person;
import Login.Login;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        Login log = new Login();
        GUI_Login newView = new GUI_Login(log);
        newView.setVisible(true);

        HelpMethods h = new HelpMethods();
        h.convertStringIntoDateFormat("02.06.2002");
        h.convertStringIntoDateFormat("05.12.1977");
    }
}
