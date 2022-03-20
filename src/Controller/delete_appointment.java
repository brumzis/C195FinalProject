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


public class delete_appointment {

    public TextField deleteApptTbox;
    public Button deleteApptButton;
    public Button cancelButton;


    public void cancelButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
        Stage menuStage = (Stage)cancelButton.getScene().getWindow();
        Scene menuScene = new Scene(root, 600, 400);
        menuStage.setTitle("Main Menu");
        menuStage.setScene(menuScene);
        menuStage.show();
    }


    public void apptDeleteButtonClick(ActionEvent actionEvent) {
        try {
            int apptID = Integer.parseInt(deleteApptTbox.getText());

            alertBoxInterface alert = () -> { Alert myAlert = new Alert(Alert.AlertType.CONFIRMATION);
                                              myAlert.setTitle("Delete Appointment?");
                                              myAlert.setContentText("Are you sure you wish to delete this appointment?");
                                              return myAlert.showAndWait();
                                            };
            Optional<ButtonType> result = alert.displayAlertBox();

            if(result.isPresent() && result.get() == ButtonType.OK) {
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

                    Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
                    Stage menuStage = (Stage) cancelButton.getScene().getWindow();
                    Scene menuScene = new Scene(root, 600, 400);
                    menuStage.setTitle("Main Menu");
                    menuStage.setScene(menuScene);
                    menuStage.show();

                } else {
                    alertBoxInterface alert2 = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);
                                                       myAlert.setTitle("Deletion Error:");
                                                       myAlert.setHeaderText("Appointment not found");
                                                       return myAlert.showAndWait();
                                                     };
                    alert2.displayAlertBox();
                }
            }
        } catch (NumberFormatException | SQLException | IOException e) {
            alertBoxInterface alert2 = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);
                                               myAlert.setTitle("Deletion Error:");
                                               myAlert.setHeaderText("Please enter a valid Appointment ID");
                                               return myAlert.showAndWait();
                                             };
            alert2.displayAlertBox();
        }
    }

}
