package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Customer class allows for the creation of a Customer object. The Customer object has six attributes,
 * corresponding to the 6 relevant columns in the database. customerID, customerName, customerAddress,
 * customerPostalCode, customerPhone, and customerDivision. Getters and setters for each attribute follow.
 *
 */
public class Customer {

    private int customerID;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhone;
    private int customerDivision;
    private String customerCountry;
    private Object lambdaInterface;

    /**
     * Constructor for the Customer Object
     */
    public Customer(int customerID, String customerName, String customerAddress, String customerPostalCode, String customerPhone, int customerDivision, String customerCountry) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhone = customerPhone;
        this.customerDivision = customerDivision;
        this.customerCountry = customerCountry;
    }


    public int getCustomerID() {
        return customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public int getCustomerDivision() {
        return customerDivision;
    }

    public String getCustomerDivisionName() throws SQLException {
        int i = getCustomerDivision();
        String s = null;
        String str = "SELECT Division FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(str);
        ps.setInt(1, i);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
            s = rs.getString(1);
        return s;
    }


    public void setCustomerDivision(int customerDivision) {
        this.customerDivision = customerDivision;
    }


    @Override
    public String toString() {
        return customerName;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(int divisionID) throws SQLException {

        this.customerCountry = JDBC.getCountryName(divisionID);

    }
}
