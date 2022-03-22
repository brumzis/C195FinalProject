package Main;

import Model.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.ResourceBundle;


/**
 * Application class from which JAVAFX applications extend
 */
public class Main extends Application {

    /**
     * main() method to start the program and launch Javafx.
     *
     * Before launching the program - I first establish a connection
     * to the SQL Database. When the program is completed, the
     * connection gets closed.
     *
     * @author Brandon Rumzis
     * @param args unused
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {

        //Locale.setDefault(new Locale("fr"));   //for testing purposes.
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();

    }

    /**
     * start() method loads first stage which is the login screen.
     *
     * The login screen is the opening page of the program. I used a ResourceBundle to allow
     * for the display of the French language. (provided the user's system settings are in French).
     * The French language functionality only applies to the login screen.
     *
     * @author Brandon Rumzis
     * @throws Exception An incorrect file name/location will cause an exception.
     */
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

}
