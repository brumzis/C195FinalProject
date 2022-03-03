package Controller;

import Model.JDBC;
import com.sun.scenario.effect.impl.sw.java.JSWBlend_SRC_OUTPeer;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.w3c.dom.ls.LSOutput;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class login_screen implements Initializable {
    public Button loginButton;
    public Label loginLabel;
    public TextField userIDTextBox;
    public TextField passwordTextBox;
    public Label locationLabel;
    public Label userIdLabel;
    public Label passwordLabel;
    public Label screenTitle;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("called from initialize on login screen load page - from new screen");
        System.out.println(Locale.getDefault());
        userIDTextBox.setPromptText(rb.getString("userid2"));
        passwordTextBox.setPromptText(rb.getString("pwd2"));
        loginLabel.setText(String.valueOf(ZoneId.systemDefault()));
        loginButton.setText(rb.getString("button"));
        loginLabel.setText(String.valueOf(ZoneId.systemDefault()));
        passwordLabel.setText(rb.getString("pwd"));
        screenTitle.setText(rb.getString("title"));
        locationLabel.setText(rb.getString("loc"));
        userIdLabel.setText(rb.getString("userid1"));





    }

    public void onLoginButtonClick(ActionEvent actionEvent) throws SQLException {
        String userNameInput = userIDTextBox.getText();
        String passwordInput = passwordTextBox.getText();
        if(getUserID(userNameInput) == 0)
            System.out.println("invalid username");
        else
            System.out.println("username accepted");



    }



    private int getUserID(String inputName) throws SQLException {
        int userID = 0;
        String sql = "SELECT User_ID FROM users WHERE User_Name = '" + inputName + "'";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            userID = rs.getInt("User_ID");
        }
        return userID;
    }



}
