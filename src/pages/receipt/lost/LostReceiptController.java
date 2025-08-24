package pages.receipt.lost;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import database.DatabaseConnection;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LostReceiptController {
  @FXML
  Label dateTimeLabel;
  @FXML
  Label vehicleTypeLabel;

  @SuppressWarnings("unused")
  @FXML
  public void start(String slotCode, String vehicleType) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy hh:mm:ss a");
    String formattedDateTime = LocalDateTime.now().format(formatter);

    dateTimeLabel.setText(formattedDateTime);
    vehicleTypeLabel.setText(vehicleType);

    // Update database to set the slot as unoccupied
    DatabaseConnection.setUnoccupied(slotCode);

    PauseTransition interval = new PauseTransition(Duration.seconds(5));
    interval.setOnFinished(e -> {
      try {
        Stage stage = (Stage) dateTimeLabel.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../../starting/start.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    });
    interval.play();
  }

}
