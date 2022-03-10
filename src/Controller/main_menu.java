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
    public Button customerViewButton;

    public void addCustomerButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/add_customer.fxml"));
        Stage addCustomerStage = (Stage)customerAddButton.getScene().getWindow();
        Scene addCustomerScene = new Scene(root, 700, 400);
        addCustomerStage.setTitle("Add New Customer");
        addCustomerStage.setScene(addCustomerScene);
        addCustomerStage.show();
    }


    public void deleteCustomerButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/delete_customer.fxml"));
        Stage delCustomerStage = (Stage)customerDeleteButton.getScene().getWindow();
        Scene delCustomerScene = new Scene(root, 400, 200);
        delCustomerStage.setTitle("Delete Customer");
        delCustomerStage.setScene(delCustomerScene);
        delCustomerStage.show();
    }

    public void newApptButtonClick(ActionEvent actionEvent) {
    }

    public void editApptButtonClick(ActionEvent actionEvent) {
    }

    public void deleteApptButtonClick(ActionEvent actionEvent) {
    }

    public void viewCustomerTableButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/view_customer.fxml"));
        Stage viewCustomerStage = (Stage)customerViewButton.getScene().getWindow();
        Scene viewCustomerScene = new Scene(root, 800, 500);
        viewCustomerStage.setTitle("Delete Customer");
        viewCustomerStage.setScene(viewCustomerScene);
        viewCustomerStage.show();
    }
}
