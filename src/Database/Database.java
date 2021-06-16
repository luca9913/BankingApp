package Database;

import java.sql.*;
import java.util.ArrayList;
import org.apache.commons.lang3.ArrayUtils;
import java.util.regex.Pattern;

/**
 * Die abstrakte Datenbank-Klasse enthält alle gemeinsamen Felder und (abstrakten) Methoden zum Arbeiten mit den beiden Datenbanken.
 * Sie ist die Eltern-Klasse der "AuthBase" und der "ProdBase" Klassen.
 */
public abstract class Database {
    /**Enthält den Namen des Treibers, mit dem der Datenbankzugriff gesteuert wird.*/
    final String DRIVER = "jdbc:sqlite:";
    /**Enthält den Pfad der Datenbank-Dateien - auth.db and production.db.*/
    final String FOLDER = "src/data/";

    /**Ausführen von beliebigen SQL(ite)-Statements
     * @param sql Ein gültiges <a href="https://sqlite.org/index.html">SQLite</a>-Statement
     * @return eine ArrayList mit Object-Arrays als Elemente. Bei einer SELECT Abfrage enthält die Liste so viele Elemente wie die Datenbank Zeilen zurückgegeben hat.
     * Bei allen anderen Statements ist ein Integer-Array in der Liste enthalten, in dem an Stelle [0] die Zahl der betroffenen Zeilen der Datenbank steht.
     */
    abstract ArrayList<Object[]> executeCustomQuery(String sql);

    /**Umwandeln eines SQLite-ResultSets in eine Array-Liste mit Object-Arrays als Elemente.
     * Die Länge der Liste ist gleich der ausgelesenen Reihen (rows) der Tabelle + ein Object-Array an Stelle 0, der die Spaltennamen enthält. Die Länge der Object-Arrays ist gleich der Spaltenanzahl der jeweiligen Tabelle.
     * @param rs Das ResultSet, welches in eine Array-Liste konvertiert werden soll
     * @return Die resultierende, konvertierte Array-Liste (sie hat die Länge 1, wenn keine Reihen gefunden wurden).
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
                        case "INTEGER": result_row[i-1] = (rs.getInt(i)); break;
                        case "REAL": result_row[i-1] = rs.getDouble(i); break;
                        default: result_row[i-1] = rs.getString(i); break;
                    }
                }
                if(result.size() <= 10) {
                    result.add(ArrayUtils.clone(result_row));
                }else{
                    result.ensureCapacity(result.size() + 1);
                    result.add(ArrayUtils.clone(result_row));
                }
            }
            if(result.size() < 10) {
                result.trimToSize();
            }
            return result;
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
        System.out.println("test123".hashCode());
    }
}