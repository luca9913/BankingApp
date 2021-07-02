package Person;

import Database.AuthBase;
import Database.ProdBase;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Date;

/**
 * Die abstrakte Klasse "Person" ist die Vaterklasse für die Unterklassen "Banker" und "Customer".
 */
public abstract class Person {

    //Variablen
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
     * Hier Text einfügen
     * @param
     * @return
     */
    Person(int id){
        this.id = id;
    }

    /**
     * Hier Text einfügen
     * @param
     * @return
     */
    public void closeConnections(){
        data.close();
        auth.close();
    }

    /**
     * Hier Text einfügen
     * @param
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Hier Text einfügen
     * @param
     * @return
     */
    public String getName(int id) {
        return data.getData(id, "customer").get(0)[1].toString() + " " + data.getData(id, "customer").get(0)[2].toString();
    }

    /**
     * Hier Text einfügen
     * @param
     * @return
     */
    public class TableData extends AbstractTableModel {
        String[] colnames;
        ArrayList<Object[]> data;

        /**
         * Hier Text einfügen
         * @param
         * @return
         */
        TableData(String[] colnames, ArrayList<Object[]> data) {
            this.colnames = colnames;
            this.data = data;
        }

        /**
         * Hier Text einfügen
         * @param
         * @return
         */
        @Override
        public int getRowCount() {
            return data.size();
        }

        /**
         * Hier Text einfügen
         * @param
         * @return
         */
        @Override
        public int getColumnCount() {
            return colnames.length;
        }

        /**
         * Hier Text einfügen
         * @param
         * @return
         */
        @Override
        public String getColumnName(int column) {
            return colnames[column];
        }

        /**
         * Hier Text einfügen
         * @param
         * @return
         */
        @Override
        //fill transfer table
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data.get(rowIndex)[columnIndex];
        }

        /**
         * Hier Text einfügen
         * @param
         * @return
         */
        @Override
        public void setValueAt(Object value, int row, int col) {
            data.get(row)[col] = value;
            fireTableDataChanged();
        }

        /**
         * Hier Text einfügen
         * @param
         * @return
         */
        public void clear(){
            data.clear();
        }

        /**
         * Hier Text einfügen
         * @param
         * @return
         */
        public void update(ArrayList<Object[]> newdata) {
            this.data = newdata;
            fireTableDataChanged();
        }
    }
}
