package Controller;

import Model.Appointment;
import Model.DateTimeUtility;
import Model.JDBC;
import Model.User;
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

    public void comboBoxSelected(ActionEvent actionEvent) throws SQLException {

        User u = userComboBox.getSelectionModel().getSelectedItem();
        userTable.setItems(JDBC.createContactSchedule(u.getUserID()));

    }

    public void backButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
        Stage menuStage = (Stage)userBackButton.getScene().getWindow();
        Scene menuScene = new Scene(root, 600, 400);
        menuStage.setTitle("Main Menu");
        menuStage.setScene(menuScene);
        menuStage.show();
    }
}
