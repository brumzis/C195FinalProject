package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import java.sql.*;
import java.time.LocalDateTime;


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


    public static void openConnection() {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        } catch(Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }


    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        } catch(Exception e) {
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


    public static int returnContactID(String name) throws SQLException {
        int i = 0;
        String sql = "SELECT Contact_ID FROM contacts WHERE Contact_Name = ?";
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
        if (rowsAffected > 0) {
            System.out.println(rowsAffected + " row deleted from table");
            Alert alertbox = new Alert(Alert.AlertType.CONFIRMATION);
            alertbox.setTitle("Confirmation");
            alertbox.setHeaderText("Success!");
            alertbox.setContentText("Customer has been removed from DB");
            alertbox.showAndWait();
        }
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


    public static ObservableList<String> getContactNames() throws SQLException {
        ObservableList<String> myList = FXCollections.observableArrayList();
        String sql = "SELECT Contact_Name FROM contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
            myList.add(rs.getString(1));
        return myList;
    }


    public static ObservableList<Contact> getContactObjects() throws SQLException {
        ObservableList<Contact> myList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Contact c = new Contact(rs.getInt(1), rs.getString(2), rs.getString(3));
            myList.add(c);
        }
        return myList;
    }


    public static ObservableList<String> getHours() {
        ObservableList myList = FXCollections.observableArrayList();
        myList.add("00");myList.add("01");myList.add("02");myList.add("03");myList.add("04");
        myList.add("05");myList.add("06");myList.add("07");myList.add("08");myList.add("09");
        myList.add("10");myList.add("11");myList.add("12");myList.add("13");myList.add("14");
        myList.add("15");myList.add("16");myList.add("17");myList.add("18");myList.add("19");
        myList.add("20");myList.add("21");myList.add("22");myList.add("23");
        return myList;
    }


    public static ObservableList<String> getMinutes() {
        ObservableList myList = FXCollections.observableArrayList();
        myList.add("0");
        myList.add("15");
        myList.add("30");
        myList.add("45");
        return myList;
    }


    public static ObservableList<Appointment> createAppointmentList() throws SQLException {
        ObservableList<Appointment> myList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Appointment app = new Appointment(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(14), rs.getString(5), DateTimeUtility.convertFromUTC((LocalDateTime)rs.getObject("Start")), DateTimeUtility.convertFromUTC((LocalDateTime)rs.getObject("End")), rs.getInt(12), rs.getInt(13));
            myList.add(app);
        }
        return myList;
    }


    public static ObservableList<Appointment> createContactSchedule(int contactID) throws SQLException {
        ObservableList<Appointment> myList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contactID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Appointment app = new Appointment(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(14), rs.getString(5), DateTimeUtility.convertFromUTC((LocalDateTime)rs.getObject("Start")), DateTimeUtility.convertFromUTC((LocalDateTime)rs.getObject("End")), rs.getInt(12), rs.getInt(13));
            myList.add(app);
        }
        return myList;
    }


    public static ObservableList<Appointment> createAppointmentListCurrentWeek() throws SQLException {
        ObservableList<Appointment> myList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE YEARWEEK(Start) = YEARWEEK(NOW())";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Appointment app = new Appointment(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(14), rs.getString(5), DateTimeUtility.convertFromUTC((LocalDateTime)rs.getObject("Start")), DateTimeUtility.convertFromUTC((LocalDateTime)rs.getObject("End")), rs.getInt(12), rs.getInt(13));
            myList.add(app);
        }
        return myList;
    }


    public static ObservableList<Appointment> createAppointmentListCurrentMonth() throws SQLException {
        ObservableList<Appointment> myList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE MONTH(Start) = MONTH(NOW()) AND YEAR(Start) = YEAR(NOW())";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Appointment app = new Appointment(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(14), rs.getString(5), DateTimeUtility.convertFromUTC((LocalDateTime)rs.getObject("Start")), DateTimeUtility.convertFromUTC((LocalDateTime)rs.getObject("End")), rs.getInt(12), rs.getInt(13));
            myList.add(app);
        }
        return myList;
    }


    public static void deleteAppointment(int appointmentID) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentID);
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            Alert del = new Alert(Alert.AlertType.INFORMATION);
            del.setTitle("Deletion Successful");
            del.setHeaderText("Appointment deleted");
            del.showAndWait();
        }
    }


    public static int updateAppointment(Appointment appt) throws SQLException {
        int rowsAffected = 0;
        try {
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, appt.getApptTitle());
            ps.setString(2, appt.getApptDescription());
            ps.setString(3, appt.getApptLocation());
            ps.setString(4, appt.getApptType());
            ps.setObject(5, DateTimeUtility.convertToUTC(appt.getApptStart()));
            ps.setObject(6, DateTimeUtility.convertToUTC(appt.getApptEnd()));
            ps.setInt(7, appt.getApptCustomerID());
            ps.setInt(8, appt.getApptUserID());
            ps.setInt(9, appt.getApptContact());
            ps.setInt(10, appt.getApptID());
            rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                Alert updateAlert = new Alert(Alert.AlertType.INFORMATION);
                updateAlert.setTitle("Update successful!");
                updateAlert.setHeaderText("Appointment updated successfully!");
                updateAlert.showAndWait();
                return rowsAffected;
            }
        } catch (Exception e) {
            Alert errorBox = new Alert(Alert.AlertType.ERROR);
            errorBox.setTitle("error");
            errorBox.setHeaderText("All fields must have valid data");
            errorBox.showAndWait();
            System.out.println(e.getMessage());
        }
        return rowsAffected;
    }


    public static boolean checkForCustomerAppointments(int customerID) throws SQLException {

        String sql = "SELECT Appointment_ID FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();
        if(!rs.next())
            return false;
        else
            return true;
    }
}
