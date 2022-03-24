package Controller;

import Model.Appointment;
import Model.Contact;
import Model.DateTimeUtility;
import Model.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;


/**
 * Controller for the view_contact_schedules.fxml page. This page adjusts the tableview depending
 * on which contact is selected by the user in the 'Select Contact' combobox. The controller also
 * handles a 'Back Button' as well. This table is not editable.
 *
 */
public class view_contact_schedules {
    public ComboBox<Contact> contactComboBox;
    public Button contactBackButton;
    public TableColumn contactApptID;
    public TableColumn contactTitle;
    public TableColumn contactType;
    public TableColumn contactDescription;
    public TableColumn contactStart;
    public TableColumn contactEnd;
    public TableColumn contactCustomerID;
    public TableView contactTable;

    /**
     * Page is initialized with a tableview. The tableview starts off empty, as no contact is yet
     * selected from the combobox. The combobox is loaded with a list of all available contacts
     * listed in the database.
     *
     * @throws SQLException
     */
    public void initialize() throws SQLException {

        contactComboBox.setItems(JDBC.getContactObjects());     // load combobox with all available contacts

        contactApptID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("apptID"));
        contactTitle.setCellValueFactory(new PropertyValueFactory<Appointment, String>("apptTitle"));
        contactType.setCellValueFactory(new PropertyValueFactory<Appointment, String> ("apptType"));
        contactDescription.setCellValueFactory(new PropertyValueFactory<Appointment, String>("apptDescription"));
        contactStart.setCellValueFactory(new PropertyValueFactory<Appointment, DateTimeUtility>("apptStart"));
        contactEnd.setCellValueFactory(new PropertyValueFactory<Appointment, DateTimeUtility>("apptEnd"));
        contactCustomerID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("apptCustomerID"));
    }


    /**
     * This action takes the Contact Object selected by the user, and queries the database for all
     * appointments matching that contact ID. A list of all appointments matching the selected contact
     * is then displayed in the tableview
     *
     * @param actionEvent - a selection/or change of the combobox
     * @throws SQLException
     * @see Contact
     */
    public void contactComboBoxSelected(ActionEvent actionEvent) throws SQLException {

        Contact c = contactComboBox.getSelectionModel().getSelectedItem();
        contactTable.setItems(JDBC.createContactSchedule(c.getContactID()));

    }


    /**
     * Takes the user back to the main menu.
     *
     * @param actionEvent - a mouse click on the 'Back Button'
     * @throws IOException
     */
    public void contactBackButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
        Stage menuStage = (Stage)contactBackButton.getScene().getWindow();
        Scene menuScene = new Scene(root, 600, 400);
        menuStage.setTitle("Main Menu");
        menuStage.setScene(menuScene);
        menuStage.show();
    }

}
