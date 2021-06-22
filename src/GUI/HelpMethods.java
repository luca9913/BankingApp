package GUI;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Die Klasse enthält einige hilfreiche Methoden für die GUI´s, zum Beispiel
 * Methoden für die Wertüberprüfung bei Textfeldern.
 */
public class HelpMethods {

    /**
     * Diese Methode prüft Textfelder auf reines Intvorkommen.
     * @param txtField Dieser Parameter enthält den Inhalt des zu prüfenden Textfeldes.
     * @return Je nach Prüfungsergebnis wird entweder ein true oder ein false zurückgegeben.
     */
    private boolean onlyInt(String txtField){
        if(txtField.matches("[0-9]+") && txtField.length() > 2){
            return true;
        }
        else{
            JOptionPane.showMessageDialog(null,"Bitte wiederholen Sie Ihre Eingabe.","Fehlerhafte Eingabe", JOptionPane.CANCEL_OPTION);
            return false;
        }
    }

    /**
     * Diese Methode wird zum parsen von Textfeldern zu Int benötigt.
     * @param txtField Dieser Parameter enthält den Inhalt des zu parsenden Textfeldes.
     * @return Es wird der geparste Int-Wert zurückgegeben.
     */
    private int parseInt(String txtField){
       return Integer.parseInt(txtField);
    }

    /**
     * Diese Methode prüft Textfelder auf reines Doublevorkommen .
     * @param txtField Dieser Parameter enthält den Inhalt des zu prüfenden Textfeldes.
     * @return Je nach Prüfungsergebnis wird entweder ein true oder ein false zurückgegeben.
     */
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

    /**
     * Diese Methode wird zum parsen von Textfeldern zu Double benötigt.
     * @param txtField Dieser Parameter enthält den Inhalt des zu parsenden Textfeldes.
     * @return Es wird der geparste Double-Wert zurückgegeben.
     */
    private double parseDouble(String txtField){
        return Double.parseDouble(txtField);
    }

    public boolean onlyString(String txtField, boolean acceptSpace) {
//                "^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$"
        if(acceptSpace) {
            if(txtField.matches("[A-Za-z ]*")) {
                return true;
            }
            return false;
        } else {
            if(txtField.matches("[A-Za-z]*")) {
                return true;
            }
            return false;
        }

    }

    public boolean correctDateFormat(String pattern, String date) {
        SimpleDateFormat formatter=new SimpleDateFormat(pattern);
        try {
            Date newDate = formatter.parse(date);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public Date convertStringToDate(String pattern, String date){
        SimpleDateFormat formatter=new SimpleDateFormat(pattern);
        Date newDate = null;
        try {
            newDate=formatter.parse(date);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Das Datum muss im Format DD.MM.YYYY angegeben werden!","Fehlerhafte Eingabe", JOptionPane.CANCEL_OPTION);
        }
        return newDate;
    }

}
