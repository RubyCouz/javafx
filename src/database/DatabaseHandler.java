package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Cédric Cousin-Ruby
 */
public class DatabaseHandler {

    private static DatabaseHandler handler;
    // définition du chemin de création de la base de données => un dossier database sera créé dans le dossier "sources packages"
    private static final String DB_URL = "jdbc:derby:database;create=true";
    private Connection conn = null;
    private Statement stmt = null;

    public DatabaseHandler() {
        createConnection();
        setupBookTable();
        setupMemberTable();
    }

    void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            conn = DriverManager.getConnection(DB_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setupMemberTable() {
        String TABLE_NAME = "MEMBER";
        try {

            stmt = conn.createStatement();

            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);

            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists. Ready to go !!");
            } else {
                stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "id varchar(200) primary key, \n"
                        + "name varchar(200), \n"
                        + "mobile varchar(10), \n"
                        + "email varchar(200)"
                        + ")");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + "----- setupDatabase");
        } finally {

        }
    }

    void setupBookTable() {
        String TABLE_NAME = "BOOK";
        System.out.println("CREATE TABLE " + TABLE_NAME + "("
                + "id varchar(200) primary key, \n"
                + "title varchar(200), \n"
                + "author varchar(200), \n"
                + "publisher varchar(200), \n"
                + "isAvail boolean default true"
                + ")");
        try {
            stmt = conn.createStatement();

            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);

            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists. Ready to go !!");
            } else {
                stmt.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "id varchar(200) primary key, \n"
                        + "title varchar(200), \n"
                        + "author varchar(200), \n"
                        + "publisher varchar(200), \n"
                        + "isAvail boolean default true"
                        + ")");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage() + "----- setupDatabase");
        } finally {

        }

    }

    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Exception query at execQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        } finally {

        }
        return result;
    }

    public boolean execAction(String qu) {
        try {
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return false;
        } finally {

        }
    }
}
