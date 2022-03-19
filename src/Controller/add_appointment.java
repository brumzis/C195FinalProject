package Controller;

import Model.DateTimeUtility;
import Model.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.ResourceBundle;

public class add_appointment {

    public DatePicker startDateBox;
    public ComboBox startHourComboBox;
    public ComboBox startMinComboBox;
    public DatePicker endDateBox;
    public ComboBox endHourComboBox;
    public ComboBox endMinComboBox;
    public TextField customerIDTbox;
    public TextField userIDTbox;
    public Button addNewButton;
    public Button cancelButton;
    public TextField apptIDTbox;
    public TextField titleTbox;
    public TextField descriptionTbox;
    public TextField locationTbox;
    public ComboBox contactComboBox;
    public TextField typeTbox;


    public void initialize() throws SQLException{

        contactComboBox.setItems(JDBC.getContacts());
        startHourComboBox.setItems(JDBC.getHours());
        endHourComboBox.setItems(JDBC.getHours());
        startMinComboBox.setItems(JDBC.getMinutes());
        endMinComboBox.setItems(JDBC.getMinutes());


    }

    public void addButtonClick(ActionEvent actionEvent) {
        try {
            //Convert user entered time values to LocalDateTime objects
            LocalDate startDay = startDateBox.getValue();
            LocalDate endDay = endDateBox.getValue();
            String startHour = startHourComboBox.getValue().toString();
            String endHour = endHourComboBox.getValue().toString();
            String startMinute = startMinComboBox.getValue().toString();
            String endMinute = endMinComboBox.getValue().toString();
            LocalDateTime startApptTime = LocalDateTime.of(startDay.getYear(), startDay.getMonthValue(), startDay.getDayOfMonth(), Integer.parseInt(startHour), Integer.parseInt(startMinute));
            LocalDateTime endApptTime = LocalDateTime.of(endDay.getYear(), endDay.getMonthValue(), endDay.getDayOfMonth(), Integer.parseInt(endHour), Integer.parseInt(endMinute));

            //make sure user entered times do not fall outside US/Eastern business hours per specs
            if(!DateTimeUtility.validateAppointmentTime(startApptTime))
                throw new ArithmeticException("invalid time");
            if(!DateTimeUtility.validateAppointmentTime(endApptTime))
                throw new ArithmeticException("invalid time");

            //make sure start time comes before end time
            if(!DateTimeUtility.compareTimes(startApptTime, endApptTime))
                throw new NumberFormatException("start time must come before end time");

            //insert user entered fields into the SQL Table
            //the times are first converted to utc time from the system time
            String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";  //excluded primary key column
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, titleTbox.getText());
            ps.setString(2, descriptionTbox.getText());
            ps.setString(3, locationTbox.getText());
            ps.setString(4, typeTbox.getText());
            ps.setObject(5, DateTimeUtility.convertToUTC(startApptTime));
            ps.setObject(6, DateTimeUtility.convertToUTC(endApptTime));
            ps.setInt(7, Integer.parseInt(customerIDTbox.getText()));
            ps.setInt(8, Integer.parseInt(userIDTbox.getText()));
            ps.setInt(9, JDBC.returnContactID(contactComboBox.getSelectionModel().getSelectedItem().toString()));

            if(!DateTimeUtility.checkOverlap(Integer.parseInt(customerIDTbox.getText()), DateTimeUtility.convertToUTC(startApptTime), DateTimeUtility.convertToUTC(endApptTime)))
                throw new InputMismatchException("appointments are overlapping");


            int rowsAffected = ps.executeUpdate();
            //if addition was successful, show alertbox and go back to main menu
            if (rowsAffected > 0) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Addition Successful");
                alert2.setHeaderText("Appointment added to DB!");
                alert2.showAndWait();

                Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
                Stage menuStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene menuScene = new Scene(root, 600, 400);
                menuStage.setTitle("Main Menu");
                menuStage.setScene(menuScene);
                menuStage.show();
            }
        } catch (ArithmeticException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Scheduling Error");
            alert.setHeaderText("Invalid Time/Date Values");
            alert.setContentText("Appointments can only be made Mon - Fri from 8AM to 10PM Eastern Time!");
            alert.showAndWait();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Scheduling Error");
            alert.setHeaderText("Invalid Time/Date Values");
            alert.setContentText("Start time must come before end time");
            alert.showAndWait();

        } catch (InputMismatchException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Scheduling Error");
            alert.setHeaderText("Overlap");
            alert.setContentText("Customer already booked for that time!" + "\nPlease try another time");
            alert.showAndWait();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Data Entry Error");
            alert.setHeaderText("Data Entry Error");
            alert.setContentText("Please make sure all fields are filled in with correct data types");
            alert.showAndWait();
            System.out.println(e.getMessage());
        }
    }


    public void cancelButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
        Stage menuStage = (Stage)cancelButton.getScene().getWindow();
        Scene menuScene = new Scene(root, 600, 400);
        menuStage.setTitle("Main Menu");
        menuStage.setScene(menuScene);
        menuStage.show();
    }


}
