package Controller;

import Model.Country;
import Model.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;




public class add_customer {
    public TextField customerNameTbox;
    public TextField customerAddressTbox;
    public TextField postalCodeTbox;
    public TextField phoneNumberTbox;
    public ComboBox countryComboBox;
    public ComboBox divisionComboBox;
    public Button addCustomerAddButton;
    public Button addCustomerCancelButton;



    public void initialize () throws SQLException {

        countryComboBox.setItems(JDBC.getCountryNames());
        divisionComboBox.setItems(JDBC.getAllDivisionNames());

    }


    public void addCustomerButtonClick(ActionEvent actionEvent) throws SQLException {
        try {
            String sql = "INSERT INTO Customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";  //excluded primary key column
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, customerNameTbox.getText());
            ps.setString(2, customerAddressTbox.getText());
            ps.setString(3, postalCodeTbox.getText());
            ps.setString(4, phoneNumberTbox.getText());
            ps.setInt(5, JDBC.returnDivisionID(divisionComboBox.getSelectionModel().getSelectedItem().toString()));
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Addition Successful");
                alert2.setHeaderText("Customer added to DB!");
                alert2.showAndWait();
            }
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Data Entry Error");
            alert.setHeaderText("Data Entry Error");
            alert.setContentText("Please make sure all fields are filled in with correct data types");
            alert.showAndWait();
        }
    }


    public void AddCustomerCancelButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
        Stage menuStage = (Stage)addCustomerCancelButton.getScene().getWindow();
        Scene menuScene = new Scene(root, 600, 400);
        menuStage.setTitle("Main Menu");
        menuStage.setScene(menuScene);
        menuStage.show();
    }


    public void selection(ActionEvent actionEvent) throws SQLException {
        String str = countryComboBox.getSelectionModel().getSelectedItem().toString();
        for (Country c : JDBC.getCountryObjects()) {
            if (c.getCountryName().equals(str))
                divisionComboBox.setItems(JDBC.getCountrySpecificDivisionNames(c.getCountryID()));
        }
    }
}
