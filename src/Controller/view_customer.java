package Controller;

import Model.Customer;
import Model.JDBC;
import Model.alertBoxInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import static Model.JDBC.createCustomerList;

/**
 * Controller for the view_customer.fxml page. This controller handles the tableview, 'Create Customer
 * Button', 'Update Customer Button', 'Delete Customer Button', and the 'Exit Button'. The controller
 * also initializes the tableview when the page is loaded.
 *
 */
public class view_customer implements Initializable {

    public TableView<Customer> customerTable;
    public TableColumn<Customer, Integer> tableIDColumn;
    public TableColumn<Customer, String> tableNameColumn;
    public TableColumn<Customer, String> tableAddressColumn;
    public TableColumn<Customer, String> tablePostalColumn;
    public TableColumn<Customer, String> tablePhoneColumn;
    public TableColumn<Customer, Integer> tableDivisionColumn;
    public Button createCustomerButton;
    public Button updateCustomerButton;
    public Button deleteCustomerButton;
    public Button exitButton;

    /**
     * When this page is loaded, a tableview is created. The table is set up to accept Customer Objects,
     * and is also set to be editable. A method is run that loads all customers from the
     * database into the table.
     *
     * @see Customer
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tableIDColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerID"));
        tableNameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        tableNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        tableAddressColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerAddress"));
        tableAddressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        tablePostalColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerPostalCode"));
        tablePostalColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        tablePhoneColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerPhone"));
        tablePhoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        tableDivisionColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerDivision"));
        tableDivisionColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter() {
            @Override
            public Integer fromString(String val) {
                try {return super.fromString(val);}
                catch(NumberFormatException ex) {return 0;}}}));

        customerTable.setEditable(true);
        try {customerTable.setItems(createCustomerList());}
        catch (SQLException e) {e.printStackTrace();}
    }

    /**
     * Once clicked, the 'Create Customer Button' will take the user to the add_customer.fxml screen,
     * where new customers can be added to the database. See add_customer.java for more details.
     *
     * @param actionEvent - a mouse click on the 'Create Customer Button'.
     * @throws IOException
     * @see Customer
     * @see add_customer
     */
    public void createCustomerButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/add_customer.fxml"));
        Stage addCustomerStage = (Stage)createCustomerButton.getScene().getWindow();
        Scene addCustomerScene = new Scene(root, 700, 400);
        addCustomerStage.setTitle("Add New Customer");
        addCustomerStage.setScene(addCustomerScene);
        addCustomerStage.show();
    }

    /**
     * Clicking on the 'Update Customer Button' will first check the table to make sure something
     * has been selected. If no row has been selected by the user, an alertbox will be generated telling
     * the user that nothing has been selected yet. If a customer has been selected by the user, it will
     * call the controller for the edit_customer page and pass that selected object to that controller
     * to load the object attributes into the edit_customer page. The user will then be taken to the
     * edit_customer.fxml page.
     *
     * @param actionEvent - a mouse click on the 'Update Customer Button'.
     * @throws IOException
     * @see edit_customer
     */
    public void updateCustomerButtonClick(ActionEvent actionEvent) throws IOException, SQLException {
        if (customerTable.getSelectionModel().getSelectedItem() == null) {                              //if the user hasn't selected a row, display an alertbox.
            alertBoxInterface alert = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);
                                              myAlert.setTitle("Error");
                                              myAlert.setHeaderText("No Customer Selected");
                                              return myAlert.showAndWait();
                                            };
            alert.displayAlertBox();
        }
        else {
            Customer c = customerTable.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/edit_customer.fxml"));
            Parent root = loader.load();
            edit_customer controller = loader.getController();
            controller.getSelectedCustomer(c);                         //passing customer object to edit_customer.java

            Stage editCustomerStage = (Stage) updateCustomerButton.getScene().getWindow();
            Scene editCustomerScene = new Scene(root, 700, 500);
            editCustomerStage.setTitle("Edit Customer");
            editCustomerStage.setScene(editCustomerScene);
            editCustomerStage.show();
        }
    }

    /**
     * Clicking on the 'Delete Customer Button' will first check the table to make sure a row has been
     * selected. If nothing has been selected yet, an alertbox will notify the user. If a customer
     * has been selected, an alertbox will ask the customer if he/she is sure he/she wants to delete,
     * and a reminder will be given that the customer cannot be deleted if he/she has any existing
     * appointments in the database. If the user wishes to proceed, the database will be queried to see
     * if that customer selected has any existing appointments - if so, the user will be notified via an
     * alertbox, and the deletion will not be successful. If the customer does not have any appointments
     * in the database, the customer will be deleted from the database. In either case, the user will
     * be notified (via an alertbox) if the deletion was successful or not.
     *
     * @param actionEvent - a mouse click on the 'Delete Customer Button'
     * @throws SQLException
     */
    public void deleteCustomerButtonClick(ActionEvent actionEvent) throws SQLException {
        try {
            if (customerTable.getSelectionModel().getSelectedItem() == null) {                          //if nothing was selected by the user, generate an alertbox.
                alertBoxInterface alert = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);
                                                  myAlert.setTitle("Error");
                                                  myAlert.setHeaderText("No customer selected");
                                                  return myAlert.showAndWait();
                                                };
                alert.displayAlertBox();
            }
            else {
                alertBoxInterface alert1 = () -> { Alert myAlert = new Alert(Alert.AlertType.CONFIRMATION);   //remind user that customer cannot have existing appointments.
                    myAlert.setTitle("Delete Customer");
                    myAlert.setHeaderText("Customer appointment warning");
                    myAlert.setContentText("All customer appointments must be deleted before deleting customer! Do you wish to continue?");
                    return myAlert.showAndWait();
                };
                Optional<ButtonType> result = alert1.displayAlertBox();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Customer c = customerTable.getSelectionModel().getSelectedItem();

                    if (JDBC.checkForCustomerAppointments(c.getCustomerID()))               //check to make sure no existing appointments exist.
                        throw new IllegalStateException("Cannot have appointments");

                    JDBC.deleteCustomer(c.getCustomerID());                     //if no appointments, delete the customer

                    customerTable.getSelectionModel().clearSelection();         //clear the table and reload to reflect new status of the table.
                    customerTable.getItems().clear();
                    customerTable.setItems(createCustomerList());
                }
            }
        } catch (IllegalStateException e) {
            alertBoxInterface alert = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);               //generate errors
                                              myAlert.setTitle("Deletion Error");
                                              myAlert.setHeaderText("Customer cannot have appointments");
                                              return myAlert.showAndWait();
                                            };
            alert.displayAlertBox();


        } catch(Exception e) {
            alertBoxInterface alert = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);
                                              myAlert.setTitle("Deletion Error");
                                              myAlert.setHeaderText("Please enter a valid Customer ID");
                                              return myAlert.showAndWait();
                                            };
            alert.displayAlertBox();
        }
    }

    /**
     * Takes the user back to the main menu.
     *
     * @param actionEvent - a mouse click on the 'Exit Button'
     * @throws IOException
     */
    public void exitButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
        Stage menuStage = (Stage)deleteCustomerButton.getScene().getWindow();
        Scene menuScene = new Scene(root, 600, 400);
        menuStage.setTitle("Main Menu");
        menuStage.setScene(menuScene);
        menuStage.show();
    }

    /**
     * If a cell in the 'Name' Column is edited by the user, the method will run the JDBC.updateCustomer
     * command with the new value inserted, updating the Customer with the new data. All normal checks
     * for proper data will be implemented, with an alertbox being generated if the data is not valid.
     *
     * @param customerStringCellEditEvent - the editing of a cell in the name column
     * @throws SQLException
     */
    public void nameEditCommit(TableColumn.CellEditEvent<Customer, String> customerStringCellEditEvent) throws SQLException {

        Customer c = customerTable.getSelectionModel().getSelectedItem();
        try {
            c.setCustomerName(customerStringCellEditEvent.getNewValue());
            int i = JDBC.updateCustomer(c);
        } catch (Exception e) {
            alertBoxInterface alert = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);
                                              myAlert.setTitle("Update Error");
                                              myAlert.setHeaderText("Please enter valid values");
                                              return myAlert.showAndWait();
                                            };
            alert.displayAlertBox();
        }
    }

    /**
     * If a cell in the 'Address' Column is edited by the user, the method will run the JDBC.updateCustomer
     * command with the new value inserted, updating the Customer with the new data. All normal checks
     * for proper data will be implemented, with an alertbox being generated if the data is not valid.
     *
     * @param customerStringCellEditEvent - the editing of a cell in the Address column
     * @throws SQLException
     */
    public void addressEditCommit(TableColumn.CellEditEvent<Customer, String> customerStringCellEditEvent) {

        Customer c = customerTable.getSelectionModel().getSelectedItem();
        try {
            c.setCustomerAddress(customerStringCellEditEvent.getNewValue());
            int i = JDBC.updateCustomer(c);
        } catch (Exception e) {
            alertBoxInterface alert = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);
                                              myAlert.setTitle("Update Error");
                                              myAlert.setHeaderText("Please enter valid values");
                                              return myAlert.showAndWait();
                                            };
            alert.displayAlertBox();
        }
    }

    /**
     * If a cell in the 'Postal Code' Column is edited by the user, the method will run the JDBC.updateCustomer
     * command with the new value inserted, updating the Customer with the new data. All normal checks
     * for proper data will be implemented, with an alertbox being generated if the data is not valid.
     *
     * @param customerStringCellEditEvent - the editing of a cell in the Postal Code column
     * @throws SQLException
     */
    public void postalEditCommit(TableColumn.CellEditEvent<Customer, String> customerStringCellEditEvent) {

        Customer c = customerTable.getSelectionModel().getSelectedItem();
        try {
            c.setCustomerPostalCode(customerStringCellEditEvent.getNewValue());
            int i = JDBC.updateCustomer(c);
        } catch (Exception e) {
            alertBoxInterface alert = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);
                                              myAlert.setTitle("Update Error");
                                              myAlert.setHeaderText("Please enter valid values");
                                              return myAlert.showAndWait();
                                            };
            alert.displayAlertBox();
        }
    }

    /**
     * If a cell in the 'Phone #' Column is edited by the user, the method will run the JDBC.updateCustomer
     * command with the new value inserted, updating the Customer with the new data. All normal checks
     * for proper data will be implemented, with an alertbox being generated if the data is not valid.
     *
     * @param customerStringCellEditEvent - the editing of a cell in the Phone# column
     * @throws SQLException
     */
    public void phoneEditCommit(TableColumn.CellEditEvent<Customer, String> customerStringCellEditEvent) {

        Customer c = customerTable.getSelectionModel().getSelectedItem();
        try {
            c.setCustomerPhone(customerStringCellEditEvent.getNewValue());
            int i = JDBC.updateCustomer(c);
        } catch (Exception e) {
            alertBoxInterface alert = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);
                                              myAlert.setTitle("Update Error");
                                              myAlert.setHeaderText("Please enter valid values");
                                              return myAlert.showAndWait();
                                            };
            alert.displayAlertBox();
        }
    }

    /**
     * If a cell in the 'DivisionID' Column is edited by the user, the method will run the JDBC.updateCustomer
     * command with the new value inserted, updating the Customer with the new data. All normal checks
     * for proper data will be implemented, with an alertbox being generated if the data is not valid.
     *
     * @param customerIntegerCellEditEvent - the editing of a cell in the DivisionID column
     * @throws SQLException
     */
    public void divisionEditCommit(TableColumn.CellEditEvent<Customer, Integer> customerIntegerCellEditEvent){

        try {
            Customer c = customerTable.getSelectionModel().getSelectedItem();
            c.setCustomerDivision(customerIntegerCellEditEvent.getNewValue());
            int i = JDBC.updateCustomer(c);
            System.out.println(i);
            if (i < 1) {
                alertBoxInterface alert = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);
                                                  myAlert.setTitle("Update Error");
                                                  myAlert.setHeaderText("Please enter valid values");
                                                  return myAlert.showAndWait();
                                                };
                alert.displayAlertBox();
            }

        } catch (Exception e) {
            alertBoxInterface alert = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);
                                              myAlert.setTitle("Update Error");
                                              myAlert.setHeaderText("Please enter valid values");
                                              return myAlert.showAndWait();
                                            };
            alert.displayAlertBox();
        }
    }
}
