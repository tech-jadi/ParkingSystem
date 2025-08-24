package pages.payment;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import database.DatabaseConnection;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;
import model.Slot;
import pages.receipt.lost.LostReceiptController;
import pages.receipt.normal.ReceiptController;

public class PaymentController {
  @FXML
  Label vehicleTypeLabel;
  @FXML
  Label entryTimeLabel;
  @FXML
  Label exitTimeLabel;
  @FXML
  Label additionalChargeLabel;
  @FXML
  Label totalLabel;
  @FXML
  TextField cashInputField;

  private Slot selectedSlot = null;
  private String vehicleType = null;
  private double subtotal = 0d;
  private double discountedAmount = 0d;
  private String discountType = "Regular";
  private LocalDateTime startingTime = null;
  private LocalDateTime exitTime = null;

  private boolean isDiscounted = false;

  @FXML
  public void start(String slotCode) {
    selectedSlot = DatabaseConnection.getSlotByCode(slotCode);
    assert selectedSlot != null : "Selected slot should not be null";

    // Set exit time as soon as the page is loaded
    startingTime = selectedSlot.startingTime();
    exitTime = LocalDateTime.now();

    // Parsed details for vehicle type for later processing
    vehicleType = selectedSlot.vehicleType();

    // Format datetime
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy hh:mm:ss a");
    String formattedStartingTime = startingTime.format(formatter);
    String formattedExitTime = exitTime.format(formatter);

    // Additional charge calculation logic here
    long duration = Duration.between(startingTime, exitTime).toMinutes();
    double additionalCharge = 0d;

    if (vehicleType.equals("2-Wheeler")) {
      subtotal = 20d;
    } else {
      subtotal = 40d;
    }

    if (vehicleType.equals("2-Wheeler") && duration > 120) {
      additionalCharge = (duration - 120) * (1 / 6d);
    } else if (vehicleType.equals("4-Wheeler") && duration > 120) {
      additionalCharge = (duration - 120) * (1 / 3d);
    }

    subtotal += additionalCharge;

    // Display details
    vehicleTypeLabel.setText(vehicleTypeLabel.getText() + " " + vehicleType);
    entryTimeLabel.setText(entryTimeLabel.getText() + " " + formattedStartingTime);
    exitTimeLabel.setText(exitTimeLabel.getText() + " " + formattedExitTime);
    additionalChargeLabel.setText(additionalChargeLabel.getText() + " ₱" + String.format("%.2f", additionalCharge));
    totalLabel.setText(totalLabel.getText() + " ₱" + String.format("%.2f", (subtotal)));
  }

  @SuppressWarnings("unused")
  @FXML
  public void confirmPayment() throws IOException {
    double totalAmount = Double.parseDouble(totalLabel.getText().split("₱")[1]);

    try {
      double customerCash = Double.parseDouble(cashInputField.getText());

      // Validate if inputted cash negative or non-numbers
      if (customerCash < 0) {
        notifyCustomerCashError("Invalid Amount");

        PauseTransition timeout = new PauseTransition(javafx.util.Duration.seconds(3));
        timeout.setOnFinished(e -> {
          revertToDefaultCashInput();
        });
        timeout.play();
        return;
      }

      if (customerCash >= totalAmount) { // Proceed to receipt
        Stage stage = (Stage) cashInputField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../receipt/normal/receipt.fxml"));
        Parent root = loader.load();

        ReceiptController controller = loader.getController();
        controller.start(new Customer(vehicleType, discountType, selectedSlot.slotCode(), startingTime,
            exitTime, subtotal, discountedAmount, customerCash));

        Scene scene = new Scene(root);
        stage.setScene(scene);
      } else if (customerCash < totalAmount) {
        notifyCustomerCashError("Insufficient Cash");

        PauseTransition timeout = new PauseTransition(javafx.util.Duration.seconds(3));
        timeout.setOnFinished(e -> {
          revertToDefaultCashInput();
        });
        timeout.play();
      }
    } catch (NumberFormatException e) {
      notifyCustomerCashError("Invalid Amount");

      PauseTransition timeout = new PauseTransition(javafx.util.Duration.seconds(3));
      timeout.setOnFinished(e2 -> {
        revertToDefaultCashInput();
      });
      timeout.play();
      return;
    }
  }

  private void notifyCustomerCashError(String text) {
    cashInputField.clear();
    cashInputField.setPromptText(text);
    cashInputField.setStyle(("-fx-prompt-text-fill: #FF0000;"));
  }

  private void revertToDefaultCashInput() {
    cashInputField.setPromptText("Input Amount");
    cashInputField.setStyle(("-fx-prompt-text-fill: #A9A9A9;"));
  }

  @FXML
  public void discountPWD() {
    if (isDiscounted) {
      return;
    }

    discountType = "PWD";
    isDiscounted = true;

    double totalAmount = Double.parseDouble(totalLabel.getText().split("₱")[1]);
    discountedAmount = totalAmount * 0.15;
    totalLabel.setText("Total(15% Off): ₱" + String.format("%.2f", totalAmount - discountedAmount));
  }

  @FXML
  public void discountSenior() {
    if (isDiscounted) {
      return;
    }

    discountType = "Senior";
    isDiscounted = true;

    double totalAmount = Double.parseDouble(totalLabel.getText().split("₱")[1]);
    discountedAmount = totalAmount * 0.15;
    totalLabel.setText("Total(15% Off): ₱" + String.format("%.2f", totalAmount - discountedAmount));
  }

  @FXML
  public void discountStudent() {
    if (isDiscounted) {
      return;
    }

    discountType = "Student";
    isDiscounted = true;

    double totalAmount = Double.parseDouble(totalLabel.getText().split("₱")[1]);
    discountedAmount = totalAmount * 0.15;
    totalLabel.setText("Total(15% Off): ₱" + String.format("%.2f", totalAmount - discountedAmount));
  }

  @FXML
  public void discountRegular() {
    if (!isDiscounted) {
      return;
    }

    discountType = "Regular";
    isDiscounted = false;

    double totalAmount = Double.parseDouble(totalLabel.getText().split("₱")[1]);
    double originalAmount = (totalAmount + discountedAmount);
    totalLabel.setText("Total: ₱" + String.format("%.2f", originalAmount));
  }

  @FXML
  public void switchToLostTicket() throws IOException {
    Stage stage = (Stage) cashInputField.getScene().getWindow();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("../receipt/lost/lostreceipt.fxml"));
    Parent root = loader.load();

    LostReceiptController controller = loader.getController();
    controller.start(selectedSlot.slotCode(), vehicleType);

    Scene scene = new Scene(root);
    stage.setScene(scene);
  }
}
