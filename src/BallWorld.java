import java.util.ArrayList;
import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class BallWorld{
	private static final double ENEMY_GROWTH_FACTOR = 1.1;
	private static final int OPPONENT_SIZE = 40;
	private static final int PLAYER_SPEED = 10;
	private static final int BACK_SPEED=2;
	private static final int NUM_BACK=3;
	private Scene myScene;
	private Scene Temp;
	private Group myRoot;
	private Group group_Blades;
	private Group group_Back;
	private Group group_Players;
	private ImageView myPlayer;
	private ImageView myBackground;
	private ImageView mySawBlade;
	private Point2D mySawBladeVelocity;
	private Random myGenerator = new Random();
	private Integer i;
	private Integer frameR =0;
	private Integer before=1;
	private ArrayList<Point2D> speedPoints=new ArrayList<Point2D>();
	private boolean Collide=false;
	private Text Time;

	/**
	 * Create the game's scene
	 */
	public Scene init (Stage s, int width, int height) {		
		/*Create an ArrayList of background*/
		newImage();
		initializeGroup();
		imageTranslate(width, height);

		//*Design, want to produce increasing amount of flying sawblades as the level goes up*//
		for (i=0;i<8;i++){
			speedPoints.add(new Point2D(myGenerator.nextInt(7),myGenerator.nextInt(7)));
		}
		
		group_Blades.getChildren().add(mySawBlade);
		group_Players.getChildren().add(myPlayer);
		group_Back.getChildren().add(myBackground);

		//add all groups
		addGroup(myRoot,group_Back);
		addGroup(myRoot,group_Blades);
		addGroup(myRoot,group_Players);

		// create a place to display the shapes and react to input
		Temp=new Scene(myRoot,500,365, Color.WHITE);
		Temp.setOnKeyPressed(e -> handleKeyInput(e));
		myScene = Temp;
		
		/*Create Text*/
		Time=new Text();
		Time.setText("Time:"+frameR);
		Time.setTranslateX(25);
		Time.setTranslateY(25);
		Time.setFill(Color.ANTIQUEWHITE);
		Time.setFont(Font.font(java.awt.Font.SANS_SERIF, 20));
		myRoot.getChildren().add(Time);
		
		System.out.println(frameR);
		return myScene;

	}
	
	private void initializeGroup() {
		myRoot = new Group();
		group_Blades=new Group();
		group_Back=new Group();
		group_Players=new Group();
	}

	private void imageTranslate(int width, int height) {
		myPlayer.setTranslateX(25);
		myPlayer.setTranslateY(25);
		myBackground.setTranslateX(0);
		myBackground.setTranslateY(0);
		mySawBlade.setTranslateX(myGenerator.nextInt(width));
		mySawBlade.setTranslateY(myGenerator.nextInt(height));
	}


	private void newImage() {
		myBackground=new ImageView(new Image(getClass().getResourceAsStream("images/background.png")));             
		myPlayer = new ImageView(new Image(getClass().getResourceAsStream("images/meatboy.png")));
		mySawBlade=new ImageView(new Image(getClass().getResourceAsStream("images/SawBlade.png")));
	}

	public void addGroup(Group g, Group g2) {
		g.getChildren().add(g2);
	}


	/**
	 * Create the game's frame
	 */
	public KeyFrame start (int frameRate) {
		return new KeyFrame(Duration.millis(1000 / frameRate), e -> updateSprites());
	}

	/**
	 * What to do each game frame
	 *
	 * Change the sprite properties each frame by a tiny amount to animate them
	 *
	 * Note, there are more sophisticated ways to animate shapes, but these simple ways work too.
	 */
	private void updateSprites () {
		frameR=frameR+1;
		Time.setText("Time:" + frameR);
		if(frameR%500==499){			
			for(i=0;i<(before+1);i++){
				Collide=false;
				mySawBlade=new ImageView(new Image(getClass().getResourceAsStream("images/SawBlade.png")));
				mySawBlade.setTranslateX(myGenerator.nextInt(700));
				mySawBlade.setTranslateY(myGenerator.nextInt(365));
				group_Blades.getChildren().add(mySawBlade);
			}
			if(group_Blades.getChildren().size()>8){
				group_Blades.getChildren().clear();
			}
		}

		/*SawBlade random movement*/
		if(group_Blades.getChildren().size()!=0){
			for(int i=0; i<group_Blades.getChildren().size(); i++){
				mySawBladeVelocity=speedPoints.get(i);
				group_Blades.getChildren().get(i).setRotate(group_Blades.getChildren().get(i).getRotate()+20);
				group_Blades.getChildren().get(i).setTranslateX(group_Blades.getChildren().get(i).getTranslateX()+mySawBladeVelocity.getX());
				group_Blades.getChildren().get(i).setTranslateY(group_Blades.getChildren().get(i).getTranslateY()+mySawBladeVelocity.getY());

				if(group_Blades.getChildren().get(i).getTranslateX() >=myScene.getWidth() || group_Blades.getChildren().get(i).getTranslateX()<=0){
					mySawBladeVelocity=new Point2D(mySawBladeVelocity.getX()*-1, mySawBladeVelocity.getY());
					speedPoints.remove(i);
					speedPoints.add(i,mySawBladeVelocity);

				}

				if (group_Blades.getChildren().get(i).getTranslateY()>myScene.getHeight() || group_Blades.getChildren().get(i).getTranslateY()<=0){
					mySawBladeVelocity=new Point2D(mySawBladeVelocity.getX(), mySawBladeVelocity.getY()*-1);
					speedPoints.remove(i);
					speedPoints.add(i,mySawBladeVelocity);
				}
			}
		}

		/*Scrolling Background*/
		myBackground.setTranslateX(myBackground.getTranslateX()-BACK_SPEED);
		if ((myBackground.getTranslateX()-BACK_SPEED)==-1500){
			myBackground.setTranslateX(0);
			i=i+1;
		};
		
		for (int p=0;p<group_Blades.getChildren().size();p++){
			checkCollide(myPlayer, group_Blades.getChildren().get(p));
			if(Collide){
				endGame();
			}
		}

	}

	public void endGame(){
		myScene.setFill(Color.RED);
		myRoot.getChildren().clear();
		Text Final=new Text();
		Final.setText("YOU DIDN'T SURVIVE");
		Final.setFill(Color.BLACK);
		Final.setFont(Font.font(java.awt.Font.SANS_SERIF,30));
		Final.setTranslateX(105);
		Final.setTranslateY(182);
		myRoot.getChildren().add(Final);
		Collide=false;
	}

	/**
	 * What to do each time a key is pressed
	 */
	private void handleKeyInput (KeyEvent e) {
		KeyCode keyCode = e.getCode();
		if (keyCode == KeyCode.RIGHT) {
			myPlayer.setTranslateX(myPlayer.getTranslateX() + PLAYER_SPEED);
		}
		else if (keyCode == KeyCode.LEFT) {
			myPlayer.setTranslateX(myPlayer.getTranslateX() - PLAYER_SPEED);
		}
		else if (keyCode == KeyCode.UP) {
			myPlayer.setTranslateY(myPlayer.getTranslateY() - PLAYER_SPEED);
		}
		else if (keyCode == KeyCode.DOWN) {
			myPlayer.setTranslateY(myPlayer.getTranslateY() + PLAYER_SPEED);
		}

	}
	/**
	 * What to do each time shapes collide
	 */
	private boolean checkCollide (Node player, Node enemy) {
		// check for collision
		if(player.getBoundsInParent().intersects(enemy.getBoundsInParent())){
			Collide=true;
			System.out.println("hit");
			return true;
		}
		/*When Collide=True kill to the game over screen*/
		return false;
	}
}
