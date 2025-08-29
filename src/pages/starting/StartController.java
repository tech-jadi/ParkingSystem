package pages.starting;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class StartController {
  private Stage stage;
  private Scene scene;
  private Parent root;

  @FXML
  Label parkingLabel;

  /**
   * Switches to Vehicle Selection page when the "Back" label is clicked.
   * 
   * @throws IOException when the vehicle selection FXML file cannot be loaded.
   */
  @FXML
  public void switchToVehicleSelection() throws IOException {
    stage = (Stage) parkingLabel.getScene().getWindow();
    root = FXMLLoader.load(getClass().getResource("../vehicle_type/vehicleselection.fxml"));
    scene = new Scene(root);
    stage.setScene(scene);
  }

  /**
   * Switches to Check Out page when the "Checkout" label is clicked.
   * 
   * @throws IOException when the checkout FXML file cannot be loaded.
   */
  @FXML
  public void switchToCheckOut() throws IOException {
    stage = (Stage) parkingLabel.getScene().getWindow();
    root = FXMLLoader.load(getClass().getResource("../checkout/parkingslotcode.fxml"));
    scene = new Scene(root);
    stage.setScene(scene);
  }
}
