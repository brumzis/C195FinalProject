package Controller;

import Model.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;


public class view_appointments implements Initializable {

    public TableColumn apptIDColumn;
    public TableColumn apptTitleColumn;
    public TableColumn apptDescriptionColumn;
    public TableColumn apptLocationColumn;
    public TableColumn apptContactColumn;
    public TableColumn apptTypeColumn;
    public TableColumn apptStartTimeColumn;
    public TableColumn apptEndTimeColumn;
    public TableColumn apptCustomerID;
    public TableColumn apptUserID;
    public Button scheduleNewButton;
    public Button updateApptButton;
    public Button deleteApptButton;
    public RadioButton monthRadioButton;
    public RadioButton weekRadioButton;
    public Button exitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        apptIDColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("apptID"));
        apptTitleColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("apptTitle"));
        apptDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("apptDescription"));
        apptLocationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("apptLocation"));
        apptContactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("apptContact"));
        apptTypeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("apptID"));


    }

    public void titleEditCommit(TableColumn.CellEditEvent cellEditEvent) {
    }

    public void descriptionEditCommit(TableColumn.CellEditEvent cellEditEvent) {
    }

    public void locationEditCommit(TableColumn.CellEditEvent cellEditEvent) {
    }

    public void contactEditCommit(TableColumn.CellEditEvent cellEditEvent) {
    }

    public void typeEditCommit(TableColumn.CellEditEvent cellEditEvent) {
    }

    public void startTimeEditCommit(TableColumn.CellEditEvent cellEditEvent) {
    }

    public void endTimeEditCommit(TableColumn.CellEditEvent cellEditEvent) {
    }

    public void customerIDEditCommit(TableColumn.CellEditEvent cellEditEvent) {
    }

    public void userIDEditCommit(TableColumn.CellEditEvent cellEditEvent) {
    }

    public void scheduleNewApptClick(ActionEvent actionEvent) {
    }

    public void updateApptButtonClick(ActionEvent actionEvent) {
    }

    public void deleteApptButtonClick(ActionEvent actionEvent) {
    }

    public void monthRadioButtonChecked(ActionEvent actionEvent) {
    }

    public void weekRadioButtonChecked(ActionEvent actionEvent) {
    }


    public void exitButtonClick(ActionEvent actionEvent) {
    }
}