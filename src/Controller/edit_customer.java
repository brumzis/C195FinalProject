package Controller;

import Model.Country;
import Model.Customer;
import Model.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controller for the edit_customer.fxml page. This controller manages an Update Button, a Cancel
 * Button, two combo boxes, and has a method used to populate the fields on this screen.
 * The method belonging to this controller is actually called from a different screen using
 * a loader with the edit_customer controller.
 *
 */
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

    /**
     * Once the update button is clicked all fields' data are used in a constructor to create/overwrite
     * the existing customer. The customer ID field cannot be modified, thus a new customer cannot be
     * created from this screen. Any invalid data will generate an exception, which will alert the user
     * through an alertbox. If the update fails, the user will be notified with an alertbox.
     *
     * @param actionEvent - a mouse click on the Update Button
     * @throws SQLException
     * @see Customer
     */
    public void editCustomerUpdateButtonClick(ActionEvent actionEvent) throws SQLException, IOException {
        try {
            int i = Integer.parseInt(editCustomerIDTbox.getText());
            Customer c = new Customer(i, editCustomerNameTbox.getText(), editCustomerAddressTbox.getText(), editPostalCodeTbox.getText(), editPhoneNumberTbox.getText(), JDBC.returnDivisionID((String) editDivisionComboBox.getValue()), editCountryComboBox.getValue().toString());
            int j = JDBC.updateCustomer(c);
            if (j > 0) {
                Parent root = FXMLLoader.load(getClass().getResource("/view/view_customer.fxml"));  //if successful go back to the view customer screen
                Stage viewCustomerStage = (Stage) editCustomerButton.getScene().getWindow();
                Scene viewCustomerScene = new Scene(root, 800, 500);
                viewCustomerStage.setTitle("View Customers");
                viewCustomerStage.setScene(viewCustomerScene);
                viewCustomerStage.show();
            }
        } catch (Exception e) {                                    //notify the user if there is invalid data in one or more of the fields.
            Alert errorBox = new Alert(Alert.AlertType.ERROR);
            errorBox.setTitle("Error");
            errorBox.setHeaderText("All fields require valid data");
            errorBox.showAndWait();
        }
    }

    /**
     * If the cancel button is clicked, the user will return to the main menu
     *
     * @param actionEvent - a mouse click of the cancel button
     * @throws IOException
     */
    public void editCustomerCancelButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
        Stage menuStage = (Stage) editCustomerCancelButton.getScene().getWindow();
        Scene menuScene = new Scene(root, 600, 400);
        menuStage.setTitle("Main Menu");
        menuStage.setScene(menuScene);
        menuStage.show();
    }

    /**
     * This method is called from the view_customer page by instantiating a edit_customer loader
     * from there. The view_customer page takes the selected customer object, and passes it
     * to this page via this method. The customer object is accepted here and used to populate
     * the text fields and combo boxes on the page.
     *
     * @param c - representing a customer object passed in from the view_customer controller
     * @return Customer Object
     * @throws SQLException
     * @see Customer
     * @see view_customer
     */
    public Customer getSelectedCustomer(Customer c) throws SQLException {
        System.out.println(c.getCustomerID());
        editCustomerIDTbox.setText(String.valueOf(c.getCustomerID()));    //populate the fields with the customer object passed
        editCustomerNameTbox.setText(c.getCustomerName());                //in from the view_customer page
        editCustomerAddressTbox.setText(c.getCustomerAddress());
        editPhoneNumberTbox.setText(c.getCustomerPhone());
        editPostalCodeTbox.setText(c.getCustomerPostalCode());
        editCountryComboBox.setItems(JDBC.getCountryNames());
        editCountryComboBox.setValue(c.getCustomerCountry());
        editDivisionComboBox.setItems(JDBC.getCountrySpecificDivisionNames(JDBC.returnCountryID(c.getCustomerDivision())));
        editDivisionComboBox.setValue(c.getCustomerDivisionName());
        return c;
    }

    /**
     * Selecting/changing a country value from the combo box will trigger an actionEvent. Once triggered,
     * the division combobox will populate with all divisions associated with that selected country.
     *
     * @param actionEvent - a selection made within the country combo box triggers the event.
     * @throws SQLException
     * @see Country
     */
    public void countrySelected(ActionEvent actionEvent) throws SQLException {
        String str = editCountryComboBox.getValue().toString();
        System.out.println(str);
        for (Country c : JDBC.getCountryObjects()) {
            if (c.getCountryName().equals(str)) {
                editDivisionComboBox.setItems(JDBC.getCountrySpecificDivisionNames(c.getCountryID()));   //set the division combobox with the correct divisions

            }
        }
    }

    public void divisionBoxSelect(ActionEvent actionEvent) throws SQLException {

    }

    

}