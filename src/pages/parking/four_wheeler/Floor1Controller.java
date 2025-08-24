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

public class Floor1Controller {
  // FXML 4-Wheeler elements
  @FXML
  Circle slot19;
  @FXML
  Circle slot20;
  @FXML
  Circle slot21;
  @FXML
  Circle slot22;
  @FXML
  Circle slot23;
  @FXML
  Circle slot24;
  @FXML
  Circle slot25;
  @FXML
  Circle slot26;
  @FXML
  Circle slot27;
  @FXML
  Circle slot28;
  @FXML
  Circle slot29;
  @FXML
  Circle slot30;
  @FXML
  Circle slot31;
  @FXML
  Circle slot32;
  @FXML
  Circle slot33;
  @FXML
  Circle slot34;
  @FXML
  Circle slot35;
  @FXML
  Circle slot36;
  @FXML
  Circle slot37;
  @FXML
  Circle slot38;
  @FXML
  Circle slot39;
  @FXML
  Circle slot40;
  @FXML
  Circle slot41;
  @FXML
  Circle slot42;
  @FXML
  Circle slot43;
  @FXML
  Circle slot44;
  @FXML
  Circle slot45;
  @FXML
  Circle slot46;
  @FXML
  Circle slot47;
  @FXML
  Circle slot48;

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
    slots.put(19, slot19);
    slots.put(20, slot20);
    slots.put(21, slot21);
    slots.put(22, slot22);
    slots.put(23, slot23);
    slots.put(24, slot24);
    slots.put(25, slot25);
    slots.put(26, slot26);
    slots.put(27, slot27);
    slots.put(28, slot28);
    slots.put(29, slot29);
    slots.put(30, slot30);
    slots.put(31, slot31);
    slots.put(32, slot32);
    slots.put(33, slot33);
    slots.put(34, slot34);
    slots.put(35, slot35);
    slots.put(36, slot36);
    slots.put(37, slot37);
    slots.put(38, slot38);
    slots.put(39, slot39);
    slots.put(40, slot40);
    slots.put(41, slot41);
    slots.put(42, slot42);
    slots.put(43, slot43);
    slots.put(44, slot44);
    slots.put(45, slot45);
    slots.put(46, slot46);
    slots.put(47, slot47);
    slots.put(48, slot48);

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
