package GUI;

import javax.swing.*;

public class HelpMethods {

    //Funktion um Textfelder auf reines Intvorkommen zu prüfen
    private boolean onlyInt(String txtField){
        if(txtField.matches("[0-9]+") && txtField.length() > 2){
            return true;
        }
        else{
            JOptionPane.showMessageDialog(null,"Bitte wiederholen Sie Ihre Eingabe.","Fehlerhafte Eingabe", JOptionPane.CANCEL_OPTION);
            return false;
        }
    }

    //Funktion zum parsen von Integertextfeldern
    private int parseInt(String txtField){
       return Integer.parseInt(txtField);
    }

    //Funktion um Textfelder auf korrekte Doubleeingabe zu prüfen
    private boolean onlyDouble(String txtField){
        try{
            Double proof = Double.parseDouble(txtField);
            return true;
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Bitte wiederholen Sie Ihre Eingabe.","Fehlerhafte Eingabe", JOptionPane.CANCEL_OPTION);
            return false;
        }
    }

    //Funktion zum parsen von Doubletextfeldern
    private double parseDouble(String txtField){
        return Double.parseDouble(txtField);
    }

}
