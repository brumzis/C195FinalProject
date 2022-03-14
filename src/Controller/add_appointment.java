package Controller;

import Model.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class add_appointment {

    public DatePicker startDateBox;
    public ComboBox startHourComboBox;
    public ComboBox startMinComboBox;
    public ComboBox startAMComboBox;
    public DatePicker endDateBox;
    public ComboBox endHourComboBox;
    public ComboBox endMinComboBox;
    public ComboBox endAMComboBox;
    public TextField customerIDTbox;
    public TextField userIDTbox;
    public Button addNewButton;
    public Button cancelButton;
    public TextField apptIDTbox;
    public TextField titleTbox;
    public TextField descriptionTbox;
    public TextField locationTbox;
    public ComboBox contactComboBox;
    public TextField typeTbox;


    public void initialize() throws SQLException{

        contactComboBox.setItems(JDBC.getContacts());
        startHourComboBox.setItems(JDBC.getHours());
        endHourComboBox.setItems(JDBC.getHours());
        startMinComboBox.setItems(JDBC.getMinutes());
        endMinComboBox.setItems(JDBC.getMinutes());
        startAMComboBox.setItems(JDBC.getAMPM());
        endAMComboBox.setItems(JDBC.getAMPM());


    }

    public void addButtonClick(ActionEvent actionEvent) {
        try {
            LocalDate startDay = startDateBox.getValue();
            LocalDate endDay = endDateBox.getValue();
            LocalTime startTime = LocalTime.of((Integer)startHourComboBox.getSelectionModel().getSelectedItem(), (Integer)startMinComboBox.getSelectionModel().getSelectedItem());
            LocalTime endTime = LocalTime.of((Integer)endHourComboBox.getSelectionModel().getSelectedItem(), (Integer)endMinComboBox.getSelectionModel().getSelectedItem());
            LocalDateTime startApptTime = LocalDateTime.of(startDay, startTime);
            LocalDateTime endApptTime = LocalDateTime.of(endDay, endTime);
            String sql = "INSERT INTO appointments (Title, Description, Location, Type, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";  //excluded primary key column
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, titleTbox.getText());
            ps.setString(2, descriptionTbox.getText());
            ps.setString(3, locationTbox.getText());
            ps.setString(4, typeTbox.getText());
            ps.setInt(5, Integer.parseInt(customerIDTbox.getText()));
            ps.setInt(6, Integer.parseInt(userIDTbox.getText()));
            ps.setInt(7, JDBC.returnContactID(contactComboBox.getSelectionModel().getSelectedItem().toString()));
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Addition Successful");
                alert2.setHeaderText("Appointment added to DB!");
                alert2.showAndWait();
                Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
                Stage menuStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene menuScene = new Scene(root, 600, 400);
                menuStage.setTitle("Main Menu");
                menuStage.setScene(menuScene);
                menuStage.show();
            }
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Data Entry Error");
            alert.setHeaderText("Data Entry Error");
            alert.setContentText("Please make sure all fields are filled in with correct data types");
            alert.showAndWait();
            System.out.println(e.getMessage());
        }


    }

    public void cancelButtonClick(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main_menu.fxml"));
        Stage menuStage = (Stage)cancelButton.getScene().getWindow();
        Scene menuScene = new Scene(root, 600, 400);
        menuStage.setTitle("Main Menu");
        menuStage.setScene(menuScene);
        menuStage.show();
    }


}
