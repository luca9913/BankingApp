package GUI.GUI_Login;

import Login.Login;

import java.awt.*;

public class GUI_Main {

    public static void main(String[] args) {
        Login loginObjekt = new Login();
        GUI_Login newView = new GUI_Login(loginObjekt);
        newView.setVisible(true);

    }
}
