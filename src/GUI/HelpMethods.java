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
    public boolean onlyInt(String txtField){
        if(txtField.matches("[0-9]+") && txtField.length() > 2){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Diese Methode wird zum parsen von Textfeldern zu Int benötigt.
     * @param txtField Dieser Parameter enthält den Inhalt des zu parsenden Textfeldes.
     * @return Es wird der geparste Int-Wert zurückgegeben.
     */
    public int parseInt(String txtField){
       return Integer.parseInt(txtField);
    }

    /**
     * Diese Methode prüft Textfelder auf reines Doublevorkommen.
     * @param txtField Dieser Parameter enthält den Inhalt des zu prüfenden Textfeldes.
     * @return Je nach Prüfungsergebnis wird entweder ein true oder ein false zurückgegeben.
     */
    private boolean onlyDouble(String txtField){
        try{
            Double proof = Double.parseDouble(txtField);
            return true;
        }
        catch (Exception ex) {
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

    public boolean onlyString(String txtField, boolean acceptSpace, int minLength) {
//                "^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$"
        if(txtField.length() > minLength) {
            if(acceptSpace) {
                if(txtField.matches("[A-Za-z -]*")) {
                    return true;
                }
            } else {
                if(txtField.matches("[A-Za-z-]*")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Diese Methode prüft Textfelder auf das richtige Format bei der Eingabe eines Datums.
     * @param date Dieser Parameter enthält den Inhalt des zu prüfenden Textfeldes.
     * @return Je nach Prüfungsergebnis wird entweder ein true oder ein false zurückgegeben.
     */
    public boolean correctDateFormat(String date) {
        SimpleDateFormat formatter=new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date newDate = formatter.parse(date);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Das Datum muss im Format DD.MM.YYYY angegeben werden!","Fehlerhafte Eingabe", JOptionPane.CANCEL_OPTION);
            return false;
        }
        return true;
    }

    /**
     * Diese Methode wird zum parsen von Textfeldern zu Date benötigt.
     * @param date Dieser Parameter enthält den Inhalt des zu parsenden Textfeldes.
     * @return Es wird der geparste Datums-Wert zurückgegeben.
     */
    public Date convertStringToDate(String date){
        SimpleDateFormat formatter=new SimpleDateFormat("dd.MM.yyyy");
        Date newDate = null;
        try {
            newDate=formatter.parse(date);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Das Datum muss im Format DD.MM.YYYY angegeben werden!","Fehlerhafte Eingabe", JOptionPane.CANCEL_OPTION);
        }
        return newDate;
    }

}
