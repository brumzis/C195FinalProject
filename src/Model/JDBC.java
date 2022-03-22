package Model;

import Controller.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * An abstract class that is used to handle access to and from the database. JDBC also handles opening
 * and closing the connection to the database. Methods contain SQL language and statements to input
 * and retrieve data from the database.
 *
 */
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

    /**
     * Method used to open/establish the connection to the database
     *
     */
    public static void openConnection() {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        } catch(Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * Method used to close/terminate the connection to the database
     *
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        } catch(Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * Method used to get list of all countries from the DB. This method retrieves the values in 2 columns
     * of the database, the country name and the country ID. Both of those values are used in the Country
     * Object Constructor. For each line returned in the database, a new Country Object is created using
     * the 2 column values in its constructor. Each line returned from the DB will create a new Country Object and
     * add it to a list. The list is returned by the method.
     *
     * @return returns a list of Country Objects
     * @throws SQLException
     * @see Country
     */
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

    /**
     * Method returning of list of just the country names from the database. Much like the
     * getCountryObjects() method - this one just returns a list of Strings (the country names)
     * instead of returning a list of objects
     *
     * @return an ObservaleList of strings. The country names
     * @throws SQLException
     * @see Country
     */
    public static ObservableList<String> getCountryNames() throws SQLException {
        ObservableList<String> myList = FXCollections.observableArrayList();
        String sql = "SELECT Country FROM countries";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
            myList.add(rs.getString(1));
        return myList;
    }

    /**
     * This method returns a list of all the division names from the database. The names
     * are returned as Strings in an ObservableList
     *
     * @return an ObservableList of Strings
     * @throws SQLException
     */
    public static ObservableList<String> getAllDivisionNames() throws SQLException {
        ObservableList<String> myList = FXCollections.observableArrayList();
        String sql = "SELECT Division FROM First_Level_Divisions";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
            myList.add(rs.getString(1));
        return myList;
    }

    /**
     * Method that returns only the division names specific to a particular country
     *
     * @param country_ID    The country ID
     * @return an ObservableList of Strings - the divison names associated with a given country
     * @throws SQLException
     */
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

    /**
     * Method that takes the name of a division as input and returns the corresponding division ID
     *
     * @param name  - takes the division name
     * @return returns the corresponding division ID
     * @throws SQLException
     */
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

    /**
     * Method that takes the name of a contact as input and returns the corresponding contact ID
     *
     * @param name - takes a contact name
     * @return returns the corresponding contact ID
     * @throws SQLException
     */
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

    /**
     * Method to take customer entries from the database and covert them into Customer Objects
     * The objects are put into a list and returned in list form. Each column returned from the
     * database is put into the Customer Constructor to create the constructor object. This is
     * done for each line returned from the DB. The result is a list of Customer Objects
     *
     * @return an ObservableList of Customer Objects
     * @throws SQLException
     * @see Customer
     * @see view_customer
     */
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

    /**
     * Method to delete a customer from the database. The method takes in a customer ID and
     * searches the database for a match. If found, the entry is deleted. The executeUpdate
     * command will return the number of rows affected by the delete query. If the number of rows > 0,
     * the command was successful and will trigger an alertbox to let the user know the deletion
     * was successful. If no match was found, a different alertbox will trigger to let the user
     * know that the customer was not found in the database.
     *
     * @param customerID - the ID of the customer to be deleted from the DB.
     * @return number of rows affected by the SQL Statement. An integer.
     * @throws SQLException
     * @see Customer
     * @see delete_customer
     */
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

    /**
     * Method to update the attributes of an existing customer. All attributes except the customer ID
     * can be updated/changed. Much like the addCustomer method, it takes inputs from the textfields
     * and uses them in an INSERT statement in SQL. If the update was successful, the number of rows
     * afftected > 0 and an alertbox will be generated to let the user know the update was successful
     *
     * @param cust - the customer object to be updated
     * @return the number of rows affected by the query, an integer
     * @throws SQLException
     * @see add_customer
     */
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

    /**
     * Method to return an ObservableList of all the contact names in the database. The list
     * is in string form. The list is used to populate a combobox with the contact options.
     *
     * @return an ObservableList of contact names (String form)
     * @throws SQLException
     * @see Contact
     */
    public static ObservableList<String> getContactNames() throws SQLException {
        ObservableList<String> myList = FXCollections.observableArrayList();
        String sql = "SELECT Contact_Name FROM contacts";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
            myList.add(rs.getString(1));
        return myList;
    }

    /**
     * Method used to retrieve contact info from the database and convert it into Contact Objects.
     * The columns returned by the query are used in the constructor to create the objects.
     * The Contact objects are added to a list and returned by this method
     *
     * @return an ObservableList of Contact Objects
     * @throws SQLException
     * @see Contact
     */
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

    /**
     * Method used to create User Objects from the user table in the database. This method
     * retrieves the column info from the User Table in the database. The column info is used in
     * the constructor to create a User Object for every row returned by the query. The method
     * returns an ObservableList of User Objects
     *
     * @return an ObservableList of User Objects
     * @throws SQLException
     * @see User
     */
    public static ObservableList<User> getUserObjects() throws SQLException {
        ObservableList<User> myList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            User u = new User(rs.getInt(1), rs.getString(2), rs.getString(3));
            myList.add(u);
        }
        return myList;
    }

    /**
     * Method to return a list of strings. The list is used to populate a combobox with a list
     * of choices for the hour of the day
     *
     * @return an ObservableList of Strings to populate a combobox
     */
    public static ObservableList<String> getHours() {
        ObservableList myList = FXCollections.observableArrayList();
        myList.add("00");myList.add("01");myList.add("02");myList.add("03");myList.add("04");
        myList.add("05");myList.add("06");myList.add("07");myList.add("08");myList.add("09");
        myList.add("10");myList.add("11");myList.add("12");myList.add("13");myList.add("14");
        myList.add("15");myList.add("16");myList.add("17");myList.add("18");myList.add("19");
        myList.add("20");myList.add("21");myList.add("22");myList.add("23");
        return myList;
    }

    /**
     * Method to return a list of strings. The list is used to populate a combobox with a list
     * of choices for the minute of the day. The minutes are only available in 15 minute increments.
     *
     * @return an ObservableList of Strings to populate a combobox
     */
    public static ObservableList<String> getMinutes() {
        ObservableList myList = FXCollections.observableArrayList();
        myList.add("0");
        myList.add("15");
        myList.add("30");
        myList.add("45");
        return myList;
    }

    /**
     * Method used to retrieve the data from the appointment table in the database and use it
     * to create Appointment Objects. Each column returned from the query is used in the constructor
     * for the Appointment Object. Each row returned will create a new object. The objects are added
     * to an ObservableList and returned.
     *
     * @return an ObservableList of Appointment Objects
     * @throws SQLException
     * @see Appointment
     * @see view_appointments
     */
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

    /**
     * Method that returns an ObservableList of Appointment Objects. The appointment objects returned
     * match the contact ID entered by the user. The list is used to populate a tableview on the
     * "View Contact Schedules" page.
     *
     * @param contactID the contactID entered by the user to search for
     * @return an ObservableList of Appointment objects corresponding to the contact ID
     * @throws SQLException
     * @see Contact
     * @see view_contact_schedules
     */
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

    /**
     * Method queries the database for all appointments that match the given customer ID
     * All matching rows from the appointment table are then put into the Appointment
     * Object Constructor and coverted into objects. Those Appointment objects are returned
     * as an ObservableList to populate the Customer Appointment Table
     *
     * @param customerID customerID to get appointments for
     * @return ObservableList of Appointments objects for a given customerID
     * @throws SQLException
     * @see customer_appointments
     */
    public static ObservableList<Appointment> createCustomerSchedule(int customerID) throws SQLException {
        ObservableList<Appointment> myList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Appointment app = new Appointment(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(14), rs.getString(5), DateTimeUtility.convertFromUTC((LocalDateTime)rs.getObject("Start")), DateTimeUtility.convertFromUTC((LocalDateTime)rs.getObject("End")), rs.getInt(12), rs.getInt(13));
            myList.add(app);
        }
        return myList;
    }

    /**
     * Method to populate a table containing customer appointments. This method queries the server
     * for all appointments that match a selected month. All results are returned and converted into
     * appointment objects that populate the table. This view is the result of a radio button.
     *
     * @param selectedMonth the requested month selected by the user
     * @return an ObservableList of Appointment Objects matching the requested month
     * @throws SQLException
     * @see customer_appointments
     */
    public static ObservableList<Appointment> createCustomerScheduleByMonth(String selectedMonth) throws SQLException {
        ObservableList<Appointment> myList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE MONTHNAME(Start) = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, selectedMonth);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Appointment app = new Appointment(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(14), rs.getString(5), DateTimeUtility.convertFromUTC((LocalDateTime)rs.getObject("Start")), DateTimeUtility.convertFromUTC((LocalDateTime)rs.getObject("End")), rs.getInt(12), rs.getInt(13));
            myList.add(app);
        }
        return myList;
    }

    /**
     * Method to populate a table containing customer appointments. This method queries the server
     * for all appointments that match a user entered string. All results are returned and converted into
     * appointment objects that populate the table. This view is the result of a radio button.
     *
     * @param selectedType the requested type of appointment entered by the user in string form.
     * @return an ObservableList of Appointment Objects matching the requested type
     * @throws SQLException
     * @see customer_appointments
     */
    public static ObservableList<Appointment> createCustomerScheduleByType(String selectedType) throws SQLException {
        ObservableList<Appointment> myList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Type = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, selectedType);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Appointment app = new Appointment(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(14), rs.getString(5), DateTimeUtility.convertFromUTC((LocalDateTime)rs.getObject("Start")), DateTimeUtility.convertFromUTC((LocalDateTime)rs.getObject("End")), rs.getInt(12), rs.getInt(13));
            myList.add(app);
        }
        return myList;
    }

    /**
     * Method that selects all rows from the appointment database that match the criteria of the current week
     * The built-in SQL command YEARWEEK is used to select matching appointments. The rows
     * returned by the query are converted into Appointment Objects using the constructor. The objects
     * are returned in list form and used to populate a tableview
     *
     * @return an ObservableList of Appointment objects from the database matching the current week
     * @throws SQLException
     * @see customer_appointments
     */
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

    /**
     * Method that selects all rows from the appointment database that match the criteria of the current month
     * The built-in SQL commands MONTH and YEAR are used to select matching appointments. The rows
     * returned by the query are converted into Appointment Objects using the constructor. The objects
     * are returned in list form and used to populate a tableview
     *
     * @return an ObservableList of Appointment Objects matching the current month.
     * @throws SQLException
     * @see customer_appointments
     */
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


    /**
     * Method to update a selected Appointment in the database. All attributes except the Appointment ID are editable.
     * User entered textfield values are used in the SQL query to update the requested appointment. Invalid
     * user entries will throw an exception.
     *
     * @param appt - the appointmentID of the requested appointment to update
     * @return rowsAffected - returns the number of rows affected by the query
     * @throws SQLException
     * @see update_appointment
     * @see Appointment
     */
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
            rowsAffected = ps.executeUpdate();                                         //If the executeUpdate command is successful in updating an object
            if (rowsAffected > 0) {                                                    //the rowsAffected > 0. An alertbox will let the user know
                Alert updateAlert = new Alert(Alert.AlertType.INFORMATION);            //that the update was successful
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

    /**
     * Queries the database to check if a given customerID has any appointments associated with it.
     * If at least one appointment is associated it will return true, otherwise it returns false.
     *
     * @param customerID - input by the user
     * @return true/false - returns true if a customer has any appointments assigned to them
     * @throws SQLException
     */
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

    /**
     * This method uses the PrintWriter to create and add to a file which records all login attempts.
     * When a login attempt is made (whether successful or unsuccessful) a text file will receive
     * the Username and Password used, as well as the timestamp(UTC) for the attempt.
     * A true/false is also used to indicate if the login attempt was successful
     *
     * @param userName - A string value entered by the user at the initial login screen
     * @param password - A string value entered by the user at the initial login screen
     * @param loginSuccess - a boolean determined by a successful login attempt
     * @throws IOException
     */
    public static void attemptLogger(String userName, String password, Boolean loginSuccess) throws IOException {

        Instant instant = Instant.now();
        FileWriter myFileWriter = new FileWriter("login_activity.txt", true);
        PrintWriter fileOut = new PrintWriter(myFileWriter);
        fileOut.println("Username used: " + userName);
        fileOut.println("Password used: " + password);
        fileOut.println("Login Successful? " + loginSuccess);
        fileOut.println("Time/Date of attempt(UTC): " + instant);
        fileOut.println("---------------------------------------------------------------");
        fileOut.close();
    }
}
