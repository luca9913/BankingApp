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
import java.nio.charset.Charset;
import java.util.Random;

/**
 * Hier Text einfügen
 */
public class Main {

    /**
     * Hier Text einfügen
     * @param args
     */
    public static void main(String[] args) {

        Login log = new Login();
        GUI_Login newView = new GUI_Login(log);
        newView.setVisible(true);

    }
}
