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

public class delete_customer {

    public TextField deleteCustomerIDTbox;
    public Button deleteCustomerButton;
    public Button deleteCustomerCancelButton;


    public void initialize() {}


    public void deleteButtonClick(ActionEvent actionEvent) throws SQLException {
        try {
            int customerID = Integer.parseInt(deleteCustomerIDTbox.getText());
            alertBoxInterface alert = () -> { Alert myAlert = new Alert(Alert.AlertType.CONFIRMATION);
                                              myAlert.setTitle("Delete Customer");
                                              myAlert.setHeaderText("Confirm Delete?");
                                              myAlert.setContentText("All customer appointments must be deleted before deleting customer!" +
                                                                     " Do you wish to continue?");
                                              return myAlert.showAndWait();
                                            };
            Optional<ButtonType> result = alert.displayAlertBox();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (JDBC.checkForCustomerAppointments(customerID))
                    throw new IndexOutOfBoundsException("can't have any appointments");

                String sql = "DELETE FROM customers WHERE Customer_ID = ?";
                PreparedStatement ps = JDBC.connection.prepareStatement(sql);
                ps.setInt(1, customerID);
                int rowsAffected = ps.executeUpdate();

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


    public void cancelButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
        Stage menuStage = (Stage)deleteCustomerCancelButton.getScene().getWindow();
        Scene menuScene = new Scene(root, 600, 400);
        menuStage.setTitle("Main Menu");
        menuStage.setScene(menuScene);
        menuStage.show();
    }
}
