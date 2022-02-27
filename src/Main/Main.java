package Main;

import Model.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {

    public static void main(String[] args) throws SQLException {


        //Locale.setDefault(new Locale("fr"));   //for testing purposes.
        JDBC.openConnection();
        launch(args);



        JDBC.closeConnection();

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/login_screen.fxml"));
        primaryStage.setTitle("Login Screen");
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();
    }

    @Override
    public void init() {
        System.out.println("starting up - called from init in main, in the new file");
    }

    @Override
    public void stop() {
        System.out.println("called from the stop in main");
    }

}
