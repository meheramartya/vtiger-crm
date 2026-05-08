package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtils {

    private Connection con;
    
    // Hardcoded database credentials
    private String dbUrl = "jdbc:mysql://localhost:3306/advproject";
    private String dbUsername = "root";
    private String dbPassword = "root";

    /**
     * Connect to DB using hardcoded credentials
     */
    public void connectToDB() throws SQLException {
        con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }

    /**
     * Fetch data (SELECT query)
     */
    public ResultSet fetchDataFromDatabase(String query) throws SQLException {
        Statement stat = con.createStatement();
        return stat.executeQuery(query);
    }

    /**
     * Update data (INSERT / UPDATE / DELETE)
     */
    public int updateDataToDB(String query) throws SQLException {
        Statement stat = con.createStatement();
        return stat.executeUpdate(query);
    }

    /**
     * Close DB connection
     */
    public void disconnectWithDB() throws SQLException {
        if (con != null && !con.isClosed()) {
            con.close();
        }
    }
}