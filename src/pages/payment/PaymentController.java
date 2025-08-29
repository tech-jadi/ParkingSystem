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

  /**
   * Loads the FXML file along with the details of the payment of the selected
   * slot.
   * 
   * @param slotCode the code of the parking slot selected by the customer.
   * @throws AssertionError if the selected slot is null
   */
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

  /**
   * Handles the payment confirmation process. Validates the input cash amount,
   * calculates change if applicable, and navigates to the receipt page upon
   * successful
   * payment.
   * 
   * @throws IOException when the receipt FXML file cannot be loaded.
   */
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

      // Check if cash is sufficient for payment
      if (customerCash >= totalAmount) {
        // Proceed to receipt
        Stage stage = (Stage) cashInputField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../receipt/normal/receipt.fxml"));
        Parent root = loader.load();

        // Initializes receipt details for the upcoming receipt page
        ReceiptController controller = loader.getController();
        controller.start(new Customer(vehicleType, discountType, selectedSlot.slotCode(), startingTime,
            exitTime, subtotal, discountedAmount, customerCash));

        Scene scene = new Scene(root);
        stage.setScene(scene);
      } else if (customerCash < totalAmount) {
        // If cash is insufficient, show error on the input field as prompt text
        notifyCustomerCashError("Insufficient Cash");

        // Revert back to default prompt text after 3 seconds
        PauseTransition timeout = new PauseTransition(javafx.util.Duration.seconds(3));
        timeout.setOnFinished(e -> {
          revertToDefaultCashInput();
        });
        timeout.play();
      }
    } catch (NumberFormatException e) {
      // If input is not a number, show error on the input field as prompt text
      notifyCustomerCashError("Invalid Amount");

      // Revert back to default prompt text after 3 seconds
      PauseTransition timeout = new PauseTransition(javafx.util.Duration.seconds(3));
      timeout.setOnFinished(e2 -> {
        revertToDefaultCashInput();
      });
      timeout.play();
      return;
    }
  }

  /**
   * Clears the input field, sets the prompt text to the specified error message,
   * and sets the prompt text color to red.
   * 
   * @param text the error message to be displayed as prompt text.
   */
  private void notifyCustomerCashError(String text) {
    cashInputField.clear();
    cashInputField.setPromptText(text);
    cashInputField.setStyle(("-fx-prompt-text-fill: #FF0000;"));
  }

  /**
   * Reverts the cash input field to its default state with the original prompt
   * text and style.
   */
  private void revertToDefaultCashInput() {
    cashInputField.setPromptText("Input Amount");
    cashInputField.setStyle(("-fx-prompt-text-fill: #A9A9A9;"));
  }

  /**
   * Applies a 15% discount for <i>Persons with Disabilities (PWD)</i> if no other
   * discount has been applied yet. Updates the total amount displayed. If a
   * discount has already been applied, the method exits without making any
   * changes (<b>to avoid infinite discounting</b>).
   */
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

  /**
   * Applies a 15% discount for <i>Senior Citizens</i> if no other
   * discount has been applied yet. Updates the total amount displayed. If a
   * discount has already been applied, the method exits without making any
   * changes (<b>to avoid infinite discounting</b>).
   */
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

  /**
   * Applies a 15% discount for <i>Studens</i> if no other
   * discount has been applied yet. Updates the total amount displayed. If a
   * discount has already been applied, the method exits without making any
   * changes (<b>to avoid infinite discounting</b>).
   */
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

  /**
   * Reverts any applied discount, restoring the total amount to its original
   * value
   * before any discounts were applied. This method only functions if a discount
   * has previously been applied; otherwise, it exits without making changes.
   */
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

  /**
   * Switches to Lost Ticket page when the "Lost Ticket" label is clicked. Passes
   * the selected slot code and vehicle type to the LostReceiptController for
   * further details to display.
   * 
   * @throws IOException when the lost ticket FXML file cannot be loaded.
   */
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
