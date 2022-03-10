package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer {

    private int customerID;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhone;
    private int customerDivision;


    public Customer(int customerID, String customerName, String customerAddress, String customerPostalCode, String customerPhone, int customerDivision) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhone = customerPhone;
        this.customerDivision = customerDivision;
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
        while(rs.next())
            s = rs.getString(1);
        return s;
    }

    public void setCustomerDivision(int customerDivision) {
        this.customerDivision = customerDivision;
    }
}
