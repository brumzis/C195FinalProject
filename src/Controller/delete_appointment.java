package Controller;

import Model.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Appointment?");
            alert.setContentText("Are you sure you wish to delete this appointment?");
            alert.showAndWait();

            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, apptID);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " appt deleted from table");
                Alert del = new Alert(Alert.AlertType.INFORMATION);
                del.setTitle("Deletion Successful");
                del.setHeaderText("Appointment deleted");
                del.showAndWait();
                Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
                Stage menuStage = (Stage)cancelButton.getScene().getWindow();
                Scene menuScene = new Scene(root, 600, 400);
                menuStage.setTitle("Main Menu");
                menuStage.setScene(menuScene);
                menuStage.show();
            }
            else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Deletion Error:");
                alert1.setHeaderText("Appointment not found");
                alert1.showAndWait();
            }
        } catch (NumberFormatException | SQLException | IOException e) {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Deletion Error:");
            alert2.setHeaderText("Please enter a valid Appointment ID");
            alert2.showAndWait();
        }
    }

}
