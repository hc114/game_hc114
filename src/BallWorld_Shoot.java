import java.awt.geom.Line2D;
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


public class BallWorld_Shoot{
	private static final double ENEMY_GROWTH_FACTOR = 1.1;
	private static final int OPPONENT_SIZE = 40;
	private static final int PLAYER_SPEED = 10;
	private static final int BACK_SPEED=2;
	private static final int NUM_BACK=3;
	private static final int BULLET_VELOCITY=75;

	private Scene myScene;
	private Scene GameOver;
	private Scene Temp;

	private Group myRoot;
	private Group group_Blades;
	private Group group_Back;
	private Group group_Players;
	private Group group_Mouth;


	private ImageView myPlayer;
	private ImageView myBackground;
	private ImageView mySawBlade;
	private ImageView myMouth;
	private ImageView myBullet;
	private ImageView myCoins;
	private ImageView myTransparency;
	private Point2D mySawBladeVelocity;
	private Random myGenerator = new Random();
	private int i;
	private int wait=0;


	private ArrayList<Integer> coorX=new ArrayList<Integer>();
	private ArrayList<Integer> coorY=new ArrayList<Integer>();
	private ArrayList<Integer> start=new ArrayList<Integer>();
	private ArrayList<ImageView> mouths=new ArrayList<ImageView>();
	private ArrayList<Point2D> speedPoints=new ArrayList<Point2D>();
	private Text scoring_label;
	private Double Score;
	
	private Dead_Scene GO;
	
	private Stage end_Stage;
	private Boolean done=false;

	/**
	 * Create the game's scene
	 */
	public Scene init (Stage s, int width, int height) {
		s.setResizable(false);
		end_Stage=s;
		
		if(done){
			s.close();
		}
		
		/*Create an ArrayList*/
		for(int i=0; i<100; i++){
			coorX.add(myGenerator.nextInt(600));
		}

		for(int j=0; j<100; j++){
			coorY.add(myGenerator.nextInt(300));
		}

		for (int k=800; k>0;){
			start.add(k);
			k=k-10;
		}
		// create a scene graph to organize the scene
		group_Initialize();

		// make some shapes and set their properties
		//*Design, want to produce increasing amount of flying sawblades as the level goes up*//
		for (i=0;i<8;i++){
			speedPoints.add(new Point2D(myGenerator.nextInt(7),myGenerator.nextInt(7)));
		}
		
		newImage();	
		translateItem(width, height);

		/*Scrolling Pipes*/
		objectGenerator(coorX,coorY,start,mouths);

		// add elements to groups //
		group_Players.getChildren().add(myPlayer);
		group_Mouth.getChildren().add(myTransparency);
		group_Back.getChildren().add(myBackground);

		for(int i=0; i<6; i++){
			objectGenerator(coorX,coorY,start,mouths);
		}

		// add a bunch of mySawblades in a group of blades
		for(i=0;i<speedPoints.size();i++){
			mySawBlade=new ImageView(new Image(getClass().getResourceAsStream("images/SawBlade.png")));
			mySawBlade.setTranslateX(myGenerator.nextInt(width));
			mySawBlade.setTranslateY(myGenerator.nextInt(height));
			group_Blades.getChildren().add(mySawBlade);

		}

		//add all groups
		addGroup(myRoot,group_Back);
		addGroup(myRoot,group_Players);
		addGroup(myRoot,group_Mouth);

		// create a place to display the shapes and react to input
		Temp=new Scene(myRoot,500,365, Color.WHITE);
		Temp.setOnKeyPressed(e -> handleKeyInput(e));
		myScene = Temp;
		
		/*Create Text*/
		Score=0.0;
		scoring_label=new Text();
		scoring_label.setText("Score:" +Score);
		scoring_label.setTranslateX(25);
		scoring_label.setTranslateY(25);
		scoring_label.setFill(Color.ANTIQUEWHITE);
		scoring_label.setFont(Font.font(java.awt.Font.SANS_SERIF, 20));
		myRoot.getChildren().add(scoring_label);
		
		return myScene;

	}

	/**
	 * 
	 */
	private void group_Initialize() {
		myRoot = new Group();
		group_Blades=new Group();
		group_Back=new Group();
		group_Players=new Group();
		group_Mouth=new Group();
	}

	/**
	 * @param width
	 * @param height
	 */
	private void translateItem(int width, int height) {
		myPlayer.setTranslateX(myGenerator.nextInt(width));
		myPlayer.setTranslateY(myGenerator.nextInt(height));
		myBackground.setTranslateX(0);
		myBackground.setTranslateY(0);
		mySawBlade.setTranslateX(myGenerator.nextInt(width));
		mySawBlade.setTranslateY(myGenerator.nextInt(height));
		myBullet.setTranslateX(myPlayer.getTranslateX());
		myBullet.setTranslateY(myPlayer.getTranslateY());
	}

	/**
	 * 
	 */
	private void newImage() {
		myBackground=new ImageView(new Image(getClass().getResourceAsStream("images/background.png"))); 
		myCoins=new ImageView(new Image(getClass().getResourceAsStream("images/coins.png")));
		mySawBlade=new ImageView(new Image(getClass().getResourceAsStream("images/SawBlade.png")));
		myBullet = new ImageView(new Image(getClass().getResourceAsStream("images/bullet.png")));
		myPlayer = new ImageView(new Image(getClass().getResourceAsStream("images/meatboy.png")));
		myMouth=new ImageView(new Image(getClass().getResourceAsStream("images/mouth.png")));
		myTransparency=new ImageView(new Image(getClass().getResourceAsStream("images/transparency.png")));
	}

	/**
	 * 
	 */
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
		/*Scrolling Background*/
		myBackground.setTranslateX(myBackground.getTranslateX()-BACK_SPEED);
		if ((myBackground.getTranslateX()-BACK_SPEED)==-1500){
			myBackground.setTranslateX(0);
			i=i+1;
		};

		/*Scrolling Bullets*/
		myBullet.setTranslateX(myBullet.getTranslateX()+BULLET_VELOCITY);
		for(int k=0; k<mouths.size(); k++){
			if(checkCollide(myBullet,mouths.get(k))==true){
				mouths.get(k).setOpacity(0);
				mouths.remove(k);
				Score=Score+0.5;
				scoring_label.setText("Score:"+Score);
			}
			if(checkCollide(myPlayer,mouths.get(k))==true){
				endGame();			
			}
		}

		if(myBullet.getTranslateX()>myPlayer.getTranslateX()+500){
			myRoot.getChildren().remove(myBullet);
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
	}
	
	public void objectGenerator(ArrayList<Integer> x, ArrayList<Integer> y, ArrayList<Integer> s, ArrayList<ImageView> m){
		final Timeline animation=new Timeline(
				new KeyFrame(Duration.seconds(0.5), 
						new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent actionEvent) {	
						
						group_Mouth.getChildren().get(0).setOpacity(0);
						if(group_Mouth.getChildren().size()<(Score%5)+8){
							myMouth=new ImageView(new Image(getClass().getResourceAsStream("images/mouth.png")));
							myMouth.setTranslateX(1000-x.get(0));
							myMouth.setTranslateY(y.get(50));
							reAlign(y);
							reAlign(x);
							group_Mouth.getChildren().add(myMouth);
							m.add(myMouth);
						}

						if(s.get(0)<30){
							group_Mouth.getChildren().clear();
							m.clear();
							myMouth=new ImageView(new Image(getClass().getResourceAsStream("images/mouth.png")));
							myMouth.setTranslateX(1000-x.get(0));
							myMouth.setTranslateY(y.get(50));
							reAlign(y);
							reAlign(x);
							group_Mouth.getChildren().add(myMouth);
							m.add(myMouth);
						}

						for(int j=0; j<m.size(); j++){
							m.get(j).setTranslateX(s.get(0));
							m.get(j).setTranslateY(m.get(j).getTranslateY());
						}

						int temp_x=s.get(0);
						s.remove(0);
						s.add(temp_x);
					}
				})
				);
		animation.setCycleCount(Animation.INDEFINITE);
		animation.play();
	}

	private void reAlign(ArrayList<Integer> x){
		int temp=x.get(0);
		x.remove(0);
		x.add(temp);
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
		else if (keyCode == KeyCode.SPACE){
			Shoot();
		}
	}

	private void Shoot(){
		myBullet = new ImageView(new Image(getClass().getResourceAsStream("images/bullet.png")));
		myBullet.setTranslateX(myPlayer.getTranslateX());
		myBullet.setTranslateY(myPlayer.getTranslateY());
		myRoot.getChildren().add(myBullet);
		updateSprites ();
	}

	/**
	 * What to do each time shapes collide
	 */
	private boolean checkCollide (Node player, Node enemy) {
		// check for collision
		if(player.getBoundsInParent().intersects(enemy.getBoundsInParent())){
			myRoot.getChildren().remove(myBullet);
			return true;
		}	
		/*When Collide=True kill to the game over screen*/
		return false;
	}
}
