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


    public void createCustomerButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/add_customer.fxml"));
        Stage addCustomerStage = (Stage)createCustomerButton.getScene().getWindow();
        Scene addCustomerScene = new Scene(root, 700, 400);
        addCustomerStage.setTitle("Add New Customer");
        addCustomerStage.setScene(addCustomerScene);
        addCustomerStage.show();
    }


    public void updateCustomerButtonClick(ActionEvent actionEvent) throws IOException, SQLException {
        if (customerTable.getSelectionModel().getSelectedItem() == null) {
            alertBoxInterface alert = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);
                                              myAlert.setTitle("Error");
                                              myAlert.setHeaderText("Customer appointments found");
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


    public void deleteCustomerButtonClick(ActionEvent actionEvent) throws SQLException {
        try {
            if (customerTable.getSelectionModel().getSelectedItem() == null) {
                alertBoxInterface alert = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);
                                                  myAlert.setTitle("Error");
                                                  myAlert.setHeaderText("No customer selected");
                                                  return myAlert.showAndWait();
                                                };
                alert.displayAlertBox();
            }
            else {
                alertBoxInterface alert1 = () -> { Alert myAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    myAlert.setTitle("Delete Customer");
                    myAlert.setHeaderText("Customer appointment warning");
                    myAlert.setContentText("All customer appointments must be deleted before deleting customer! Do you wish to continue?");
                    return myAlert.showAndWait();
                };
                Optional<ButtonType> result = alert1.displayAlertBox();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Customer c = customerTable.getSelectionModel().getSelectedItem();

                    if (JDBC.checkForCustomerAppointments(c.getCustomerID()))
                        throw new IllegalStateException("Cannot have appointments");

                    JDBC.deleteCustomer(c.getCustomerID());

                    customerTable.getSelectionModel().clearSelection();
                    customerTable.getItems().clear();
                    customerTable.setItems(createCustomerList());
                }
            }
        } catch (IllegalStateException e) {
            alertBoxInterface alert = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);
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


    public void exitButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
        Stage menuStage = (Stage)deleteCustomerButton.getScene().getWindow();
        Scene menuScene = new Scene(root, 600, 400);
        menuStage.setTitle("Main Menu");
        menuStage.setScene(menuScene);
        menuStage.show();
    }


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
