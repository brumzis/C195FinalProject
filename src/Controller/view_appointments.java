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
    public void scheduleNewApptClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/add_appointment.fxml"));
        Stage addApptStage = (Stage)scheduleNewButton.getScene().getWindow();
        Scene addApptScene = new Scene(root, 800, 600);
        addApptStage.setTitle("Add Appointment");
        addApptStage.setScene(addApptScene);
        addApptStage.show();
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
    public void updateApptButtonClick(ActionEvent actionEvent) throws IOException, SQLException {
        if (appointmentTable.getSelectionModel().getSelectedItem() == null) {
            alertBoxInterface alert = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);
                                              myAlert.setTitle("Error:");
                                              myAlert.setHeaderText("No appointment selected");
                                              return myAlert.showAndWait();
                                            };
            alert.displayAlertBox();
        }
        else {
            Appointment a = appointmentTable.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/update_appointment.fxml"));
            Parent root = loader.load();
            update_appointment controller = loader.getController();
            controller.getSelectedAppointment(a);                         //passing appointment object to update_appointment.java
            Stage myStage = (Stage) updateApptButton.getScene().getWindow();
            Scene myScene = new Scene(root, 800, 600);
            myStage.setTitle("Edit Appointment");
            myStage.setScene(myScene);
            myStage.show();
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
    public void deleteApptButtonClick(ActionEvent actionEvent) throws SQLException {
        if (appointmentTable.getSelectionModel().getSelectedItem() == null) {
            alertBoxInterface alert = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);
                                              myAlert.setTitle("Error:");
                                              myAlert.setHeaderText("No appointment selected");
                                              return myAlert.showAndWait();
                                            };
            alert.displayAlertBox();
        }
        else {
            alertBoxInterface alert1 = () -> { Alert myAlert = new Alert(Alert.AlertType.CONFIRMATION);
                                               myAlert.setTitle("Delete Appointment");
                                               myAlert.setContentText("Are you sure?");
                                               return myAlert.showAndWait();
                                             };
            Optional<ButtonType> result = alert1.displayAlertBox();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Appointment a = appointmentTable.getSelectionModel().getSelectedItem();
                String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
                PreparedStatement ps = JDBC.connection.prepareStatement(sql);
                ps.setInt(1, a.getApptID());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    alertBoxInterface alert2 = () -> { Alert myAlert = new Alert(Alert.AlertType.INFORMATION);
                                                       myAlert.setTitle("Success");
                                                       myAlert.setHeaderText("Appointment Cancelled");
                                                       myAlert.setContentText("Appointment successfully deleted from DB");
                                                       return myAlert.showAndWait();
                                                     };
                    alert2.displayAlertBox();
                }
                else {
                    alertBoxInterface alert = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);
                                                      myAlert.setTitle("Deletion Error:");
                                                      myAlert.setHeaderText("Appointment not found");
                                                      return myAlert.showAndWait();
                                                    };
                    alert.displayAlertBox();
                }

                appointmentTable.getSelectionModel().clearSelection();
                appointmentTable.getItems().clear();
                appointmentTable.setItems(JDBC.createAppointmentList());
            }
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
    public void monthRadioButtonChecked(ActionEvent actionEvent) throws SQLException {
        appointmentTable.getSelectionModel().clearSelection();
        appointmentTable.getItems().clear();
        appointmentTable.setItems(JDBC.createAppointmentListCurrentMonth());
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
    public void weekRadioButtonChecked(ActionEvent actionEvent) throws SQLException {
        appointmentTable.getSelectionModel().clearSelection();
        appointmentTable.getItems().clear();
        appointmentTable.setItems(JDBC.createAppointmentListCurrentWeek());
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
    public void allRadioButtonChecked(ActionEvent actionEvent) throws SQLException {
        appointmentTable.getSelectionModel().clearSelection();
        appointmentTable.getItems().clear();
        appointmentTable.setItems(JDBC.createAppointmentList());
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
    public void exitButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
        Stage menuStage = (Stage)exitButton.getScene().getWindow();
        Scene menuScene = new Scene(root, 600, 400);
        menuStage.setTitle("Main Menu");
        menuStage.setScene(menuScene);
        menuStage.show();
    }
}