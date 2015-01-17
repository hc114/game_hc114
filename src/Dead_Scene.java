import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Dead_Scene{
	private Scene myScene;
	private Group myRoot;
	private Text GameOver;
	private ImageView Bloody;
	
	 Scene Dead_Scene(Stage s, int width, int height) {
			Group myRoot=new Group();
			Text GameOver=new Text();
			GameOver.setText("GAME OVER");
			GameOver.setTranslateX(width/4);
			GameOver.setTranslateY(height/2);
			GameOver.setFill(Color.RED);
			GameOver.setFont(Font.font(java.awt.Font.SANS_SERIF,40));
			myRoot.getChildren().add(GameOver);
			
			/*Set Background*/
			Bloody=new ImageView(new Image(getClass().getResourceAsStream("images/SawBlade.png")));
			
			myScene=new Scene(myRoot,500,365,Color.WHITE);
			return myScene;
		}
}


