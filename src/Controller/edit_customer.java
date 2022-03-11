package Controller;

import Model.Country;
import Model.Customer;
import Model.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class edit_customer implements Initializable {
    public TextField editCustomerIDTbox;
    public TextField editCustomerNameTbox;
    public TextField editCustomerAddressTbox;
    public TextField editPostalCodeTbox;
    public TextField editPhoneNumberTbox;
    public ComboBox editCountryComboBox;
    public ComboBox editDivisionComboBox;
    public Button editCustomerButton;
    public Button editCustomerCancelButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }
    public void editCustomerUpdateButtonClick(ActionEvent actionEvent) throws SQLException, IOException {
        try {
            int i = Integer.parseInt(editCustomerIDTbox.getText());
            Customer c = new Customer(i, editCustomerNameTbox.getText(), editCustomerAddressTbox.getText(), editPostalCodeTbox.getText(), editPhoneNumberTbox.getText(), JDBC.returnDivisionID((String) editDivisionComboBox.getValue()));
            JDBC.updateCustomer(c);
        } catch (Exception e) {
            Alert errorBox = new Alert(Alert.AlertType.ERROR);
            errorBox.setTitle("Error");
            errorBox.setHeaderText("All fields require valid data");
            errorBox.showAndWait();
        }
    }

    public void editCustomerCancelButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
        Stage menuStage = (Stage)editCustomerCancelButton.getScene().getWindow();
        Scene menuScene = new Scene(root, 600, 400);
        menuStage.setTitle("Main Menu");
        menuStage.setScene(menuScene);
        menuStage.show();
    }

    public Customer getSelectedCustomer(Customer c) throws SQLException {
        System.out.println(c.getCustomerID());
        editCustomerIDTbox.setText(String.valueOf(c.getCustomerID()));
        editCustomerNameTbox.setText(c.getCustomerName());
        editCustomerAddressTbox.setText(c.getCustomerAddress());
        editPhoneNumberTbox.setText(c.getCustomerPhone());
        editPostalCodeTbox.setText(c.getCustomerPostalCode());
        editCountryComboBox.setItems(JDBC.getCountryNames());
        editDivisionComboBox.setItems(JDBC.getAllDivisionNames());
        editDivisionComboBox.setValue(c.getCustomerDivisionName());
        return c;
    }

    public void countrySelected(ActionEvent actionEvent) throws SQLException {
        String str = editCountryComboBox.getSelectionModel().getSelectedItem().toString();
        for (Country c : JDBC.getCountryObjects()) {
            if (c.getCountryName().equals(str)) {
                editDivisionComboBox.setItems(JDBC.getCountrySpecificDivisionNames(c.getCountryID()));
                editDivisionComboBox.setValue(null);
            }
        }
    }

}
