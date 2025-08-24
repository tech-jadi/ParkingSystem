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

public class ParkingSlotCodeController {
  @FXML
  Label backLabel;
  @FXML
  TextField slotCodeTextField;

  @SuppressWarnings("unused")
  @FXML
  public void confirmSlotCode() throws IOException {
    String inputSlotCode = slotCodeTextField.getText().toUpperCase();
    List<String> occupiedSlotCodes = DatabaseConnection.getOccupiedSlotCodes();

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

  @FXML
  public void switchToStart() throws IOException {
    Stage stage = (Stage) backLabel.getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("../starting/start.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
  }
}
