package Controller;

import Model.Customer;
import Model.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
        tableDivisionColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        customerTable.setEditable(true);
        try {customerTable.setItems(createCustomerList());} catch (SQLException e) {e.printStackTrace();}
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
            Alert noSelection = new Alert(Alert.AlertType.ERROR);
            noSelection.setTitle("Error");
            noSelection.setHeaderText("No customer selected");
            noSelection.showAndWait();
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
                Alert noSelection = new Alert(Alert.AlertType.ERROR);
                noSelection.setTitle("Error");
                noSelection.setHeaderText("No customer selected");
                noSelection.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Customer?");
                alert.setContentText("All customer appointments must be deleted before deleting customer! " + "Do you wish to continue?");
                alert.showAndWait();
                Customer c = customerTable.getSelectionModel().getSelectedItem();
                JDBC.deleteCustomer(c.getCustomerID());
                customerTable.getSelectionModel().clearSelection();
                customerTable.getItems().clear();
                customerTable.setItems(createCustomerList());
            }
        } catch (Exception e) {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Deletion Error:");
            alert2.setHeaderText("Please enter a valid Customer ID");
            alert2.showAndWait();
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

    public Customer sendSelectedCustomer(Customer c) {
        return c;
    }

    public void editCancel(TableColumn.CellEditEvent<Customer, String> customerStringCellEditEvent) {
    }

    public void editCommit(TableColumn.CellEditEvent<Customer, String> customerStringCellEditEvent) {
    }

    public void editStart(TableColumn.CellEditEvent<Customer, String> customerStringCellEditEvent) {
    }
}
