package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class login_screen implements Initializable {
    public Button loginButton;
    public Label loginLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("called from initialize on login screen load page");
    }

    public void onLoginButtonClick(ActionEvent actionEvent) {
        loginLabel.setText("button has been clicked!");
    }
}
