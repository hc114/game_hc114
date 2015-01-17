import java.awt.event.KeyEvent;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.*;


public class Splash {
	private Scene Splash;
	private Group SplashRoot;
	private Text Introduction;
	private ImageView Bloody;
	private Text Stage_1;
	private Text Stage_2;
	private Text Information;
	private Boolean s1;
	private Boolean s2;
	
	public Scene Splash(Stage s, Scene gs, Scene gs2, int width, int height){
		Group SplashRoot=new Group();
		
		Text Introduction=new Text();
		Introduction.setText("MEATBOY & SURVIVAL");
		Introduction.setTranslateX(width/5.5);
		Introduction.setTranslateY(height/2);
		Introduction.setFill(Color.GRAY);
		Introduction.setFont(Font.font(java.awt.Font.SANS_SERIF,30));
		SplashRoot.getChildren().add(Introduction);
		
		Text Stage_1=new Text();
		Stage_1.setText("Stage 1:HUNGRY MOUTHS");
		Stage_1.setTranslateX(width/4);
		Stage_1.setTranslateY(height/2+25);
		Stage_1.setFont(Font.font(java.awt.Font.BOLD));
		Stage_1.setFill(Color.GRAY);
		Stage_1.setFont(Font.font(java.awt.Font.SANS_SERIF,20));
		//Stage_1.setOnMouseClicked(e->choice(e));
		SplashRoot.getChildren().add(Stage_1);
		
		Text Stage_2=new Text();
		Stage_2.setText("Stage 2:BLOODY SAWBLADES");
		Stage_2.setTranslateX(width/4);
		Stage_2.setTranslateY(height/2+50);
		Stage_2.setFill(Color.GRAY);
		Stage_2.setFont(Font.font(java.awt.Font.BOLD));
		Stage_2.setFont(Font.font(java.awt.Font.SANS_SERIF,20));
		//Stage_2.setOnMouseClicked(e->choice(e));
		SplashRoot.getChildren().add(Stage_2);
		
		Text Information=new Text();
		Information.setText("Press Enter for Stage 1 and Space for Stage 2");
		Information.setTranslateX(width/4);
		Information.setTranslateY(height/2+70);
		Information.setFill(Color.GRAY);
		Information.setFont(Font.font(java.awt.Font.BOLD));
		Information.setFont(Font.font(java.awt.Font.SANS_SERIF,10));
		//Stage_2.setOnMouseClicked(e->choice(e));
		SplashRoot.getChildren().add(Information);
		
		Splash=new Scene(SplashRoot,width,height,Color.CRIMSON);
		Splash.setOnKeyPressed(e->handleKeyInput(s,gs,gs2,e));
		return Splash;
		
	}
	public void handleKeyInput(Stage s, Scene game_main, Scene game_main2, javafx.scene.input.KeyEvent e){
		KeyCode keyCode=e.getCode();
			if(keyCode==KeyCode.ENTER){
				s.setScene(game_main);
			}
			if(keyCode==KeyCode.SPACE){
				s.setScene(game_main2);
			}
	}
	

}
