package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class main_menu {
    public Button customerAddButton;
    public Button customerDeleteButton;
    public Button appointmentNewButton;
    public Button appointmentDeleteButton;
    public Button customerViewButton;
    public Button appointmentViewButton;
    public Button viewContactScheduleButton;
    public Button viewUserScheduleButton;
    public Button viewCustApptButton;
    public Button userLoginTrackingButton;

    public void addCustomerButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/add_customer.fxml"));
        Stage addCustomerStage = (Stage)customerAddButton.getScene().getWindow();
        Scene addCustomerScene = new Scene(root, 700, 400);
        addCustomerStage.setTitle("Add New Customer");
        addCustomerStage.setScene(addCustomerScene);
        addCustomerStage.show();
    }


    public void deleteCustomerButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/delete_customer.fxml"));
        Stage delCustomerStage = (Stage)customerDeleteButton.getScene().getWindow();
        Scene delCustomerScene = new Scene(root, 400, 200);
        delCustomerStage.setTitle("Delete Customer");
        delCustomerStage.setScene(delCustomerScene);
        delCustomerStage.show();
    }


    public void viewCustomerTableButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/view_customer.fxml"));
        Stage viewCustomerStage = (Stage)customerViewButton.getScene().getWindow();
        Scene viewCustomerScene = new Scene(root, 800, 500);
        viewCustomerStage.setTitle("View Customers");
        viewCustomerStage.setScene(viewCustomerScene);
        viewCustomerStage.show();
    }

    public void newApptButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/add_appointment.fxml"));
        Stage addApptStage = (Stage)appointmentNewButton.getScene().getWindow();
        Scene addApptScene = new Scene(root, 800, 600);
        addApptStage.setTitle("Add Appointment");
        addApptStage.setScene(addApptScene);
        addApptStage.show();
    }

    public void deleteAppointmentButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/delete_appointment.fxml"));
        Stage delCustomerStage = (Stage)appointmentDeleteButton.getScene().getWindow();
        Scene delCustomerScene = new Scene(root, 400, 200);
        delCustomerStage.setTitle("Delete Appointment");
        delCustomerStage.setScene(delCustomerScene);
        delCustomerStage.show();
    }

    public void viewAppointmentButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/view_appointments.fxml"));
        Stage myStage = (Stage)appointmentViewButton.getScene().getWindow();
        Scene myScene = new Scene(root, 1300, 500);
        myStage.setTitle("Appointment Screen");
        myStage.setScene(myScene);
        myStage.show();
    }

    public void viewContactScheduleButtonClick(ActionEvent actionEvent) {
    }

    public void viewUserScheduleButtonClick(ActionEvent actionEvent) {
    }

    public void viewCustApptButtonClick(ActionEvent actionEvent) {
    }

    public void loginTrackingButtonClick(ActionEvent actionEvent) {
    }
}
