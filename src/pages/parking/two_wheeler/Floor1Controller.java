package pages.parking.two_wheeler;

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

public class Floor1Controller {
  // FXML 2-Wheeler elements
  @FXML
  Circle slot1;
  @FXML
  Circle slot2;
  @FXML
  Circle slot3;
  @FXML
  Circle slot4;
  @FXML
  Circle slot5;
  @FXML
  Circle slot6;
  @FXML
  Circle slot7;
  @FXML
  Circle slot8;
  @FXML
  Circle slot9;

  // Circle container
  private Map<Integer, Circle> slots = new HashMap<>();

  @FXML
  Label nextLabel;

  private void setSlotOccupied() {
    for (Map.Entry<Integer, Circle> entry : slots.entrySet()) {
      int key = entry.getKey();
      Circle circle = entry.getValue();

      List<Integer> availableSlots = DatabaseConnection.getIdOfAvailableSlots();
      if (!availableSlots.contains(key)) {
        circle.setFill(Color.web("#ff5757"));
      }
    }
  }

  @SuppressWarnings("unused")
  @FXML
  public void initialize() {
    slots.put(1, slot1);
    slots.put(2, slot2);
    slots.put(3, slot3);
    slots.put(4, slot4);
    slots.put(5, slot5);
    slots.put(6, slot6);
    slots.put(7, slot7);
    slots.put(8, slot8);
    slots.put(9, slot9);

    // Set occupied slots
    setSlotOccupied();

    // Set on click listeners
    for (Map.Entry<Integer, Circle> entry : slots.entrySet()) {
      Circle circle = entry.getValue();
      circle.setOnMouseClicked(event -> {
        // Only if the slot is available
        if (circle.getFill().equals(Color.web("#7ed957"))) {
          DatabaseConnection.setOccupied(entry.getKey(), "2-Wheeler");

          // Generate ticket
          try {
            Parent root = FXMLLoader.load(getClass().getResource("../../ticket/parkingticket.fxml"));
            Stage stage = (Stage) nextLabel.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      });
    }
  }

  @FXML
  public void goToFloor2() throws IOException {
    Stage stage = (Stage) nextLabel.getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("floor2.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
  }

  @FXML
  public void goToVehicleSelection() throws IOException {
    Stage stage = (Stage) nextLabel.getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("../../vehicle_type/VehicleSelection.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
  }
}
