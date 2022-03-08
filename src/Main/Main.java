package Main;

import Model.FruitsQuery;
import Model.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    public static void main(String[] args) throws SQLException {

        //Locale.setDefault(new Locale("fr"));   //for testing purposes.
        JDBC.openConnection();
        launch(args);

        JDBC.closeConnection();

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ResourceBundle rb = ResourceBundle.getBundle("Main/C195Bundle");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login_screen.fxml"));
        loader.setResources(rb);
        Parent root = loader.load();
        primaryStage.setTitle(rb.getString("title"));
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();
    }

    @Override
    public void init() {

    }

    @Override
    public void stop() {

    }

}
