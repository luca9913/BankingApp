package Person;

import Database.AuthBase;
import Database.ProdBase;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Die abstrakte Klasse "Person" ist die Vaterklasse für die Unterklassen "Banker" und "Customer".
 */
public abstract class Person {

    public int id;
    public String name;
    public String preName;
    public String birthDate;
    public int zip;
    public String city;
    public String address;
    public String email;
    public String tel;
    public ProdBase data = ProdBase.initialize();
    public AuthBase auth = AuthBase.initialize();

    /**
     * Standardkonstruktor der Klasse "Person".
     * @param id Parameter des Konstruktors.
     */
    Person(int id){
        this.id = id;
    }

    /**
     * Diese Methode schließt die Datenbankverbindungen.
     */
    public void closeConnections(){
        data.close();
        auth.close();
    }

    /**
     * Diese Methode gibt die ID des Personenobjektes zurück.
     * @return Gibt die ID des Objektes zurück.
     */
    public int getId() {
        return id;
    }

    /**
     * Diese Methode gibt den Namen des Objektes zurück.
     * @param id Als Parameter wird die ID des Objektes genutzt.
     * @return Es wird der String mit dem Namen zurückgegeben.
     */
    public String getName(int id) {
        return data.getData(id, "customer").get(0)[1].toString() + " " + data.getData(id, "customer").get(0)[2].toString();
    }

    /**
     * Diese Klasse ist für das Füllen von JTables zuständig.
     */
    public class TableData extends AbstractTableModel {
        String[] colnames;
        ArrayList<Object[]> data;

        /**
         * Konstruktor der Klasse <code>TableData</code>: Weißt den Variablen in der Klasse Werte zu, die als Parameter übergeben werden
         * @param colnames String Array mit den Spalten der späteren Tabelle
         * @param data ArrayList, die die Daten der jeweiligen Spalten beinhaltet
         */
        TableData(String[] colnames, ArrayList<Object[]> data) {
            this.colnames = colnames;
            this.data = data;
        }

        /**
         * Diese Methode gibt die Anzahl der Zeilen der Tabelle zurück.
         * @return Der Rückgabewert ist int.
         */
        @Override
        public int getRowCount() {
            return data.size();
        }

        /**
         * Diese Methode gibt die Anzahl der Reihen der Tabelle zurück.
         * @return Der Rückgabewert ist int.
         */
        @Override
        public int getColumnCount() {
            return colnames.length;
        }

        /**
         * Gibt den Namen einer Spalte in der Tabelle zurück
         * @param column Nummer der Spalte in der Tabelle
         * @return Name der Spalte
         */
        @Override
        public String getColumnName(int column) {
            return colnames[column];
        }

        /**
         * Gibt den Wert in einer bestimmten Zelle in der Tabelle zurück
         * @param rowIndex Reihe der abgefragten Zelle
         * @param columnIndex Spalte der abgefragten Zelle
         * @return Inhalt der Zelle als Object
         */
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data.get(rowIndex)[columnIndex];
        }

        /**
         * Setzt den Wert in einer bestimmten Zelle einer Tabelle
         * @param value Zu setztender Wert
         * @param row Reihe der Zelle, in der der Wert gesetzt werden soll
         * @param col Spalte der Zelle, in der der Wert gesetzt werden soll
         */
        @Override
        public void setValueAt(Object value, int row, int col) {
            data.get(row)[col] = value;
            fireTableDataChanged();
        }

        /**
         * Entfernt alle Elemente
         */
        public void clear(){
            data.clear();
        }

        /**
         * Aktualisiert die Daten, die unter <code>data</code> gespeichert sind
         * @param newdata Neue Daten Als ArrayList von Object Arrays
         */
        public void update(ArrayList<Object[]> newdata) {
            this.data = newdata;
            fireTableDataChanged();
        }
    }
}
