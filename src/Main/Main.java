package Main;


import GUI.GUI_Customer_Connector;
import Database.*;
import GUI.GUI_Login.GUI_Login;
import GUI.GUI_Banker.*;
import GUI.GUI_Customer.GUI_Customer;
import Person.Person;
import Login.Login;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        AuthBase authBase = AuthBase.initialize();
        Login log = new Login(authBase);
        GUI_Login wneView = new GUI_Login(log);
        wneView.setVisible(true);

    }
}
