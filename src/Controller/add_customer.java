package Controller;

import Model.Country;
import Model.JDBC;
import Model.alertBoxInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

/**
 * Controller for the add_customer.fxml page. Handles the add button, cancel button, country combobox,
 * and has an initialize method.
 *
 */
public class add_customer {

    public TextField customerNameTbox;
    public TextField customerAddressTbox;
    public TextField postalCodeTbox;
    public TextField phoneNumberTbox;
    public ComboBox countryComboBox;
    public ComboBox divisionComboBox;
    public Button addCustomerAddButton;
    public Button addCustomerCancelButton;

    /**
     * Loads both comboboxes (country and division) will available values from the database
     *
     * @throws SQLException
     */
    public void initialize () throws SQLException {

        //Load combo boxes with available countries and divisions

        countryComboBox.setItems(JDBC.getCountryNames());
        divisionComboBox.setItems(JDBC.getAllDivisionNames());
    }

    /**
     * Upon mouse click, an INSERT query is made to the database. The query is surrounded by a try/catch
     * block to make sure invalid data is caught. User entered text fields are used to populate the query.
     * An alertbox is used to notify the user if the addition was successful or not
     *
     * @param actionEvent - a mouse click on the Add button.
     * @throws SQLException
     * @see Model.Customer
     */
    public void addCustomerButtonClick(ActionEvent actionEvent) throws SQLException {

        try {
            String sql = "INSERT INTO Customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";  //excluded primary key column
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, customerNameTbox.getText());
            ps.setString(2, customerAddressTbox.getText());     //user entered values in the textfields are substituted into the SQL Statement.
            ps.setString(3, postalCodeTbox.getText());
            ps.setString(4, phoneNumberTbox.getText());
            ps.setInt(5, JDBC.returnDivisionID(divisionComboBox.getSelectionModel().getSelectedItem().toString()));

            //execute SQL Query with user entered values
            int rowsAffected = ps.executeUpdate();

            //If customer addition was successful, load alertbox and go back to the main menu
            if (rowsAffected > 0) {
                alertBoxInterface alert = () -> { Alert myAlert = new Alert(Alert.AlertType.INFORMATION);
                                                  myAlert.setTitle("Addition Successful");
                                                  myAlert.setHeaderText("Customer added to DB!");
                                                  return myAlert.showAndWait();
                                                };
                alert.displayAlertBox();

                Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));   //back to the main menu
                Stage menuStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene menuScene = new Scene(root, 600, 400);
                menuStage.setTitle("Main Menu");
                menuStage.setScene(menuScene);
                menuStage.show();
            }

        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Data Entry Error");
            alert.setHeaderText("Data Entry Error");
            alert.setContentText("Please make sure all fields are filled in with correct data types");
            alert.showAndWait();
        }
    }

    /**
     * Takes the user back to the main menu
     *
     * @throws IOException
     */
    public void AddCustomerCancelButtonClick(ActionEvent actionEvent) throws IOException {  //go back to the main menu
        Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
        Stage menuStage = (Stage)addCustomerCancelButton.getScene().getWindow();
        Scene menuScene = new Scene(root, 600, 400);
        menuStage.setTitle("Main Menu");
        menuStage.setScene(menuScene);
        menuStage.show();
    }

    /**
     * Once a country has been selected from the country combobox, the division combobox will
     * automatically update to reflect the available divisions per that country that is selected.
     *
     * @param actionEvent - the changing/selection of a country in the country combobox.
     * @throws SQLException
     */
    public void countrySelected(ActionEvent actionEvent) throws SQLException {
        String str = countryComboBox.getSelectionModel().getSelectedItem().toString();
        for (Country c : JDBC.getCountryObjects()) {
            if (c.getCountryName().equals(str))
                divisionComboBox.setItems(JDBC.getCountrySpecificDivisionNames(c.getCountryID()));
        }
    }
}
