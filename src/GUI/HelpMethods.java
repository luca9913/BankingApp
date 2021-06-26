package GUI;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;


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
            JOptionPane.showMessageDialog(null,"Bitte wiederholen Sie Ihre Eingabe.","Fehlerhafte Eingabe", JOptionPane.CANCEL_OPTION);
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

    public boolean onlyString(String txtField, boolean acceptSpace, int minLength) {
//                "^(?=.*[A-Z])(?=.*[0-9])[A-Z0-9]+$"
        if(txtField.length() > minLength) {
            if(acceptSpace) {
                if(txtField.matches("[A-Za-z äüöÄÜÖ-]*")) {
                    return true;
                }
            } else {
                if(txtField.matches("[A-Za-z- äüöÄÜÖ]*")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Diese Methode prüft Textfelder auf das richtige Format bei der Eingabe eines Datums.
     * @param dateString Dieser Parameter enthält den Inhalt des zu prüfenden Textfeldes.
     * @return Je nach Prüfungsergebnis wird entweder ein true oder ein false zurückgegeben.
     */
    public boolean correctDateFormat(String dateString, boolean inPast) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            if (sdf.format(sdf.parse(dateString)).equals(dateString)) {
                if(inPast){
                    DateTimeFormatter yearFormat = DateTimeFormatter.ofPattern("yyyy");
                    DateTimeFormatter monthFormat = DateTimeFormatter.ofPattern("MM");
                    DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("dd");
                    SimpleDateFormat sYear = new SimpleDateFormat("yyyy");
                    SimpleDateFormat sMonth = new SimpleDateFormat("MM");
                    SimpleDateFormat sDay = new SimpleDateFormat("dd");
                    LocalDateTime now = LocalDateTime.now();

                    if(this.parseInt(sYear.format(sdf.parse(dateString))) < this.parseInt(yearFormat.format(now))){
                        return true;
                    } else if (this.parseInt(sYear.format(sdf.parse(dateString))) == this.parseInt(yearFormat.format(now))){
                        if(this.parseInt(sMonth.format(sdf.parse(dateString))) < this.parseInt(monthFormat.format(now))){
                            if(this.parseInt(sDay.format(sdf.parse(dateString))) < this.parseInt(dayFormat.format(now))){
                                return true;
                            }
                        } else if (this.parseInt(sMonth.format(sdf.parse(dateString))) < this.parseInt(monthFormat.format(now))){
                            return true;
                        }
                    }
                } else {
                    return true;
                }
            }
        } catch (Exception pe) {
        }

        return false;

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
