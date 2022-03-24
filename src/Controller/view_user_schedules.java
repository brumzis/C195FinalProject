package Controller;

import Model.*;
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
 * Controller for the view_user_schedules.fxml page. This page adjusts the tableview depending
 * on which user is selected by the user in the 'Select User' combobox. The controller also
 * handles a 'Back Button' as well. This table is not editable.
 *
 */
public class view_user_schedules {

    public ComboBox<User> userComboBox;
    public Button userBackButton;
    public TableView userTable;
    public TableColumn userApptID;
    public TableColumn userTitle;
    public TableColumn userType;
    public TableColumn userDescription;
    public TableColumn userStart;
    public TableColumn userEnd;
    public TableColumn userCustomerID;


    /**
     * Page is initialized with a tableview. The tableview starts off empty, as no User is yet
     * selected from the combobox. The combobox is loaded with a list of all available users
     * listed in the database.
     *
     * @throws SQLException
     */
    public void initialize() throws SQLException {

        userComboBox.setItems(JDBC.getUserObjects());

        userApptID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("apptID"));
        userTitle.setCellValueFactory(new PropertyValueFactory<Appointment, String>("apptTitle"));
        userType.setCellValueFactory(new PropertyValueFactory<Appointment, String> ("apptType"));
        userDescription.setCellValueFactory(new PropertyValueFactory<Appointment, String>("apptDescription"));
        userStart.setCellValueFactory(new PropertyValueFactory<Appointment, DateTimeUtility>("apptStart"));
        userEnd.setCellValueFactory(new PropertyValueFactory<Appointment, DateTimeUtility>("apptEnd"));
        userCustomerID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("apptCustomerID"));

    }


    /**
     * This action takes the User Object selected by the user, and queries the database for all
     * appointments matching that user ID. A list of all appointments matching the selected user
     * is then displayed in the tableview
     *
     * @param actionEvent - a selection/or change of the combobox
     * @throws SQLException
     * @see User
     */
    public void comboBoxSelected(ActionEvent actionEvent) throws SQLException {

        User u = userComboBox.getSelectionModel().getSelectedItem();
        userTable.setItems(JDBC.createContactSchedule(u.getUserID()));

    }


    /**
     * Takes the user back to the main menu.
     *
     * @param actionEvent - a mouse click on the 'Back Button'
     * @throws IOException
     */
    public void backButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
        Stage menuStage = (Stage)userBackButton.getScene().getWindow();
        Scene menuScene = new Scene(root, 600, 400);
        menuStage.setTitle("Main Menu");
        menuStage.setScene(menuScene);
        menuStage.show();
    }
}
