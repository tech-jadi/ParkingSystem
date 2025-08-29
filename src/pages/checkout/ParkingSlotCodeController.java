package pages.checkout;

import java.io.IOException;
import java.util.List;

import database.DatabaseConnection;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import pages.payment.PaymentController;

/**
 * Controller class for handling parking slot code input and validation during
 * the checkout process.
 */
public class ParkingSlotCodeController {
  @FXML
  Label backLabel;
  @FXML
  TextField slotCodeTextField;

  /**
   * Validates the entered parking slot code against occupied slot codes in the
   * database. If valid, transitions to the payment page; if invalid, prompts the
   * user to re-enter the code.
   * 
   * @throws IOException if the payment FXML file cannot be loaded.
   */
  @SuppressWarnings("unused")
  @FXML
  public void confirmSlotCode() throws IOException {
    String inputSlotCode = slotCodeTextField.getText().toUpperCase();
    List<String> occupiedSlotCodes = DatabaseConnection.getOccupiedSlotCodes();

    // Check if the entered slot code is already occupied. If so, proceed to
    // payment.
    if (occupiedSlotCodes.contains(inputSlotCode)) {
      Stage stage = (Stage) backLabel.getScene().getWindow();
      FXMLLoader loader = new FXMLLoader(getClass().getResource("../payment/payment.fxml"));
      Parent root = loader.load();

      // Pass the slot code to the payment controller
      PaymentController controller = loader.getController();
      controller.start(inputSlotCode);

      Scene scene = new Scene(root);
      stage.setScene(scene);
    } else {
      // Invalid slot code entered. Prompt user to re-enter. Clear the text field and
      // change prompt text color to red.
      slotCodeTextField.clear();
      slotCodeTextField.setPromptText("Invalid Code");
      slotCodeTextField.setStyle(("-fx-prompt-text-fill: #FF0000;"));

      // Revert changes after 3 seconds
      PauseTransition timeout = new PauseTransition(Duration.seconds(3));
      timeout.setOnFinished(e -> {
        slotCodeTextField.setPromptText("Slot Code");
        slotCodeTextField.setStyle(("-fx-prompt-text-fill: #A9A9A9;"));
      });
      timeout.play();
    }
  }

  /**
   * Switches the scene back to the starting page.
   * 
   * @throws IOException if the starting FXML file cannot be loaded.
   */
  @FXML
  public void switchToStart() throws IOException {
    Stage stage = (Stage) backLabel.getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("../starting/start.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
  }
}
