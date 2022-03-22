package Model;

/**
 * Contact class allows for the creation of a Contact object. The Contact object has three attributes,
 * corresponding to the 3 relevant columns in the database. contactID, contactName, and contactEmail.
 * Getters and setters for each attribute follow.
 *
 */
public class Contact {

    private int contactID;
    private String contactName;
    private String contactEmail;

    /**
     * Constructor for the Contact object.
     *
     */
    public Contact(int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    public int getContactID() {
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

    @Override
    public String toString() {
        return contactName;
    }


}
