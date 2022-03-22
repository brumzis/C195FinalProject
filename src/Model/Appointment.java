package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * The Appointment object has 10 attributes: apptID, apptTitle, apptDescription, apptLocation
 * apptContact, apptType, apptStart, apptEnd, apptCustomerID, and apptUserID. These attributes correspond
 * to columns in the appointment database. The start and end times are stored in the database using UTC time.
 * Times must be converted to UTC before uploading to database, while times being downloaded from database
 * will be converted to the local time on the machine using it
 *
 */
public class Appointment {

    private int apptID;
    private String apptTitle;
    private String apptDescription;
    private String apptLocation;
    private int apptContact;
    private String apptType;
    private LocalDateTime apptStart;
    private LocalDateTime apptEnd;
    private int apptCustomerID;
    private int apptUserID;

    /**
     * Constructor for the Appointment object. Getters and setters created as well.
     *
     */
    public Appointment(int apptID, String apptTitle, String apptDescription, String apptLocation, int apptContact, String apptType, LocalDateTime apptStart, LocalDateTime apptEnd, int apptCustomerID, int apptUserID) {
        this.apptID = apptID;
        this.apptTitle = apptTitle;
        this.apptDescription = apptDescription;
        this.apptLocation = apptLocation;
        this.apptContact = apptContact;
        this.apptType = apptType;
        this.apptStart = apptStart;
        this.apptEnd = apptEnd;
        this.apptCustomerID = apptCustomerID;
        this.apptUserID = apptUserID;
    }

    public int getApptID() {
        return apptID;
    }

    public String getApptTitle() {
        return apptTitle;
    }

    public void setApptTitle(String apptTitle) {
        this.apptTitle = apptTitle;
    }

    public String getApptDescription() {
        return apptDescription;
    }

    public void setApptDescription(String apptDescription) {
        this.apptDescription = apptDescription;
    }

    public String getApptLocation() {
        return apptLocation;
    }

    public void setApptLocation(String apptLocation) {
        this.apptLocation = apptLocation;
    }

    public int getApptContact() { return apptContact; }

    public String getApptContactName(int id) throws SQLException {
        String str = "";
        String sql = "SELECT Contact_Name FROM contacts WHERE Contact_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next())
            str = rs.getString(1);
        return str;
    }

    public void setApptContact(int apptContact) { this.apptContact = apptContact; }

    public String getApptType() {
        return apptType;
    }

    public void setApptType(String apptType) {
        this.apptType = apptType;
    }

    public LocalDateTime getApptStart() {
        return apptStart;
    }

    public void setApptStart(LocalDateTime apptStart) {
        this.apptStart = apptStart;
    }

    public LocalDateTime getApptEnd() {
        return apptEnd;
    }

    public void setApptEnd(LocalDateTime apptEnd) {
        this.apptEnd = apptEnd;
    }

    public int getApptCustomerID() {
        return apptCustomerID;
    }

    public void setApptCustomerID(int apptCustomerID) {
        this.apptCustomerID = apptCustomerID;
    }

    public int getApptUserID() {
        return apptUserID;
    }







}
