package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class edit_customer {
    public TextField editCustomerIDTbox;
    public TextField editCustomerNameTbox;
    public TextField editCustomerAddressTbox;
    public TextField editPostalCodeTbox;
    public TextField editPhoneNumberTbox;
    public ComboBox editCountryComboBox;
    public ComboBox editDivisionComboBox;
    public Button editCustomerButton;
    public Button editCustomerCancelButton;

    public void editCustomerUpdateButtonClick(ActionEvent actionEvent) {
    }

    public void editCustomerCancelButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
        Stage menuStage = (Stage)editCustomerCancelButton.getScene().getWindow();
        Scene menuScene = new Scene(root, 600, 400);
        menuStage.setTitle("Main Menu");
        menuStage.setScene(menuScene);
        menuStage.show();
    }
}
