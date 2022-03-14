package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

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

    public void addButtonClick(ActionEvent actionEvent) {


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
