package Controller;

import Model.Country;
import Model.Division;
import Model.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static Model.Country.countryObjectList;


public class add_customer {
    public TextField customerNameTbox;
    public TextField customerAddressTbox;
    public TextField postalCodeTbox;
    public TextField phoneNumberTbox;
    public ComboBox countryComboBox;
    public ComboBox divisionComboBox;
    public Button addCustomerAddButton;
    public Button addCustomerCancelButton;
    private ObservableList<String> emptyList = FXCollections.observableArrayList();


    public void initialize () throws SQLException {
        Country.loadCountryObjects();
        countryComboBox.setItems(Country.loadCountryNames());
        divisionComboBox.setItems(Division.loadDivisions());


    }


    public void addCustomerButtonClick(ActionEvent actionEvent) throws SQLException {
        String sql = "INSERT INTO Customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";  //excluded primary key column
        PreparedStatement ps =  JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerNameTbox.getText());
        ps.setString(2, customerAddressTbox.getText());
        ps.setString(3, postalCodeTbox.getText());
        ps.setString(4, phoneNumberTbox.getText());
        ps.setInt(5, Division.returnDivisionID(divisionComboBox.getSelectionModel().getSelectedItem().toString()));
        int rowsAffected = ps.executeUpdate();
        System.out.println(rowsAffected + " Customer successfully added");


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
        for (Country c : countryObjectList) {
            if (c.getCountryName().equals(str))
                divisionComboBox.setItems(Division.loadSpecificDivisions(c.getCountryID()));
        }
    }
}
