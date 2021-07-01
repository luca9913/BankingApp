package Person;

import Database.AuthBase;
import Database.ProdBase;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Date;

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

    //Konstruktor
    Person(int id){
        this.id = id;
    }

    public void closeConnections(){
        data.close();
        auth.close();
    }

    public int getId() {
        return id;
    }

    public String getName(int id) {
        return data.getData(id, "customer").get(0)[1].toString() + " " + data.getData(id, "customer").get(0)[2].toString();
    }

    public class TableData extends AbstractTableModel {
        String[] colnames;
        ArrayList<Object[]> data;

        TableData(String[] colnames, ArrayList<Object[]> data) {
            this.colnames = colnames;
            this.data = data;
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return colnames.length;
        }

        @Override
        public String getColumnName(int column) {
            return colnames[column];
        }

        @Override
        //fill transfer table
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data.get(rowIndex)[columnIndex];
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            data.get(row)[col] = value;
            fireTableDataChanged();
        }

        public void clear(){
            data.clear();
        }

        public void update(ArrayList<Object[]> newdata) {
            this.data = newdata;
            fireTableDataChanged();
        }
    }
}
