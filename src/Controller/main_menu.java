package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The controller for the main_menu.fxml page. This page does not actually process any user data, it
 * serves as a selection screen for the user to choose where he/she wants to go. All buttons on this
 * page will take the user to a different screen.
 *
 */
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

    /**
     * Button click takes the user to the add_customer.fxml page
     *
     * @param actionEvent - the mouse click of the Add Customer Button
     * @throws IOException
     * @see Model.Customer
     */
    public void addCustomerButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/add_customer.fxml"));
        Stage addCustomerStage = (Stage)customerAddButton.getScene().getWindow();
        Scene addCustomerScene = new Scene(root, 700, 400);
        addCustomerStage.setTitle("Add New Customer");
        addCustomerStage.setScene(addCustomerScene);
        addCustomerStage.show();
    }

    /**
     * Button click takes the user to the delete_customer.fxml page
     *
     * @param actionEvent - the mouse click of the Delete Customer Button
     * @throws IOException
     * @see Model.Customer
     */
    public void deleteCustomerButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/delete_customer.fxml"));
        Stage delCustomerStage = (Stage)customerDeleteButton.getScene().getWindow();
        Scene delCustomerScene = new Scene(root, 400, 200);
        delCustomerStage.setTitle("Delete Customer");
        delCustomerStage.setScene(delCustomerScene);
        delCustomerStage.show();
    }

    /**
     * Button click takes the user to the view_customer.fxml page
     *
     * @param actionEvent - the mouse click of the View Customer Table Button
     * @throws IOException
     * @see Model.Customer
     */
    public void viewCustomerTableButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/view_customer.fxml"));
        Stage viewCustomerStage = (Stage)customerViewButton.getScene().getWindow();
        Scene viewCustomerScene = new Scene(root, 800, 500);
        viewCustomerStage.setTitle("View Customers");
        viewCustomerStage.setScene(viewCustomerScene);
        viewCustomerStage.show();
    }

    /**
     * Button click takes the user to the add_appointment.fxml page
     *
     * @param actionEvent - the mouse click of the Create New Appointment Button
     * @throws IOException
     * @see Model.Appointment
     */
    public void newApptButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/add_appointment.fxml"));
        Stage addApptStage = (Stage)appointmentNewButton.getScene().getWindow();
        Scene addApptScene = new Scene(root, 800, 600);
        addApptStage.setTitle("Add Appointment");
        addApptStage.setScene(addApptScene);
        addApptStage.show();
    }

    /**
     * Button click takes the user to the delete_appointment.fxml page
     *
     * @param actionEvent - the mouse click of the Delete Appointment Button
     * @throws IOException
     * @see Model.Appointment
     */
    public void deleteAppointmentButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/delete_appointment.fxml"));
        Stage delCustomerStage = (Stage)appointmentDeleteButton.getScene().getWindow();
        Scene delCustomerScene = new Scene(root, 400, 200);
        delCustomerStage.setTitle("Delete Appointment");
        delCustomerStage.setScene(delCustomerScene);
        delCustomerStage.show();
    }

    /**
     * Button click takes the user to the view_appointments.fxml page
     *
     * @param actionEvent - the mouse click of the View Appointments Button
     * @throws IOException
     * @see Model.Appointment
     */
    public void viewAppointmentButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/view_appointments.fxml"));
        Stage myStage = (Stage)appointmentViewButton.getScene().getWindow();
        Scene myScene = new Scene(root, 1300, 500);
        myStage.setTitle("Appointment Screen");
        myStage.setScene(myScene);
        myStage.show();
    }

    /**
     * Button click takes the user to the view_contact_schedules.fxml page
     *
     * @param actionEvent - the mouse click of the View Contact Schedules Button
     * @throws IOException
     * @see Model.Contact
     */
    public void viewContactScheduleButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/view_contact_schedules.fxml"));
        Stage menuStage = (Stage)viewContactScheduleButton.getScene().getWindow();
        Scene menuScene = new Scene(root, 1000, 500);
        menuStage.setTitle("View Contact Schedule");
        menuStage.setScene(menuScene);
        menuStage.show();
    }

    /**
     * Button click takes the user to the view_user_schedules.fxml page
     *
     * @param actionEvent - the mouse click of the View User Schedules Button
     * @throws IOException
     * @see Model.User
     */
    public void viewUserScheduleButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/view_user_schedules.fxml"));
        Stage menuStage = (Stage)viewUserScheduleButton.getScene().getWindow();
        Scene menuScene = new Scene(root, 1000, 500);
        menuStage.setTitle("View Contact Schedule");
        menuStage.setScene(menuScene);
        menuStage.show();
    }

    /**
     * Button click takes the user to the customer_appointments.fxml page
     *
     * @param actionEvent - the mouse click of the Customer Appointments Button
     * @throws IOException
     * @see Model.Contact
     */
    public void viewCustApptButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/customer_appointments.fxml"));
        Stage menuStage = (Stage)viewCustApptButton.getScene().getWindow();
        Scene menuScene = new Scene(root, 1100, 500);
        menuStage.setTitle("View Customer Schedule");
        menuStage.setScene(menuScene);
        menuStage.show();
    }

}
