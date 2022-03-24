package Controller;

import Model.Appointment;
import Model.DateTimeUtility;
import Model.JDBC;
import Model.alertBoxInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The controller for the view_appointments.fxml page. This controller initializes a tableview containing
 * all appointments found in the database. There are 3 radio buttons at the top of the page that allow
 * the appointments to be filtered by current week, current month, or view all. There are also 4 buttons
 * found on the page: Schedule New, Update Existing, Delete, and Exit. This controller handles those
 * button presses as well
 *
 */
public class view_appointments implements Initializable {

    public TableView<Appointment> appointmentTable;
    public TableColumn<Appointment, Integer> apptIDColumn;
    public TableColumn<Appointment, String> apptTitleColumn;
    public TableColumn<Appointment, String> apptDescriptionColumn;
    public TableColumn<Appointment, String> apptLocationColumn;
    public TableColumn<Appointment, Integer> apptContactColumn;
    public TableColumn<Appointment, String> apptTypeColumn;
    public TableColumn<Appointment, DateTimeUtility> apptStartTimeColumn;
    public TableColumn<Appointment, DateTimeUtility> apptEndTimeColumn;
    public TableColumn<Appointment, Integer> apptCustomerID;
    public TableColumn<Appointment, Integer> apptUserID;
    public Button scheduleNewButton;
    public Button updateApptButton;
    public Button deleteApptButton;
    public RadioButton monthRadioButton;
    public RadioButton weekRadioButton;
    public Button exitButton;


    /**
     * A tableview is loaded, in which all rows from the appointments table in the database are
     * converted into Appointment Objects, and input into the table under their respective columns, which
     * represent the Appointment Object attributes. This table is not editable.
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        apptIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("apptID"));
        apptTitleColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("apptTitle"));
        apptDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("apptDescription"));
        apptLocationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("apptLocation"));
        apptContactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("apptContact"));
        apptTypeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("apptType"));
        apptStartTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, DateTimeUtility>("apptStart"));
        apptEndTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, DateTimeUtility>("apptEnd"));
        apptCustomerID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("apptCustomerID"));
        apptUserID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("apptUserID"));

        try {appointmentTable.setItems(JDBC.createAppointmentList());} catch (SQLException e) {e.printStackTrace();}
    }


    /**
     * Clicking on the 'Schedule New' Button takes the user to the add_appointment.fxml page
     * where new appointments can be added to the database.
     *
     * @param actionEvent - a mouse click on the 'Schedule New Button'.
     * @throws IOException
     * @see Appointment
     */
    public void scheduleNewApptClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/add_appointment.fxml"));
        Stage addApptStage = (Stage)scheduleNewButton.getScene().getWindow();
        Scene addApptScene = new Scene(root, 800, 600);
        addApptStage.setTitle("Add Appointment");
        addApptStage.setScene(addApptScene);
        addApptStage.show();
    }


    /**
     * Clicking on the 'Update Existing' Button first checks to make sure a row has been selected from
     * the table. If no row is selected, there is obviously nothing to update and an alertbox with an
     * error message will be generated. If a row is selected, the user will be taken to the update_appointment.fxml
     * page, where changes can be made to the appointment. This button also triggers an instantiation
     * of the update_appointment controller so that the selected appointment object can be passed from
     * this page to the update page.
     *
     * @param actionEvent - a mouse click on the 'Update Existing' Button
     * @throws IOException
     * @see update_appointment
     */
    public void updateApptButtonClick(ActionEvent actionEvent) throws IOException, SQLException {
        if (appointmentTable.getSelectionModel().getSelectedItem() == null) {                          //if no row selected, send an error
            alertBoxInterface alert = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);
                                              myAlert.setTitle("Error:");
                                              myAlert.setHeaderText("No appointment selected");
                                              return myAlert.showAndWait();
                                            };
            alert.displayAlertBox();
        }
        else {
            Appointment a = appointmentTable.getSelectionModel().getSelectedItem();            //if an appointment was selected, send that object to the update_appointment page

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/update_appointment.fxml"));
            Parent root = loader.load();
            update_appointment controller = loader.getController();
            controller.getSelectedAppointment(a);                                        //passing appointment object to update_appointment.java
            Stage myStage = (Stage) updateApptButton.getScene().getWindow();
            Scene myScene = new Scene(root, 800, 600);
            myStage.setTitle("Edit Appointment");
            myStage.setScene(myScene);
            myStage.show();
        }
    }

    /**
     * Fist checks to make sure an appointment object has been selected. If nothing is selected by the user
     * the delete button will generate an alertbox telling the user that no appointment has been selected.
     * If an appointment is selected for deletion, the user is first prompted with a confirmation box to
     * make sure deletion is what was wanted. If confirmed, the selected object is identified by its
     * appointment ID number and deleted from the database via an SQL DELETE Statement. The user is notified
     * by an alertbox whether the deletion was successful.
     *
     * @param actionEvent - a mouse click on the 'Delete' Button
     * @throws SQLException
     */
    public void deleteApptButtonClick(ActionEvent actionEvent) throws SQLException {
        if (appointmentTable.getSelectionModel().getSelectedItem() == null) {                       //error if nothing is selected by the user
            alertBoxInterface alert = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);
                                              myAlert.setTitle("Error:");
                                              myAlert.setHeaderText("No appointment selected");
                                              return myAlert.showAndWait();
                                            };
            alert.displayAlertBox();
        }
        else {
            alertBoxInterface alert1 = () -> { Alert myAlert = new Alert(Alert.AlertType.CONFIRMATION);   //confirmation box
                                               myAlert.setTitle("Delete Appointment");
                                               myAlert.setContentText("Are you sure?");
                                               return myAlert.showAndWait();
                                             };
            Optional<ButtonType> result = alert1.displayAlertBox();

            if (result.isPresent() && result.get() == ButtonType.OK) {                      //once confirmed, SQL DELETE Statement is used to delete the appointment
                Appointment a = appointmentTable.getSelectionModel().getSelectedItem();
                String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
                PreparedStatement ps = JDBC.connection.prepareStatement(sql);
                ps.setInt(1, a.getApptID());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    alertBoxInterface alert2 = () -> { Alert myAlert = new Alert(Alert.AlertType.INFORMATION);    //alertbox indicating deletion was successful
                                                       myAlert.setTitle("Success");
                                                       myAlert.setHeaderText("Appointment Cancelled");
                                                       myAlert.setContentText("Appointment successfully deleted from DB");
                                                       return myAlert.showAndWait();
                                                     };
                    alert2.displayAlertBox();
                }
                else {
                    alertBoxInterface alert = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);   //alertbox indicating appointment ID was not found
                                                      myAlert.setTitle("Deletion Error:");
                                                      myAlert.setHeaderText("Appointment not found");
                                                      return myAlert.showAndWait();
                                                    };
                    alert.displayAlertBox();
                }

                appointmentTable.getSelectionModel().clearSelection();              //table is then cleared and reloaded to reflect results of deletion
                appointmentTable.getItems().clear();
                appointmentTable.setItems(JDBC.createAppointmentList());
            }
        }
    }


    /**
     * When the 'Current Month' Radio Button is checked, the table will clear and reload with a different
     * criteria. Instead of loading all appointments found in the database, it will only load those
     * appointments whose month matches the current month. This is done with the
     * JDBC.createAppointmentListCurrentMonth() method.
     *
     * @param actionEvent - the click of the radio button
     * @throws SQLException
     * @see JDBC
     */
    public void monthRadioButtonChecked(ActionEvent actionEvent) throws SQLException {
        appointmentTable.getSelectionModel().clearSelection();
        appointmentTable.getItems().clear();
        appointmentTable.setItems(JDBC.createAppointmentListCurrentMonth());
    }


    /**
     * When the 'Current Week' Radio Button is checked, the table will clear and reload with a different
     * criteria. Instead of loading all appointments found in the database, it will only load those
     * appointments whose week matches the current week. This is done with the
     * JDBC.createAppointmentListCurrentWeek() method.
     *
     * @param actionEvent - the click of the radio button
     * @throws SQLException
     * @see JDBC
     */
    public void weekRadioButtonChecked(ActionEvent actionEvent) throws SQLException {
        appointmentTable.getSelectionModel().clearSelection();
        appointmentTable.getItems().clear();
        appointmentTable.setItems(JDBC.createAppointmentListCurrentWeek());
    }


    /**
     * When the 'View All' Radio Button is checked, the table will clear and reload with all
     * appointments found in the database. Just like the initialization.
     *
     * @param actionEvent - the click of the radio button
     * @throws SQLException
     * @see JDBC
     */
    public void allRadioButtonChecked(ActionEvent actionEvent) throws SQLException {
        appointmentTable.getSelectionModel().clearSelection();
        appointmentTable.getItems().clear();
        appointmentTable.setItems(JDBC.createAppointmentList());
    }


    /**
     * Returns the user back to the main menu screen.
     *
     * @param actionEvent - a mouse click on the 'Exit Button'
     * @throws IOException
     */
    public void exitButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
        Stage menuStage = (Stage)exitButton.getScene().getWindow();
        Scene menuScene = new Scene(root, 600, 400);
        menuStage.setTitle("Main Menu");
        menuStage.setScene(menuScene);
        menuStage.show();
    }
}