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


    public void initialize() throws SQLException {

        contactComboBox.setItems(JDBC.getContactObjects());

        contactApptID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("apptID"));
        contactTitle.setCellValueFactory(new PropertyValueFactory<Appointment, String>("apptTitle"));
        contactType.setCellValueFactory(new PropertyValueFactory<Appointment, String> ("apptType"));
        contactDescription.setCellValueFactory(new PropertyValueFactory<Appointment, String>("apptDescription"));
        contactStart.setCellValueFactory(new PropertyValueFactory<Appointment, DateTimeUtility>("apptStart"));
        contactEnd.setCellValueFactory(new PropertyValueFactory<Appointment, DateTimeUtility>("apptEnd"));
        contactCustomerID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("apptCustomerID"));


    }
    public void contactComboBoxSelected(ActionEvent actionEvent) throws SQLException {

        Contact c = contactComboBox.getSelectionModel().getSelectedItem();
        contactTable.setItems(JDBC.createContactSchedule(c.getContactID()));

    }

    public void contactBackButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
        Stage menuStage = (Stage)contactBackButton.getScene().getWindow();
        Scene menuScene = new Scene(root, 600, 400);
        menuStage.setTitle("Main Menu");
        menuStage.setScene(menuScene);
        menuStage.show();
    }

}
