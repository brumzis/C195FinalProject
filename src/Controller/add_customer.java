package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class add_customer {
    public TextField customerNameTbox;
    public TextField customerAddressTbox;
    public TextField postalCodeTbox;
    public TextField phoneNumberTbox;
    public ComboBox countryComboBox;
    public ComboBox divisionComboBox;
    public Button addCustomerAddButton;
    public Button addCustomerCancelButton;

    public void addCustomerButtonClick(ActionEvent actionEvent) {
    }

    public void AddCustomerCancelButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
        Stage menuStage = (Stage)addCustomerCancelButton.getScene().getWindow();
        Scene menuScene = new Scene(root, 600, 400);
        menuStage.setTitle("Main Menu");
        menuStage.setScene(menuScene);
        menuStage.show();
    }
}
