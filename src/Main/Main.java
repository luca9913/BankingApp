package Main;

import Database.*;
import GUI.GUI_Login.GUI_Login;
import GUI.GUI_Banker.*;
import GUI.GUI_Customer.GUI_Customer;
import Person.Person;
import Login.Login;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        GUI_Customer wneView = new GUI_Customer();
        wneView.setVisible(true);

        /*Person user = null;
        AuthBase authBase = AuthBase.initialize();
        Login loginObjekt = new Login(authBase, user);
        GUI_Login newView = new GUI_Login(loginObjekt);
        newView.setVisible(true);
        ProdBase prodBase = ProdBase.initialize();
        user.update(prodBase);

        if(user.getUid() < 1000)
        {
           GUI_Banker ewnView = new GUI_Banker();
           ewnView.setVisible(true);
        }else
        {
            GUI_Customer wneView = new GUI_Customer();
            wneView.setVisible(true);
        }*/
    }
}
