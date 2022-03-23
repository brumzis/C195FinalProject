package Controller;

import Model.JDBC;
import Model.alertBoxInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Controller for the delete_customer.fxml page. Only two buttons populate this window,
 * a cancel button and a delete button. This controller handles both button events. This
 *
 */
public class delete_customer {

    public TextField deleteCustomerIDTbox;
    public Button deleteCustomerButton;
    public Button deleteCustomerCancelButton;


    public void initialize() {}

    /**
     * The Delete Button click first takes the user entry from the textfield and stores it to
     * an integer value. In the event a non-integer is entered by the user, an exception will
     * be thrown and the user will be alerted via a text box. If an integer value is entered, the
     * user will be prompted if they are sure they want to delete the customer, once confirmed
     * the entry will be used in an SQL Query to delete the selected row from the database.
     * Another alertbox is used to let the user know if the deletion was successful. If the
     * customer ID was not found, the user will be alerted that the deletion was not successful.
     * Per the instructions, the customer cannot be deleted if he/she has any scheduled appointments,
     * so appointments must be checked before deletion is attemtped.
     *
     * @param actionEvent - the click of the Delete Button.
     * @throws SQLException
     */
    public void deleteButtonClick(ActionEvent actionEvent) throws SQLException {
        try {
            int customerID = Integer.parseInt(deleteCustomerIDTbox.getText());      //get the user entered customerID from the textfield
            alertBoxInterface alert = () -> { Alert myAlert = new Alert(Alert.AlertType.CONFIRMATION);
                                              myAlert.setTitle("Delete Customer");
                                              myAlert.setHeaderText("Confirm Delete?");
                                              myAlert.setContentText("All customer appointments must be deleted before deleting customer!" +
                                                                     " Do you wish to continue?");
                                              return myAlert.showAndWait();
                                            };
            Optional<ButtonType> result = alert.displayAlertBox();

            if (result.isPresent() && result.get() == ButtonType.OK) {       //if customerID is valid, check for existing appointments
                if (JDBC.checkForCustomerAppointments(customerID))
                    throw new IndexOutOfBoundsException("can't have any appointments");  //throw exception with alertbox if appointments are found

                String sql = "DELETE FROM customers WHERE Customer_ID = ?";
                PreparedStatement ps = JDBC.connection.prepareStatement(sql);
                ps.setInt(1, customerID);
                int rowsAffected = ps.executeUpdate();          //if deletion was successful, rowsAffected > 0, alert user in either case.

                if (rowsAffected > 0) {
                    alertBoxInterface alert1 = () -> { Alert myAlert = new Alert(Alert.AlertType.CONFIRMATION);
                                                       myAlert.setTitle("Confirmation");
                                                       myAlert.setHeaderText("Success!");
                                                       myAlert.setContentText("Customer has been removed from DB");
                                                       return myAlert.showAndWait();
                                                     };
                    alert1.displayAlertBox();
                }
                else {
                    alertBoxInterface alert2 = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);
                                                       myAlert.setTitle("Error");
                                                       myAlert.setHeaderText("Customer lookup error");
                                                       myAlert.setContentText("Customer ID not found");
                                                       return myAlert.showAndWait();
                                                     };
                    alert2.displayAlertBox();
                }
            }

        } catch (IndexOutOfBoundsException e) {
            alertBoxInterface alert3 = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);
                                               myAlert.setTitle("Deletion Error");
                                               myAlert.setHeaderText("Customer appointments found");
                                               myAlert.setContentText("Customer cannot have any appointments. Delete customer appointments first");
                                               return myAlert.showAndWait();
                                             };
            alert3.displayAlertBox();

        } catch(NumberFormatException e) {
            alertBoxInterface alert4 = () -> { Alert myAlert = new Alert(Alert.AlertType.ERROR);
                                               myAlert.setTitle("Deletion Error");
                                               myAlert.setHeaderText("Invalid Entry");
                                               myAlert.setContentText("Please enter a valid Customer ID");
                                               return myAlert.showAndWait();
                                             };
            alert4.displayAlertBox();
        }
    }

    /**
     * Upon a click of the cancel button the user will return to the main menu.
     *
     * @param actionEvent - a mouse click on the cancel button
     * @throws IOException
     */
    public void cancelButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
        Stage menuStage = (Stage)deleteCustomerCancelButton.getScene().getWindow();
        Scene menuScene = new Scene(root, 600, 400);
        menuStage.setTitle("Main Menu");
        menuStage.setScene(menuScene);
        menuStage.show();
    }
}
