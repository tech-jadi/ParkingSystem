package pages.vehicle_type;

import java.io.IOException;
import java.util.Map;

import database.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class VehicleSelectionController {

  private Stage stage;
  private Scene scene;
  private Parent root;

  @FXML
  AnchorPane mainPane;
  @FXML
  Label backvsLabel;
  @FXML
  ImageView twoWheelerFullSlotImage;
  @FXML
  ImageView fourWheelerFullSlotImage;
  @FXML
  ImageView fourWheelerImage;
  @FXML
  ImageView twoWheelerImage;

  @FXML
  public void initialize() {
    Map<String, Integer> occupiedParkingLotMap = DatabaseConnection.getOccupiedParkingLot();

    // 2-Wheeler slots full logic
    Image image = new Image("file:D:/workspace/ParkingSystem/assets/img/full-parking.png");
    if (occupiedParkingLotMap.get("2-Wheeler") == 18) {
      twoWheelerFullSlotImage.setImage(image);
      twoWheelerImage.setOnMouseClicked(null);
    } else {
      mainPane.getChildren().remove(twoWheelerFullSlotImage);
    }

    if (occupiedParkingLotMap.get("4-Wheeler") == 60) {
      fourWheelerFullSlotImage.setImage(image);
      fourWheelerImage.setOnMouseClicked(null);
    } else {
      mainPane.getChildren().remove(fourWheelerFullSlotImage);
    }
  }

  @FXML
  public void switchToStartingPage() throws IOException {
    stage = (Stage) backvsLabel.getScene().getWindow();
    root = FXMLLoader.load(getClass().getResource("../starting/start.fxml"));
    scene = new Scene(root);
    stage.setScene(scene);
  }

  @FXML
  public void switchToTwoWheelerPage() throws IOException {
    stage = (Stage) backvsLabel.getScene().getWindow();
    root = FXMLLoader.load(getClass().getResource("../parking/two_wheeler/floor1.fxml"));
    scene = new Scene(root);
    stage.setScene(scene);
  }

  @FXML
  public void switchToFourWheelerPage() throws IOException {
    stage = (Stage) backvsLabel.getScene().getWindow();
    root = FXMLLoader.load(getClass().getResource("../parking/four_wheeler/floor1.fxml"));
    scene = new Scene(root);
    stage.setScene(scene);
  }
}
