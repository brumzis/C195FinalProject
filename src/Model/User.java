package Model;

public class User {

    private int userID;
    private String userName;
    private String userPassword;

    public User(String userName, String userPassword) {
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
}
