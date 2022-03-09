package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;

import java.sql.*;

public abstract class JDBC {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static String password = "Passw0rd!"; // Password
    public static Connection connection;  // Connection Interface


    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }


    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }


    public static ObservableList<Country> getCountryObjects() throws SQLException {
        ObservableList<Country> myList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Country c = new Country(rs.getInt(1), rs.getString(2));
            myList.add(c);
        }
        return myList;
    }


    public static ObservableList<String> getCountryNames() throws SQLException {
        ObservableList<String> myList = FXCollections.observableArrayList();
        String sql = "SELECT Country FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
            myList.add(rs.getString(1));
        return myList;
    }


    public static ObservableList<String> getAllDivisionNames() throws SQLException {
        ObservableList<String> myList = FXCollections.observableArrayList();
        String sql = "SELECT Division FROM First_Level_Divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
            myList.add(rs.getString(1));
        return myList;
    }


    public static ObservableList<String> getCountrySpecificDivisionNames(int country_ID) throws SQLException {
        ObservableList<String> myList = FXCollections.observableArrayList();
        String sql = "SELECT Division FROM First_Level_Divisions WHERE Country_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, country_ID);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
            myList.add(rs.getString(1));
        return myList;
    }


    public static int returnDivisionID(String name) throws SQLException {
        int i = 0;
        String sql = "SELECT Division_ID FROM First_Level_Divisions WHERE Division = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
            i = rs.getInt(1);
        return i;
    }









}
