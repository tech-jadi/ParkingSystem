package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

  /**
   * Starts the JavaFX application by loading the initial FXML layout and
   * displaying it in the primary stage.
   * 
   * @param primaryStage the primary stage for this application, onto which the
   *                     application scene can be set.
   * @throws IOException if the FXML file cannot be loaded.
   */
  @Override
  public void start(Stage primaryStage) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("../pages/starting/start.fxml"));
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
