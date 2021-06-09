package Database;

import java.sql.*;
import java.util.ArrayList;
import org.apache.commons.lang3.ArrayUtils;
import java.util.regex.Pattern;

/**
 * Die abstrakte Datenbank-Klasse enthält alle gemeinsamen Felder und Methoden zum Arbeiten mit den beiden Datenbanken.
 * Sie ist die Eltern-Klasse der "AuthBase" und der "ProdBase" Klassen.
 */

public abstract class Database {
    /**Enthält den Namen des Treibers, mit dem Datenbankzugriff gesteuert wird. */
    final String DRIVER = "jdbc:sqlite:";
    /**Enthält den Pfad der Datenbank-Dateien - auth.db & production.db.*/
    final String FOLDER = "src/data/";
    /**Datenbank-Verbindung aus dem Paket java.sql
     * @link java.sql.Connection */
    static Connection conn;
    /**Datenbank-Statement zur Ausführung von Abfragen
     * @link java.sql.Statement*/
    static Statement state;
    /**Speicherung der Rückgabe einer Datenbank-Abfrage
     * @link java.sql.ResultSet*/
    ResultSet result;

    /**Ausführen von beliebigen SQL(ite)-Statements
     * @param sql Ein gültiges <a href="https://sqlite.org/index.html">SQLite</a>-Statement
     * @return eine ArrayList mit Object-Arrays als Elemente. Bei einer SELECT Abfrage enthält die Liste so viele Elemente wie die Datenbank Zeilen zurückgegeben hat.
     * Bei allen anderen Statements ist ein Integer-Array in der Liste enthalten, in dem an Stelle [0] die Zahl der betroffenen Zeilen der Datenbank steht.
     */
    ArrayList<Object[]> executeCustomQuery(String sql){
        try {
            //searches for the 'SELECT' string in the statement
            if(Pattern.compile(Pattern.quote("select"), Pattern.CASE_INSENSITIVE).matcher(sql).find()){
                //if the statement is a SELECT statement
                result = state.executeQuery(sql); //save the result into the ResultList
                return rsToArrayList(result); //return the ArrayList containing Objects for each selected row
            }else{
                //if the statement doesn't contain the 'SELECT' string
                int mrows = state.executeUpdate(sql); //save the number of affected rows in a variable
                ArrayList<Object[]> update_result= new ArrayList<>(1); //initialize a new ArrayList with one element
                update_result.add(new Integer[]{mrows}); //add the number of rows as an Integer-Array with 1 entry to the ArrayList
                return update_result; //return the ArrayList containing the number of affected rows
            }
        }catch(SQLException e){ //catch an SQL-Exception
            //print a custom error message and the Exception stacktrace to the error log
            System.err.println("Beim Ausführen der Abfrage ist ein Fehler aufgetreten.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
            return null;
        }
    }

    /**Umwandeln eines SQLite-ResultSets in eine Array-Liste mit Object-Arrays als Elemente.
     * Die Länge der Liste ist gleich der ausgelesenen Reihen (rows) der Tabelle. Die Länge der Object-Arrays ist gleich der Spaltenanzahl der jeweiligen Tabelle.
     * @param rs Das ResultSet, welches in eine Array-Liste konvertiert werden soll
     * @return Die resultierende, konvertierte Array-Liste
     */
    ArrayList<Object[]> rsToArrayList(ResultSet rs){
        try {
            //initialize all needed variables and Collections
            ResultSetMetaData rsmeta = rs.getMetaData(); //Get Metadata from the ResultSet - to determine the column names
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