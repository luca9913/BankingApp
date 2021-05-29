package Database;

import java.sql.*;
import java.nio.file.*;

abstract class Database{
    final static String DRIVERSTRING = "jdbc:sqlite:";
    static Path path;
    static Connection conn;

    static Connection initialize(String p){ //Die Pfad-Strings könnten irgendwo als final deklariert werden
        path = Paths.get(p);
        if(!(path.toString().isEmpty())){
            try {
                conn = DriverManager.getConnection(DRIVERSTRING + path);
            }catch(SQLException e){
                System.err.println("Fehler beim Initialisieren der Datenbank.");
                System.err.print("Fehlermeldung: ");
                e.printStackTrace();
            }
        }else{
            System.err.println("Instance of Authentication Database already created. Only one Instance allowed.");
        }
        return conn;
    }
    abstract ResultSet getData(String sql);
    abstract int updateData(String sql);
}

class AuthBase extends Database{

    private AuthBase(){} //Konstruktor auf private setzen, damit nur eine Instanz erzeugt werden kann

    ResultSet getData(String sql){
        ResultSet rs = null;

        try {
            Statement state = conn.createStatement();
            rs = state.executeQuery(sql);
        }catch(SQLException e){
            System.err.println("Fehler beim Ausführen des SQL-Statements.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
        }
        return rs;
    }

    int updateData(String sql){
        int changedlines = 0;

        try {
            Statement state = conn.createStatement();
            changedlines = state.executeUpdate(sql);
        }catch(SQLException e){
            System.err.println("Fehler beim Ausführen des SQL-Statements.");
            System.err.print("Fehlermeldung: ");
            e.printStackTrace();
        }
        return changedlines;
    }
}

class DatabaseTest{

    public static void main(String[] args){
        try {
            Connection test = AuthBase.initialize("src/data/auth.db");
            ResultSet rs = test.createStatement().executeQuery("SELECT * from user");
            System.out.println("ID: " + rs.getString("user_id"));
            System.out.println("PW Hash: " + rs.getString("pw_hash"));
        }catch(SQLException e){
            System.err.println("Fehler beim Auslesen der Datenbank!");
            e.printStackTrace();
        }
    }
}