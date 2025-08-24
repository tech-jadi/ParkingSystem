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

public class Floor2Controller {
  // FXML 2-Wheeler elements
  @FXML
  Circle slot10;
  @FXML
  Circle slot11;
  @FXML
  Circle slot12;
  @FXML
  Circle slot13;
  @FXML
  Circle slot14;
  @FXML
  Circle slot15;
  @FXML
  Circle slot16;
  @FXML
  Circle slot17;
  @FXML
  Circle slot18;

  // Circle container
  private Map<Integer, Circle> slots = new HashMap<>();

  @FXML
  Label prevLabel;

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
    slots.put(10, slot10);
    slots.put(11, slot11);
    slots.put(12, slot12);
    slots.put(13, slot13);
    slots.put(14, slot14);
    slots.put(15, slot15);
    slots.put(16, slot16);
    slots.put(17, slot17);
    slots.put(18, slot18);

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

  @FXML
  public void goToFloor1() throws IOException {
    Stage stage = (Stage) prevLabel.getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("floor1.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
  }

  @FXML
  public void goToVehicleSelection() throws IOException {
    Stage stage = (Stage) prevLabel.getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("../../vehicle_type/VehicleSelection.fxml"));
    Scene scene = new Scene(root);
    stage.setScene(scene);
  }
}
