package Controller;

import Model.Appointment;
import Model.DateTimeUtility;
import Model.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.InputMismatchException;

/**
 * Controller for the update_appointment.fxml page. This controller takes data entered by the
 * user and processes and UPDATE command in SQL on the Appointment Database. As long as all of
 * the fields have the appropriate data, the update will process successfully and return the user
 * back to the view_appointments screen. Incomplete or invalid data will be met with an alertbox
 * letting the user know they need to enter the correct data/dates/times. This screen will load
 * with the existing data passed in by the appointment that has been selected by the user in the
 * view_appointments.fxml screen. All data can be changed except the appointment ID field.
 *
 */
public class update_appointment {
    public TextField apptIDTbox;
    public TextField titleTbox;
    public TextField descriptionTbox;
    public TextField locationTbox;
    public ComboBox contactComboBox;
    public TextField typeTbox;
    public DatePicker startDateBox;
    public ComboBox startHourComboBox;
    public ComboBox startMinComboBox;
    public DatePicker endDateBox;
    public ComboBox endHourComboBox;
    public ComboBox endMinComboBox;
    public TextField customerIDTbox;
    public TextField userIDTbox;
    public Button updateButton;
    public Button cancelButton;

    /**
     * A click of the update button will first look at the dates and times entered by the user. They will be
     * converted to LocalDateTime objects and checked to make sure they fall within given specifications.
     * Once verified to be valid, they will be converted to UTC time before being updated along with the
     * selected appointment in the database.
     *
     * @param actionEvent - the mouse click of the Update Button
     * @see Model.Customer
     */
    public void updateButtonClick(ActionEvent actionEvent) {
        try {
            Appointment myAppointment = null;
            int i = Integer.parseInt(apptIDTbox.getText());                  //convert user entered data to LocalDateTime objects
            LocalDate startDay = startDateBox.getValue();
            LocalDate endDay = endDateBox.getValue();
            String startHour = startHourComboBox.getValue().toString();
            String endHour = endHourComboBox.getValue().toString();
            String startMinute = startMinComboBox.getValue().toString();
            String endMinute = endMinComboBox.getValue().toString();
            LocalDateTime startApptTime = LocalDateTime.of(startDay.getYear(), startDay.getMonthValue(), startDay.getDayOfMonth(), Integer.parseInt(startHour), Integer.parseInt(startMinute));
            LocalDateTime endApptTime = LocalDateTime.of(endDay.getYear(), endDay.getMonthValue(), endDay.getDayOfMonth(), Integer.parseInt(endHour), Integer.parseInt(endMinute));

            if(!DateTimeUtility.validateAppointmentTime(startApptTime))         //make sure user entered times do not fall outside US/Eastern business hours per specs
                throw new ArithmeticException("invalid time");
            if(!DateTimeUtility.validateAppointmentTime(endApptTime))
                throw new ArithmeticException("invalid time");

            if(!DateTimeUtility.compareTimes(startApptTime, endApptTime))                   //make sure start time comes before end time
                throw new IllegalStateException("start time must come before end time");

            if(!DateTimeUtility.checkOverlap(Integer.parseInt(customerIDTbox.getText()), DateTimeUtility.convertToUTC(startApptTime), DateTimeUtility.convertToUTC(endApptTime)))     //make sure appointments cannot overlap
                throw new InputMismatchException("appointments are overlapping");


            if (contactComboBox.getSelectionModel().getSelectedItem() instanceof String) {
                myAppointment = new Appointment(i, titleTbox.getText(), descriptionTbox.getText(), locationTbox.getText(), JDBC.returnContactID(contactComboBox.getSelectionModel().getSelectedItem().toString()), typeTbox.getText(), startApptTime, endApptTime, Integer.parseInt(customerIDTbox.getText()), Integer.parseInt(userIDTbox.getText()));
            } else {
                myAppointment = new Appointment(i, titleTbox.getText(), descriptionTbox.getText(), locationTbox.getText(), (Integer) contactComboBox.getSelectionModel().getSelectedItem(), typeTbox.getText(), startApptTime, endApptTime, Integer.parseInt(customerIDTbox.getText()), Integer.parseInt(userIDTbox.getText()));
            }

            int j = JDBC.updateAppointment(myAppointment);      // once all data has been checked - run the SQL UPDATE method.

            if (j > 0) {
                Parent root = FXMLLoader.load(getClass().getResource("/view/view_appointments.fxml"));    //if successful, load the view appointments screen
                Stage myStage = (Stage)updateButton.getScene().getWindow();
                Scene myScene = new Scene(root, 1300, 500);
                myStage.setTitle("View Appointments");
                myStage.setScene(myScene);
                myStage.show();
            }
        } catch (ArithmeticException e) {                                 //unsuccessful, load appropriate error box
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Scheduling Error");
            alert.setHeaderText("Invalid Time/Date Values");
            alert.setContentText("Appointments can only be made Mon - Fri from 8AM to 10PM Eastern Time!");
            alert.showAndWait();

        } catch (IllegalStateException e) {
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
            Alert errorBox = new Alert(Alert.AlertType.ERROR);
            errorBox.setTitle("Error");
            errorBox.setHeaderText("All fields require valid data");
            errorBox.showAndWait();
            System.out.println(e.getMessage());
        }
    }
    /**
     * Takes the user back to the view_appointments.fxml screen.
     *
     * @param actionEvent - a mouse click on the Cancel Button
     * @throws IOException
     */
    public void cancelButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/view_appointments.fxml"));
        Stage myStage = (Stage)cancelButton.getScene().getWindow();
        Scene myScene = new Scene(root, 1300, 500);
        myStage.setTitle("Appointment Screen");
        myStage.setScene(myScene);
        myStage.show();
    }
    /**
     * This method is called from the view_appointments screen. The user selects an appointment
     * from the tableview on that page and clicks 'Update'. Once clicked, this method is called
     * and the selected object from that screen is passed in. This method takes that object and
     * uses it to populate the fields on the Update Appointment Page.
     *
     * @param a - an appointment object selected by the user on the view_appointments screen.
     * @return an object of type Appointment
     * @throws SQLException
     * @see Appointment
     */
    public Appointment getSelectedAppointment(Appointment a) throws SQLException {

        apptIDTbox.setText(String.valueOf(a.getApptID()));
        titleTbox.setText(a.getApptTitle());
        descriptionTbox.setText(a.getApptDescription());
        locationTbox.setText(a.getApptLocation());
        contactComboBox.setItems(JDBC.getContactNames());
        contactComboBox.setValue(a.getApptContactName(a.getApptContact()));
        typeTbox.setText(a.getApptType());
        startHourComboBox.setItems(JDBC.getHours());
        startHourComboBox.setValue(a.getApptStart().getHour());
        startMinComboBox.setValue(a.getApptStart().getMinute());
        startMinComboBox.setItems(JDBC.getMinutes());
        endHourComboBox.setItems(JDBC.getHours());
        endHourComboBox.setValue(a.getApptEnd().getHour());
        endMinComboBox.setItems(JDBC.getMinutes());
        endMinComboBox.setValue(a.getApptEnd().getMinute());
        startDateBox.setValue(a.getApptStart().toLocalDate());
        endDateBox.setValue(a.getApptEnd().toLocalDate());
        customerIDTbox.setText(String.valueOf(a.getApptCustomerID()));
        userIDTbox.setText(String.valueOf(a.getApptUserID()));
        return a;
    }
}
