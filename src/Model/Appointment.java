package Model;

public class Appointment {

    private int apptID;
    private String apptTitle;
    private String apptDescription;
    private String apptLocation;
    private String apptType;
    private DateTime apptStart;
    private DateTime apptEnd;
    private int apptCustomerID;
    private int apptUserID;


    public Appointment(int apptID, String apptTitle, String apptDescription, String apptLocation, String apptType, DateTime apptStart, DateTime apptEnd, int apptCustomerID, int apptUserID) {
        this.apptID = apptID;
        this.apptTitle = apptTitle;
        this.apptDescription = apptDescription;
        this.apptLocation = apptLocation;
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

    public String getApptType() {
        return apptType;
    }

    public void setApptType(String apptType) {
        this.apptType = apptType;
    }

    public DateTime getApptStart() {
        return apptStart;
    }

    public void setApptStart(DateTime apptStart) {
        this.apptStart = apptStart;
    }

    public DateTime getApptEnd() {
        return apptEnd;
    }

    public void setApptEnd(DateTime apptEnd) {
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
