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

/**
 * Page where user can add a new customer to the database
 *
 *
 *
 *
 * @param
 * @return
 * @throws
 * @see
 */
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
    public Button customerGoButton;
    public Button monthGoButton;
    public Button filterGoButton;

    /**
     * Page where user can add a new customer to the database
     *
     *
     *
     *
     * @param
     * @return
     * @throws
     * @see
     */
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

    /**
     * Page where user can add a new customer to the database
     *
     *
     *
     *
     * @param
     * @return
     * @throws
     * @see
     */
    public void customerGoButtonClick(ActionEvent actionEvent) throws SQLException {

        customerTable.getItems().clear();
        monthComboBox.getSelectionModel().clearSelection();
        totalsTextBox.setText(null);
        filterTextBox.setText(null);
        if(customerComboBox.getSelectionModel().getSelectedItem() != null) {
            Customer c = customerComboBox.getSelectionModel().getSelectedItem();
            customerTable.setItems(JDBC.createCustomerSchedule(c.getCustomerID()));
            int size = JDBC.createCustomerSchedule(c.getCustomerID()).size();
            totalsTextBox.setText(Integer.toString(size));
        }
        else
            totalsTextBox.setText("0");
    }

    /**
     * Page where user can add a new customer to the database
     *
     *
     *
     *
     * @param
     * @return
     * @throws
     * @see
     */
    public void monthGoButtonClick(ActionEvent actionEvent) throws SQLException {

        customerTable.getItems().clear();
        customerComboBox.getSelectionModel().clearSelection();
        filterTextBox.setText(null);
        totalsTextBox.setText(null);
        if (monthComboBox.getSelectionModel().getSelectedItem() != null) {
            String selection = monthComboBox.getSelectionModel().getSelectedItem();
            customerTable.setItems(JDBC.createCustomerScheduleByMonth(selection));
            int size = JDBC.createCustomerScheduleByMonth(selection).size();
            totalsTextBox.setText(Integer.toString(size));
        }
        else
            totalsTextBox.setText("0");
    }

    /**
     * Page where user can add a new customer to the database
     *
     *
     *
     *
     * @param
     * @return
     * @throws
     * @see
     */
    public void filterGoButtonClick(ActionEvent actionEvent) throws SQLException {

        customerTable.getItems().clear();
        customerComboBox.getSelectionModel().clearSelection();
        monthComboBox.getSelectionModel().clearSelection();
        totalsTextBox.setText(null);
        String s = filterTextBox.getText();
        if(s != null) {
            customerTable.setItems(JDBC.createCustomerScheduleByType(s));
            int size = JDBC.createCustomerScheduleByType(s).size();
            totalsTextBox.setText(Integer.toString(size));
        }
        else
            totalsTextBox.setText("0");
    }

    /**
     * Page where user can add a new customer to the database
     *
     *
     *
     *
     * @param
     * @return
     * @throws
     * @see
     */
    public void customerBackButtonClick(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
        Stage menuStage = (Stage)customerBackButton.getScene().getWindow();
        Scene menuScene = new Scene(root, 600, 400);
        menuStage.setTitle("Main Menu");
        menuStage.setScene(menuScene);
        menuStage.show();
    }

}
