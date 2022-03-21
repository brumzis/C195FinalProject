package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;


public class customer_appointments {

    public ComboBox<Customer> customerComboBox;
    public ComboBox<String> monthComboBox;
    public TextField filterTextBox;
    public TextField totalsTextBox;
    public Button customerBackButton;
    public TableColumn customerApptID;
    public TableColumn customerTitle;
    public TableColumn customerType;
    public TableColumn customerDescription;
    public TableColumn customerStart;
    public TableColumn customerEnd;
    public TableColumn customerContactID;
    public TableView customerTable;


    public void initialize() throws SQLException {

        customerComboBox.setItems(JDBC.createCustomerList());
        ObservableList<String> myList = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        monthComboBox.setItems(myList);

        customerApptID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("apptID"));
        customerTitle.setCellValueFactory(new PropertyValueFactory<Appointment, String>("apptTitle"));
        customerType.setCellValueFactory(new PropertyValueFactory<Appointment, String> ("apptType"));
        customerDescription.setCellValueFactory(new PropertyValueFactory<Appointment, String>("apptDescription"));
        customerStart.setCellValueFactory(new PropertyValueFactory<Appointment, DateTimeUtility>("apptStart"));
        customerEnd.setCellValueFactory(new PropertyValueFactory<Appointment, DateTimeUtility>("apptEnd"));
        customerContactID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("apptContact"));

    }

    public void customerComboBoxSelected(ActionEvent actionEvent) throws SQLException {

        Customer c = customerComboBox.getSelectionModel().getSelectedItem();
        customerTable.setItems(JDBC.createCustomerSchedule(c.getCustomerID()));

    }

    public void monthComboBoxSelected(ActionEvent actionEvent) throws SQLException {

        if(customerComboBox.getSelectionModel().getSelectedItem() == null) {
            alertBoxInterface alert = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);
                myAlert.setTitle("Error:");
                myAlert.setHeaderText("No customer selected");
                return myAlert.showAndWait();
            };
            alert.displayAlertBox();
        }

        Customer c = customerComboBox.getSelectionModel().getSelectedItem();
        String selection = monthComboBox.getSelectionModel().getSelectedItem();
        customerTable.setItems(JDBC.createCustomerScheduleByMonth(c.getCustomerID(), selection));
        System.out.println("from monthcombobox selection " + selection);

    }

    public void filterTextBoxEntered(ActionEvent actionEvent) {
        String s = filterTextBox.getText();
        System.out.println(s);
    }

    public void customerBackButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
        Stage menuStage = (Stage)customerBackButton.getScene().getWindow();
        Scene menuScene = new Scene(root, 600, 400);
        menuStage.setTitle("Main Menu");
        menuStage.setScene(menuScene);
        menuStage.show();
    }
}
