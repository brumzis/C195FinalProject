package Controller;

import Model.DateTimeUtility;
import Model.JDBC;
import Model.alertBoxInterface;
import Model.loadMainMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.InputMismatchException;

/**
 * Controller for the add_appointment.fxml page. Has an initialize() method, as well as event handling
 * for the Add and Cancel Buttons on the page
 *
 * @see Model.Appointment
 */
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


    /**
     * Fills the comboboxes on the page with the correct data.
     *
     * @throws SQLException
     */
    public void initialize() throws SQLException{

        contactComboBox.setItems(JDBC.getContactNames());
        startHourComboBox.setItems(JDBC.getHours());
        endHourComboBox.setItems(JDBC.getHours());
        startMinComboBox.setItems(JDBC.getMinutes());
        endMinComboBox.setItems(JDBC.getMinutes());
    }


    /**
     * Pressing the Add button first takes the date and time values in the user entered text fields
     * and combines/converts them to LocalDateTime Objects, one for the start and one for the end.
     * Several checks are made to ensure the times entered fall into the requirements for the project.
     * Checks are also made to make sure the appointment does not overlap with an existing one, and to
     * make sure the end time comes after the start time. User entered data from the text fields on the
     * page are entered into the SQL query to create a new appointment in the database. The LocalDateTime
     * is first converted to UTC before creating a database entry. An alertbox notifies the user
     * if the addition was successful or if there was an issue.
     *
     * @param actionEvent - the mouse click of the Add button
     * @throws ArithmeticException thrown if the appointment time entered is outside 8AM-10PM Eastern Time
     * @throws NumberFormatException thrown if the end time doesn't come before the start time
     * @throws InputMismatchException thrown is appointment is made overlapping an existing appointment
     * @throws Exception thrown is data entered by the user is the wrong type, or if a textfield is empty
     */
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


            //make sure appointments do not overlap
            if(!DateTimeUtility.checkOverlap(Integer.parseInt(customerIDTbox.getText()), DateTimeUtility.convertToUTC(startApptTime), DateTimeUtility.convertToUTC(endApptTime)))
                throw new InputMismatchException("appointments are overlapping");

            int rowsAffected = ps.executeUpdate();

            //if addition was successful, show alertbox and go back to main menu
            if (rowsAffected > 0) {
                alertBoxInterface alert = () -> { Alert myAlert = new Alert(Alert.AlertType.INFORMATION);
                                                  myAlert.setTitle("Addition Successful");
                                                  myAlert.setHeaderText("Appointment added to DB!");
                                                  return myAlert.showAndWait();
                                                };
                alert.displayAlertBox();

                Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
                Stage menuStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene menuScene = new Scene(root, 600, 400);
                menuStage.setTitle("Main Menu");
                menuStage.setScene(menuScene);
                menuStage.show();
            }
        } catch (ArithmeticException e) {
            alertBoxInterface alert1 = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);
                                               myAlert.setTitle("Scheduling Error");
                                               myAlert.setHeaderText("Invalid Time/Date Values");
                                               myAlert.setContentText("Appointments can only be made Mon - Fri from 8AM to 10PM Eastern Time!");
                                               return myAlert.showAndWait();
                                             };
            alert1.displayAlertBox();

        } catch (NumberFormatException e) {
            alertBoxInterface alert1 = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);
                                               myAlert.setTitle("Scheduling Error");
                                               myAlert.setHeaderText("Invalid Time/Date Values");
                                               myAlert.setContentText("Start time must come before end time");
                                               return myAlert.showAndWait();
                                             };
            alert1.displayAlertBox();

        } catch (InputMismatchException e) {
            alertBoxInterface alert1 = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);
                                               myAlert.setTitle("Scheduling Error");
                                               myAlert.setHeaderText("Overlap");
                                               myAlert.setContentText("Customer already booked for that time!" + "\nPlease try another time");
                                               return myAlert.showAndWait();
                                             };
            alert1.displayAlertBox();


        } catch (Exception e) {
            alertBoxInterface alert1 = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);
                                               myAlert.setTitle("Data Entry Error");
                                               myAlert.setHeaderText("Data Entry Error");
                                               myAlert.setContentText("Please make sure all fields are filled in with correct data types");
                                               return myAlert.showAndWait();
                                             };
            alert1.displayAlertBox();
        }
    }

    /**
     * Upon cancel button click, returns user back to the main menu screen
     * A lamdba function was used to load the Main Menu Screen.
     *
     * @param actionEvent - a mouse click on the cancel button
     * @throws IOException
     */
    public void cancelButtonClick(ActionEvent actionEvent) throws IOException {  //return to main menu if clicked

        loadMainMenu lamdaFunction = () -> {Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
                                            Stage menuStage = (Stage) cancelButton.getScene().getWindow();
                                            Scene menuScene = new Scene(root, 600, 400);
                                            menuStage.setTitle("Main Menu");
                                            menuStage.setScene(menuScene);
                                            menuStage.show();
                                           };
        lamdaFunction.loadMainMenuScene();
    }
}
