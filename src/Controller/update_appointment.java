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


    public void updateButtonClick(ActionEvent actionEvent) {
        try {
            Appointment myAppointment = null;
            int i = Integer.parseInt(apptIDTbox.getText());
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
                throw new IllegalStateException("start time must come before end time");

            if(!DateTimeUtility.checkOverlap(Integer.parseInt(customerIDTbox.getText()), DateTimeUtility.convertToUTC(startApptTime), DateTimeUtility.convertToUTC(endApptTime)))
                throw new InputMismatchException("appointments are overlapping");


            if (contactComboBox.getSelectionModel().getSelectedItem() instanceof String) {
                myAppointment = new Appointment(i, titleTbox.getText(), descriptionTbox.getText(), locationTbox.getText(), JDBC.returnContactID(contactComboBox.getSelectionModel().getSelectedItem().toString()), typeTbox.getText(), startApptTime, endApptTime, Integer.parseInt(customerIDTbox.getText()), Integer.parseInt(userIDTbox.getText()));
            } else {
                myAppointment = new Appointment(i, titleTbox.getText(), descriptionTbox.getText(), locationTbox.getText(), (Integer) contactComboBox.getSelectionModel().getSelectedItem(), typeTbox.getText(), startApptTime, endApptTime, Integer.parseInt(customerIDTbox.getText()), Integer.parseInt(userIDTbox.getText()));
            }

            int j = JDBC.updateAppointment(myAppointment);

            if (j > 0) {
                Parent root = FXMLLoader.load(getClass().getResource("/view/view_appointments.fxml"));
                Stage myStage = (Stage)updateButton.getScene().getWindow();
                Scene myScene = new Scene(root, 1300, 500);
                myStage.setTitle("View Appointments");
                myStage.setScene(myScene);
                myStage.show();
            }
        } catch (ArithmeticException e) {
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

    public void cancelButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/view_appointments.fxml"));
        Stage myStage = (Stage)cancelButton.getScene().getWindow();
        Scene myScene = new Scene(root, 1300, 500);
        myStage.setTitle("Appointment Screen");
        myStage.setScene(myScene);
        myStage.show();
    }

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
