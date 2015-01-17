import javafx.animation.*;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class FiveAutoCircleExample extends Application {
  private static final Random r = new Random();
  public static final int SCENE_SIZE = 800;

  public static void main(String[] args) throws Exception { launch(args); }
  public void start(final Stage stage) throws Exception {
    final Group circles = new Group();
    final Timeline animation = new Timeline(
      new KeyFrame(Duration.seconds(.5), 
      new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent actionEvent) {
          while (circles.getChildren().size() >= 5) circles.getChildren().remove(0);
          int radius = 10 * r.nextInt(20);
          circles.getChildren().add(
            new Circle(
              r.nextInt(SCENE_SIZE - radius * 2) + radius, r.nextInt(SCENE_SIZE - radius * 2) + radius,
              radius,
              new Color(r.nextDouble(), r.nextDouble(), r.nextDouble(), r.nextDouble())
            )
          );
        }
      })
    );
    animation.setCycleCount(Animation.INDEFINITE);
    animation.play();

    // display the scene.
    stage.setScene(new Scene(circles, SCENE_SIZE, SCENE_SIZE, Color.CORNSILK));
    stage.show();
  }
}