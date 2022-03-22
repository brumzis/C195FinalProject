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
 * Controller for the customer_appointments.fxml page. Handles 'Go' Buttons for three different
 * appointment filters, handles a 'back' button, and also contains an initialize method. The table on this
 * screen is not editable, so no handling is needed there.
 *
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
     * Initialize loads the customer combobox with all of the available customers in the database. It also
     * loads the month combo box with every calendar month. The tableview stays empty upon initialization, as
     * no customer, month, or type is yet selected.
     *
     * @throws SQLException
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
     * clicking the go button will use the selection from the customer combobox to populate the table
     * with all customer appointments matching the selected customer. Clicking the go button also clears
     * any remaining or old data from the other boxes or fields - this ensures the table data matches up
     * with what the user has selected. The total box also keeps a count of how many rows were returned
     * by the query, and displays those results.
     *
     * @param actionEvent - a mouse click of the go button
     * @throws SQLException
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
     * clicking the go button will use the selection from the month combobox to populate the table
     * with all customer appointments matching the selected month. Clicking the go button also clears
     * any remaining or old data from the other boxes or fields - this ensures the table data matches up
     * with what the user has selected. The total box also keeps a count of how many rows were returned
     * by the query, and displays those results.
     *
     * @param actionEvent - mouse click on the month 'Go' button
     * @throws SQLException
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
     * clicking the go button will use the string in the textfield entered by the user to populate the table
     * with all customer appointments matching the string in the textfield. Clicking the go button also clears
     * any remaining or old data from the other boxes or fields - this ensures the table data matches up
     * with what the user has selected. The total box also keeps a count of how many rows were returned
     * by the query, and displays those results.
     *
     * @param actionEvent - a mouse click on the type 'Go' button
     * @throws SQLException
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
     * Upon the click of the back button, the user will return to the main menu
     *
     * @param actionEvent - takes the user back to the main menu
     * @throws IOException
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
