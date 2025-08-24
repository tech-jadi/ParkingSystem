package pages.ticket;

import java.io.IOException;
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
import model.Slot;

public class ParkingTicketController {
  @FXML
  Label vehicleTypeLabel;
  @FXML
  Label parkingSlotLabel;
  @FXML
  Label entryTimeLabel;
  @FXML
  Label dateLabel;

  @SuppressWarnings("unused")
  @FXML
  public void initialize() {
    Slot latestSlot = DatabaseConnection.getLatestEntrySlot();
    assert latestSlot != null : "No latest slot found. Ensure a slot is occupied before generating a ticket.";

    // Concatenate values for the labels
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    String formattedDate = latestSlot.startingTime().toLocalDate().format(formatter);
    dateLabel.setText(formattedDate);

    vehicleTypeLabel.setText(latestSlot.vehicleType());
    parkingSlotLabel.setText(latestSlot.slotCode());

    formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
    String formattedTime = latestSlot.startingTime().toLocalTime().format(formatter);
    entryTimeLabel.setText(formattedTime);

    // 5 seconds wait (mimicking printing time) before closing the ticket window
    PauseTransition timeout = new PauseTransition(Duration.seconds(5));
    timeout.setOnFinished(event -> {
      try {
        Stage stage = (Stage) vehicleTypeLabel.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../starting/start.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    timeout.play();
  }
}