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
     * @param id Parameter für den Konstruktor.
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
     * Diese metzode gibt die ID der Person zurück.
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
         * Hier Text einfügen
         * @param colnames
         * @param data
         * @return
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
         * Hier Text einfügen
         * @param column
         * @return
         */
        @Override
        public String getColumnName(int column) {
            return colnames[column];
        }

        /**
         * Hier Text einfügen (fill transfer table)
         * @param rowIndex
         * @param columnIndex
         * @return
         */
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data.get(rowIndex)[columnIndex];
        }

        /**
         * Hier Text einfügen
         * @param value
         * @param row
         * @param col
         */
        @Override
        public void setValueAt(Object value, int row, int col) {
            data.get(row)[col] = value;
            fireTableDataChanged();
        }

        /**
         * Hier Text einfügen
         */
        public void clear(){
            data.clear();
        }

        /**
         * Hier Text einfügen
         * @param newdata
         */
        public void update(ArrayList<Object[]> newdata) {
            this.data = newdata;
            fireTableDataChanged();
        }
    }
}
