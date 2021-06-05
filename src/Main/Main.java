package Main;

import GUI.GUI_Login.GUI_Login;
import GUI.GUI_Banker.GUI_Banker;
import GUI.GUI_Customer.GUI_Customer;

import Login.Login;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        /*Login loginObjekt = new Login();
        GUI_Login newView = new GUI_Login(loginObjekt);
        newView.setVisible(true);
        */

        //Test
        GUI_Customer customerObject = new GUI_Customer();
        customerObject.setVisible(true);
    }
}
