package Controller;

import Model.Appointment;
import Model.JDBC;
import Model.alertBoxInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

/**
 * Controller for the login_screen.fxml page. The controller handles the userID and password text fields,
 * as well as the login button. A label displaying the system time zone is also displayed on the login screen.
 * This controller also handles support for the French language. A Resource Bundle is used, containing
 * files for English and French. After any login attempt (successful or not) a call will be made to
 * JDBC.attemptLogger which creates/adds to a 'login_activity.txt' file, recording the attempted username
 * and password, along with a timestamp.
 *
 */
public class login_screen implements Initializable {

    public Button loginButton;
    public Label loginLabel;
    public TextField userIDTextBox;
    public TextField passwordTextBox;
    public Label locationLabel;
    public Label userIdLabel;
    public Label passwordLabel;
    public Label screenTitle;

    /**
     * Initializes all labels, text fields and text to use the appropriate resource bundle
     * depending on the user's system's language settings.
     *
     * @param rb ResourceBundle containing properties for English and French
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        userIDTextBox.setPromptText(rb.getString("userid2"));
        passwordTextBox.setPromptText(rb.getString("pwd2"));
        loginLabel.setText(String.valueOf(ZoneId.systemDefault()));
        loginButton.setText(rb.getString("button"));
        loginLabel.setText(String.valueOf(ZoneId.systemDefault()));
        passwordLabel.setText(rb.getString("pwd"));
        screenTitle.setText(rb.getString("title"));
        locationLabel.setText(rb.getString("loc"));
        userIdLabel.setText(rb.getString("userid1"));
    }

    /**
     * A click on the login button takes the user entered
     *
     *
     *
     *
     * @param actionEvent - a mouse click on the login button
     * @throws SQLException
     */
    public void onLoginButtonClick(ActionEvent actionEvent) throws SQLException, IOException {
        String userNameInput = userIDTextBox.getText();
        String passwordInput = passwordTextBox.getText();
        int userID = getUserID(userNameInput);

        if(userID != 0 && (validPassword(getUserID(userNameInput), passwordInput))) {
            JDBC.attemptLogger(userNameInput, passwordInput, true);
            checkUserAppointments(userID);

            Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
            Stage menuStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene menuScene = new Scene(root, 600, 400);
            menuStage.setTitle("Main Menu");
            menuStage.setScene(menuScene);
            menuStage.show();
        }
        else {
            JDBC.attemptLogger(userNameInput, passwordInput, false);
            loadPasswordErrorBox();
        }
    }

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
    private void loadPasswordErrorBox() {
        ResourceBundle rb = ResourceBundle.getBundle("Main/C195Bundle");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(rb.getString("errorTitle"));
        alert.setContentText(rb.getString("errorContent"));
        alert.showAndWait();
    }

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
    private int getUserID(String inputName) throws SQLException {
        int userID = 0;
        String sql = "SELECT User_ID FROM users WHERE User_Name = '" + inputName + "'";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            userID = rs.getInt("User_ID");
        }
        return userID;
    }

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
    private boolean validPassword(int userID, String password) throws SQLException {
        String sql = "SELECT Password FROM users WHERE User_ID = '" + userID + "'";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            if (rs.getString("Password").equals(password))
                return true;
        }
        return false;
    }

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
    private void checkUserAppointments(int userID) throws SQLException {
        try {
            ObservableList<Appointment> myList = JDBC.createAppointmentList();
            ObservableList<Appointment> newList = FXCollections.observableArrayList();
            LocalDateTime currentDateTime = LocalDateTime.now();
            for (Appointment a : myList) {
                    if (a.getApptUserID() == userID)
                        newList.add(a);
            }

            if(newList.isEmpty()) {
                alertBoxInterface alert = () -> { Alert myAlert = new Alert(Alert.AlertType.INFORMATION);
                    myAlert.setTitle("Message");
                    myAlert.setHeaderText("Appointment Info:");
                    myAlert.setContentText("You have no scheduled appointments in the next 15 minutes");
                    return myAlert.showAndWait();
                };
                alert.displayAlertBox();
            }

            if(!newList.isEmpty()) {
                for (Appointment a : newList) {
                    if(currentDateTime.isAfter(a.getApptStart()) && currentDateTime.isBefore(a.getApptEnd())) {
                        alertBoxInterface alert2 = () -> {Alert myAlert = new Alert(Alert.AlertType.INFORMATION);
                                                          myAlert.setTitle("Important");
                                                          myAlert.setHeaderText("Meeting happening now");
                                                          myAlert.setContentText("You have a scheduled meeting you are supposed to be in right now!");
                                                          return myAlert.showAndWait();
                                                         };
                        alert2.displayAlertBox();
                    }

                    else if (a.getApptStart().minus(15, ChronoUnit.MINUTES).isBefore(currentDateTime) && a.getApptEnd().isAfter(currentDateTime))
                        loadAlertUserBox(a);
                }
            }
            else {
                System.out.println("no appointments upcoming");
                alertBoxInterface alert = () -> {Alert myAlert = new Alert(Alert.AlertType.INFORMATION);
                                                 myAlert.setTitle("Message");
                                                 myAlert.setHeaderText("Appointment Info:");
                                                 myAlert.setContentText("You have no scheduled appointments in the next 15 minutes");
                                                 return myAlert.showAndWait();
                                                };
                alert.displayAlertBox();
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText("Appointment Info:");
            alert.setContentText("You have no scheduled appointments");
            alert.showAndWait();
        }
    }

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
    private void loadAlertUserBox(Appointment a) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Appointment Alert");
        alert.setHeaderText("Appointment reminder");
        alert.setContentText("Upcoming appointment ID: " + a.getApptID() + "\nAppointment date: " + a.getApptStart().toLocalDate() + "\nAppointment time: " + a.getApptStart().toLocalTime());
        alert.showAndWait();
    }
}
