package Model;

import Controller.edit_customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

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


    public static ObservableList<Customer> createCustomerList() throws SQLException {
        ObservableList<Customer> myList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
             Customer c = new Customer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(10));
             myList.add(c);
        }
        return myList;
    }

    public static int deleteCustomer(int customerID) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0)
            System.out.println(rowsAffected + " row deleted from table");
        else {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Deletion Error:");
            alert1.setHeaderText("Customer not found");
            alert1.showAndWait();
        }
        return rowsAffected;
    }


    public static int updateCustomer(Customer cust) throws SQLException {
        int rowsAffected = 0;
        try {
            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ps.setString(1, cust.getCustomerName());
            ps.setString(2, cust.getCustomerAddress());
            ps.setString(3, cust.getCustomerPostalCode() );
            ps.setString(4, cust.getCustomerPhone());
            ps.setInt(5, cust.getCustomerDivision());
            ps.setInt(6, cust.getCustomerID());
            rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                Alert updateAlert = new Alert(Alert.AlertType.INFORMATION);
                updateAlert.setTitle("Update successful!");
                updateAlert.setHeaderText("Customer updated successfully!");
                updateAlert.showAndWait();
                return rowsAffected;
            }

        } catch (Exception e) {
            Alert errorBox = new Alert(Alert.AlertType.ERROR);
            errorBox.setTitle("error");
            errorBox.setHeaderText("All fields must have valid data");
            errorBox.showAndWait();
        }
        return rowsAffected;
    }





}
