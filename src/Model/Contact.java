package Model;

public class Contact {

    private String contactID;
    private String contactName;
    private String contactEmail;


    public Contact(String contactName, String contactEmail) {
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    public String getContactID() {
        return contactID;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
