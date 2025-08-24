package pages.receipt.normal;

import java.io.IOException;
import java.time.LocalDate;
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
import model.Customer;

public class ReceiptController {
  @FXML
  Label vehicleTypeLabel;
  @FXML
  Label discountTypeLabel;
  @FXML
  Label parkingSlotLabel;
  @FXML
  Label entryTimeLabel;
  @FXML
  Label exitTimeLabel;
  @FXML
  Label subtotalLabel;
  @FXML
  Label discountLabel;
  @FXML
  Label totalLabel;
  @FXML
  Label cashLabel;
  @FXML
  Label changeLabel;
  @FXML
  Label dateLabel;

  @SuppressWarnings("unused")
  @FXML
  public void start(Customer customer) {
    assert customer != null : "Customer should not be null";

    // Formate DateTime
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
    String formmatedEntryTime = customer.entryTime().format(formatter);
    String formmatedExitTime = customer.exitTime().format(formatter);

    vehicleTypeLabel.setText(customer.vehicleType());
    discountTypeLabel.setText(customer.discountType());
    parkingSlotLabel.setText(customer.slotCode());
    entryTimeLabel.setText(formmatedEntryTime);
    exitTimeLabel.setText(formmatedExitTime);

    subtotalLabel.setText(String.format("₱%.2f", customer.subtotal()));
    discountLabel.setText(String.format("₱%.2f", customer.discountAmount()));
    totalLabel.setText(String.format("₱%.2f", customer.subtotal() - customer.discountAmount()));
    cashLabel.setText(String.format("₱%.2f", customer.cash()));
    changeLabel.setText(String.format("₱%.2f", customer.cash() - (customer.subtotal() - customer.discountAmount())));

    // Format date
    formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
    String formattedDate = LocalDate.now().format(formatter);
    dateLabel.setText(formattedDate);

    // Update database to set the slot as unoccupied
    DatabaseConnection.setUnoccupied(customer.slotCode());

    // After 5 seconds, go back to starting page
    PauseTransition interval = new PauseTransition(Duration.seconds(5));
    interval.setOnFinished(e -> {
      try {
        Stage stage = (Stage) vehicleTypeLabel.getScene().getWindow();
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
