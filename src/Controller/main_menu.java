package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class main_menu {
    public Button customerAddButton;
    public Button customerUpdateButton;
    public Button customerDeleteButton;
    public Button appointmentNewButton;
    public Button appointmentEditButton;
    public Button appointmentDeleteButton;

    public void addCustomerButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/add_customer.fxml"));
        Stage addCustomerStage = (Stage)customerAddButton.getScene().getWindow();
        Scene addCustomerScene = new Scene(root, 700, 400);
        addCustomerStage.setTitle("Add New Customer");
        addCustomerStage.setScene(addCustomerScene);
        addCustomerStage.show();
    }

    public void updateCustomerButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/edit_customer.fxml"));
        Stage editCustomerStage = (Stage)customerUpdateButton.getScene().getWindow();
        Scene editCustomerScene = new Scene(root, 700, 500);
        editCustomerStage.setTitle("Edit Customer");
        editCustomerStage.setScene(editCustomerScene);
        editCustomerStage.show();
    }

    public void deleteCustomerButtonClick(ActionEvent actionEvent) {
    }

    public void newApptButtonClick(ActionEvent actionEvent) {
    }

    public void editApptButtonClick(ActionEvent actionEvent) {
    }

    public void deleteApptButtonClick(ActionEvent actionEvent) {
    }
}
