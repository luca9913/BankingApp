package Database;

import java.sql.*;
import java.nio.file.*;
import java.util.ArrayList;
import org.apache.commons.lang3.ArrayUtils;
import java.util.regex.Pattern;
import Person.*;

//(TODO: change ArrayList<String[]> to ArrayList<Object[]> to store Integer and other Objects)

public abstract class Database {
    final String DRIVER = "jdbc:sqlite:";
    final String FOLDER = "src/data/";
    static Connection conn;
    static Statement state;
    ResultSet result;

    ArrayList<Object[]> executeCustomQuery(String sql){
        try {
            if(Pattern.compile(Pattern.quote("select"), Pattern.CASE_INSENSITIVE).matcher(sql).find()){
                result = state.executeQuery(sql);
                return rsToArrayList(result);
            }else{
                int mrows = state.executeUpdate(sql);
                ArrayList<Object[]> update_result= new ArrayList<>(1);
                update_result.add(new Integer[]{mrows});
                return update_result;
            }
        }catch(SQLException e){
            System.err.println("Beim Ausführen der Abfrage ist ein Fehler aufgetreten.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return null;
        }
    }

    ArrayList<Object[]> rsToArrayList(ResultSet rs){
        try {
            //initialize all needed variables and Collections
            ResultSetMetaData rsmeta = rs.getMetaData(); //Get Metadata from the ResultSet
            int columns = rsmeta.getColumnCount(); //Get the column count for the for-loop
            Object[] result_row = new Object[columns]; //initialize the Array to hold the result of every column for each row
            ArrayList<Object[]>result = new ArrayList<>(); //initialize ArrayList which holds the String Arrays for each row

            for(int i = 1; i <= columns; i++){
                result_row[i-1] = rsmeta.getColumnName(i);
            }
            result.add(ArrayUtils.clone(result_row)); //Index 0 always holds a String-Array with column names
            while(rs.next()) { //while there are results left in the Set, fill the result_row Array with the column values
                for (int i = 1; i <= columns; i++) {
                    switch(rsmeta.getColumnTypeName(i)){
                        case "INTEGER": result_row[i-1] = rs.getInt(i);
                        case "REAL": result_row[i-1] = rs.getFloat(i);
                        default: result_row[i-1] = rs.getString(i);
                    }
                }
                if(result.size() <= 10) {
                    result.add(ArrayUtils.clone(result_row));
                }else{
                    result.ensureCapacity(result.size() + 1);
                    result.add(ArrayUtils.clone(result_row));
                }
            }
            if(result.size() == 1) {
                return null; //if the result list only contains one array with the column names, return null
            }else if(result.size() < 10) {
                result.trimToSize();
                return result;
            }else{
                return result; //else return the result list
            }
        }catch(SQLException e){
            System.err.println("Fehler beim Übertragen eines ResultSets in einen StringArray.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return null;
        }
    }
}

class DatabaseTest{

    public static void main(String[] args){
        String[][] strings= {
                {"test", "one", "two", "three"},
                {"this", "is", "how", "we", "do", "it"}
        };
        System.out.println(strings[1].length);
    }
}