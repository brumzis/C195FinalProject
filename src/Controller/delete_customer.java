package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class delete_customer {
    public TextField deleteCustomerIDTbox;
    public Button deleteCustomerButton;
    public Button deleteCustomerCancelButton;

    public void initialize(){

    }


    public void cancelButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
        Stage menuStage = (Stage)deleteCustomerCancelButton.getScene().getWindow();
        Scene menuScene = new Scene(root, 600, 400);
        menuStage.setTitle("Main Menu");
        menuStage.setScene(menuScene);
        menuStage.show();
    }

    public void deleteButtonClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Customer?");
        alert.setContentText("All customer appointments must be deleted before deleting customer! " + "Do you wish to continue?");
        alert.showAndWait();
        System.out.println("ok button pressed");
    }
}