import javafx.animation.*;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class PipeGenerator{
	private static final Random r = new Random();	
	private ImageView Pipings;

	public void PipeGenerator(ImageView x){
		final Group Pipes=new Group();
		final Timeline animation=new Timeline(
				new KeyFrame(Duration.seconds(.5), 
						new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent actionEvent) {
						while (Pipes.getChildren().size() >= 5) Pipes.getChildren().remove(0);
						Pipings=new ImageView(new Image(getClass().getResourceAsStream("images/pipes.png")));
						Pipings.setTranslateX(x.getTranslateX());
						Pipings.setTranslateY(x.getTranslateY());
						Pipings.resize(r.nextInt(4),r.nextInt(7));
						Pipes.getChildren().add(
								Pipings
								);
					}
				})
				);
		animation.setCycleCount(Animation.INDEFINITE);
		animation.play();
	}


}
