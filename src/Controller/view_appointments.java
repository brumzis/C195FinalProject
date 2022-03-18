package Controller;

import Model.Appointment;
import Model.Customer;
import Model.DateTimeUtility;
import Model.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class view_appointments implements Initializable {

    public TableView<Appointment> appointmentTable;
    public TableColumn<Appointment, Integer> apptIDColumn;
    public TableColumn<Appointment, String> apptTitleColumn;
    public TableColumn<Appointment, String> apptDescriptionColumn;
    public TableColumn<Appointment, String> apptLocationColumn;
    public TableColumn<Appointment, Integer> apptContactColumn;
    public TableColumn<Appointment, String> apptTypeColumn;
    public TableColumn<Appointment, DateTimeUtility> apptStartTimeColumn;
    public TableColumn<Appointment, DateTimeUtility> apptEndTimeColumn;
    public TableColumn<Appointment, Integer> apptCustomerID;
    public TableColumn<Appointment, Integer> apptUserID;
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
        apptContactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("apptContact"));
        apptTypeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("apptType"));
        apptStartTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, DateTimeUtility>("apptStart"));
        apptEndTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, DateTimeUtility>("apptEnd"));
        apptCustomerID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("apptCustomerID"));
        apptUserID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("apptUserID"));

        try {
            appointmentTable.setItems(JDBC.createAppointmentList());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    public void scheduleNewApptClick(ActionEvent actionEvent) {
    }

    public void updateApptButtonClick(ActionEvent actionEvent) throws IOException, SQLException {
        if (appointmentTable.getSelectionModel().getSelectedItem() == null) {
            Alert noSelection = new Alert(Alert.AlertType.ERROR);
            noSelection.setTitle("Error");
            noSelection.setHeaderText("No appointment selected");
            noSelection.showAndWait();
        }
        else {
            Appointment a = appointmentTable.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/update_appointment.fxml"));
            Parent root = loader.load();
            update_appointment controller = loader.getController();
            controller.getSelectedAppointment(a);                         //passing appointment object to update_appointment.java
            Stage myStage = (Stage) updateApptButton.getScene().getWindow();
            Scene myScene = new Scene(root, 800, 600);
            myStage.setTitle("Edit Appointment");
            myStage.setScene(myScene);
            myStage.show();
        }
    }

    public void deleteApptButtonClick(ActionEvent actionEvent) {
    }

    public void monthRadioButtonChecked(ActionEvent actionEvent) {
    }

    public void weekRadioButtonChecked(ActionEvent actionEvent) {
    }


    public void exitButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
        Stage menuStage = (Stage)exitButton.getScene().getWindow();
        Scene menuScene = new Scene(root, 600, 400);
        menuStage.setTitle("Main Menu");
        menuStage.setScene(menuScene);
        menuStage.show();
    }
}