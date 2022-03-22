package Model;

/**
 * User class allows for the creation of a User object. The User object has three attributes,
 * corresponding to the 3 relevant columns in the database. userID, userName, and userPassword
 *
 */
public class User {

    private int userID;
    private String userName;
    private String userPassword;

    public User(int userID, String userName, String userPassword) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
    }


    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String toString() {
        return userName;
    }
}


