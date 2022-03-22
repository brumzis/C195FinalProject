package Model;

/**
 * Page where user can add a new customer to the database
 *
 *
 *
 *
 * @param
 * @return
 * @throws
 * @see
 */
public class Contact {

    private int contactID;
    private String contactName;
    private String contactEmail;

    /**
     * Page where user can add a new customer to the database
     *
     *
     *
     *
     * @param
     * @return
     * @throws
     * @see
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
