package Model;

import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * The alertBoxInterface is a functional interface that allows for the use of lambda expressions.
 * I used lambda expressions to write code to display the various alertboxes for the appointment
 * scheduling program.
 *
 */
public interface alertBoxInterface
{
    Optional<ButtonType> displayAlertBox();
}
