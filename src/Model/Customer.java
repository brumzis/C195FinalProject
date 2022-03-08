package Model;

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

}
