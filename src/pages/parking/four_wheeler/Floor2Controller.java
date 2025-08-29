package pages.parking.four_wheeler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Floor2Controller {
  // FXML 4-Wheeler elements
  @FXML
  Circle slot49;
  @FXML
  Circle slot50;
  @FXML
  Circle slot51;
  @FXML
  Circle slot52;
  @FXML
  Circle slot53;
  @FXML
  Circle slot54;
  @FXML
  Circle slot55;
  @FXML
  Circle slot56;
  @FXML
  Circle slot57;
  @FXML
  Circle slot58;
  @FXML
  Circle slot59;
  @FXML
  Circle slot60;
  @FXML
  Circle slot61;
  @FXML
  Circle slot62;
  @FXML
  Circle slot63;
  @FXML
  Circle slot64;
  @FXML
  Circle slot65;
  @FXML
  Circle slot66;
  @FXML
  Circle slot67;
  @FXML
  Circle slot68;
  @FXML
  Circle slot69;
  @FXML
  Circle slot70;
  @FXML
  Circle slot71;
  @FXML
  Circle slot72;
  @FXML
  Circle slot73;
  @FXML
  Circle slot74;
  @FXML
  Circle slot75;
  @FXML
  Circle slot76;
  @FXML
  Circle slot77;
  @FXML
  Circle slot78;

  // Circle container
  private Map<Integer, Circle> slots = new HashMap<>();

  @FXML
  Label prevLabel;

  /**
   * Sets the color of occupied slots to red based on the database records.
   */
  private void setSlotOccupied() {
    for (Map.Entry<Integer, Circle> entry : slots.entrySet()) {
      int key = entry.getKey();
      Circle circle = entry.getValue();

      // Check if the slot is occupied, if yes set to red.
      List<Integer> availableSlots = DatabaseConnection.getIdOfAvailableSlots();
      if (!availableSlots.contains(key)) {
        circle.setFill(Color.web("#ff5757"));
      }
    }
  }

  /**
   * Auto invokes itself once the FXML file is loaded. Initializes the slot map,
   * sets occupied slots, and assigns click listeners to available slots.
   */
  @SuppressWarnings("unused")
  @FXML
  public void initialize() {
    slots.put(49, slot49);
    slots.put(50, slot50);
    slots.put(51, slot51);
    slots.put(52, slot52);
    slots.put(53, slot53);
    slots.put(54, slot54);
    slots.put(55, slot55);
    slots.put(56, slot56);
    slots.put(57, slot57);
    slots.put(58, slot58);
    slots.put(59, slot59);
    slots.put(60, slot60);
    slots.put(61, slot61);
    slots.put(62, slot62);
    slots.put(63, slot63);
    slots.put(64, slot64);
    slots.put(65, slot65);
    slots.put(66, slot66);
    slots.put(67, slot67);
    slots.put(68, slot68);
    slots.put(69, slot69);
    slots.put(70, slot70);
    slots.put(71, slot71);
    slots.put(72, slot72);
    slots.put(73, slot73);
    slots.put(74, slot74);
    slots.put(75, slot75);
    slots.put(76, slot76);
    slots.put(77, slot77);
    slots.put(78, slot78);

    // Set occupied slots
    setSlotOccupied();

    // Set on click listeners
    for (Map.Entry<Integer, Circle> entry : slots.entrySet()) {
      Circle circle = entry.getValue();
      circle.setOnMouseClicked(event -> {
        // Only if the slot is available
        if (circle.getFill().equals(Color.web("#7ed957"))) {
          DatabaseConnection.setOccupied(entry.getKey(), "4-Wheeler");

          // Generate ticket
          try {
            Parent root = FXMLLoader.load(getClass().getResource("../../ticket/parkingticket.fxml"));
            Stage stage = (Stage) prevLabel.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      });
    }
  }

  /**
   * Switches to Floor 1 page when the "PREV" label is clicked.
   * 
   * @throws IOException when the floor 1 FXML file cannot be loaded.
   */
  @FXML
  public void goToFloor1() throws IOException {
    Stage stage = (Stage) prevLabel.getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("floor1.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
  }

  /**
   * Switches to Vehicle Selection page when the "Back" label is clicked.
   * 
   * @throws IOException when the vehicle selection FXML file cannot be loaded.
   */
  @FXML
  public void goToVehicleSelection() throws IOException {
    Stage stage = (Stage) prevLabel.getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("../../vehicle_type/VehicleSelection.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
  }
}
