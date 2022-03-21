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


public class login_screen implements Initializable {

    public Button loginButton;
    public Label loginLabel;
    public TextField userIDTextBox;
    public TextField passwordTextBox;
    public Label locationLabel;
    public Label userIdLabel;
    public Label passwordLabel;
    public Label screenTitle;


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


    private void loadPasswordErrorBox() {
        ResourceBundle rb = ResourceBundle.getBundle("Main/C195Bundle");
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(rb.getString("errorTitle"));
        alert.setContentText(rb.getString("errorContent"));
        alert.showAndWait();
    }


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


    private void checkUserAppointments(int userID) throws SQLException {
        try {
            ObservableList<Appointment> myList = JDBC.createAppointmentList();
            ObservableList<Appointment> newList = FXCollections.observableArrayList();
            LocalDateTime currentDateTime = LocalDateTime.now();
            for (Appointment a : myList) {
                    if (a.getApptUserID() == userID)
                        newList.add(a);
            }

            if(!newList.isEmpty()) {
                for (Appointment a : newList) {
                    if (a.getApptStart().minus(15, ChronoUnit.MINUTES).isBefore(currentDateTime) && a.getApptEnd().isAfter(currentDateTime))
                        loadAlertUserBox(a);
                }
                System.out.println("no appointments upcoming");
                alertBoxInterface alert = () -> { Alert myAlert = new Alert(Alert.AlertType.INFORMATION);
                    myAlert.setTitle("Message");
                    myAlert.setHeaderText("Appointment Info:");
                    myAlert.setContentText("You have no scheduled appointments in the next 15 minutes");
                    return myAlert.showAndWait();
                };
                alert.displayAlertBox();
            }
            else {
                System.out.println("no appointments upcoming");
                alertBoxInterface alert = () -> { Alert myAlert = new Alert(Alert.AlertType.INFORMATION);
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


    private void loadAlertUserBox(Appointment a) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Appointment Alert");
        alert.setHeaderText("Appointment reminder");
        alert.setContentText("Upcoming appointment ID: " + a.getApptCustomerID() + "\nAppointment date: " + a.getApptStart().toLocalDate() + "\nAppointment time: " + a.getApptStart().toLocalTime());
        alert.showAndWait();
    }
}
