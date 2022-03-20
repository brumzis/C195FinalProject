package Controller;

import Model.JDBC;
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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Customer?");
            alert.setContentText("All customer appointments must be deleted before deleting customer! " + "Do you wish to continue?");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (JDBC.checkForCustomerAppointments(customerID))
                    throw new IndexOutOfBoundsException("can't have any appointments");

                String sql = "DELETE FROM customers WHERE Customer_ID = ?";
                PreparedStatement ps = JDBC.connection.prepareStatement(sql);
                ps.setInt(1, customerID);
                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println(rowsAffected + " row deleted from table");
                    Alert alertbox = new Alert(Alert.AlertType.CONFIRMATION);
                    alertbox.setTitle("Confirmation");
                    alertbox.setHeaderText("Success!");
                    alertbox.setContentText("Customer has been removed from DB");
                    alertbox.showAndWait();
                }
                else {
                    Alert message = new Alert(Alert.AlertType.ERROR);
                    message.setTitle("Error");
                    message.setHeaderText("Customer lookup error");
                    message.setContentText("Customer ID not found");
                    message.showAndWait();
                }
            }

        } catch (IndexOutOfBoundsException e) {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Deletion Error:");
            alert2.setHeaderText("Customer cannot have any appointments. Delete customer appointments first");
            alert2.showAndWait();

        } catch(NumberFormatException e) {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Deletion Error:");
            alert2.setHeaderText("Please enter a valid Customer ID");
            alert2.showAndWait();
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
