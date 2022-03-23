package Controller;

import Model.JDBC;
import Model.alertBoxInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Controller for the delete_appointment.fxml page. Only two buttons populate this window,
 * a cancel button and a delete button. This controller handles both button events
 *
 */
public class delete_appointment {

    public TextField deleteApptTbox;
    public Button deleteApptButton;
    public Button cancelButton;

    /**
     * Upon a click of the cancel button the user will return to the main menu.
     *
     * @param actionEvent - a mouse click on the cancel button
     * @throws IOException
     */
    public void cancelButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
        Stage menuStage = (Stage)cancelButton.getScene().getWindow();
        Scene menuScene = new Scene(root, 600, 400);
        menuStage.setTitle("Main Menu");
        menuStage.setScene(menuScene);
        menuStage.show();
    }

    /**
     * The Delete Button click first takes the user entry from the textfield and stores it to
     * an integer value. In the event a non-integer is entered by the user, an exception will
     * be thrown and the user will be alerted via a text box. If an integer value is entered, the
     * user will be prompted if they are sure they want to delete the appointment, once confirmed
     * the entry will be used in an SQL Query to delete the selected row from the database.
     * Another alertbox is used to let the user know if the deletion was successful. If the
     * appointment ID was not found, the user will be alerted that the deletion was not successful.
     *
     * @param actionEvent - the click of the Delete Button.
     */
    public void apptDeleteButtonClick(ActionEvent actionEvent) {
        try {
            int apptID = Integer.parseInt(deleteApptTbox.getText());

            alertBoxInterface alert = () -> { Alert myAlert = new Alert(Alert.AlertType.CONFIRMATION);
                                              myAlert.setTitle("Delete Appointment?");
                                              myAlert.setContentText("Are you sure you wish to delete this appointment?");
                                              return myAlert.showAndWait();
                                            };
            Optional<ButtonType> result = alert.displayAlertBox();

            if(result.isPresent() && result.get() == ButtonType.OK) {             //If appointment ID exists, delete from the database
                String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
                PreparedStatement ps = JDBC.connection.prepareStatement(sql);
                ps.setInt(1, apptID);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    alertBoxInterface alert1 = () -> { Alert myAlert = new Alert(Alert.AlertType.INFORMATION);
                                                       myAlert.setTitle("Deletion Successful");
                                                       myAlert.setHeaderText("Appointment deleted");
                                                       return myAlert.showAndWait();
                                                     };
                    alert1.displayAlertBox();

                    Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));  //once deleted, return to the main menu
                    Stage menuStage = (Stage) cancelButton.getScene().getWindow();
                    Scene menuScene = new Scene(root, 600, 400);
                    menuStage.setTitle("Main Menu");
                    menuStage.setScene(menuScene);
                    menuStage.show();

                } else {
                    alertBoxInterface alert2 = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);    //error box is appointment ID not found
                                                       myAlert.setTitle("Deletion Error:");
                                                       myAlert.setHeaderText("Appointment not found");
                                                       return myAlert.showAndWait();
                                                     };
                    alert2.displayAlertBox();
                }
            }
        } catch (NumberFormatException | SQLException | IOException e) {                           //error box for invalid data
            alertBoxInterface alert2 = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);
                                               myAlert.setTitle("Deletion Error:");
                                               myAlert.setHeaderText("Please enter a valid Appointment ID");
                                               return myAlert.showAndWait();
                                             };
            alert2.displayAlertBox();
        }
    }

}
