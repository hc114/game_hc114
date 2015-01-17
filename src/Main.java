import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * This is the main program, it is basically boilerplate to create an animated scene.
 *
 * @author Robert C. Duvall
 */
public class Main extends Application {
    private static final int NUM_FRAMES_PER_SECOND = 70;
    private BallWorld myGame;
    private BallWorld_Shoot myGame_stage2;
    private Splash SplashScene;
    private Scene s1_Scene;
    private Scene s2_Scene;

    /**
     * Set things up at the beginning.
     */
    @Override
    public void start (Stage s) {
        s.setTitle("MeatBoy!");
        // create your own game here
        myGame = new BallWorld();
        myGame_stage2=new BallWorld_Shoot();
        Scene s1_Scene=myGame.init(s,500,365);
        Scene s2_Scene=myGame_stage2.init(s,500,365);
        
        SplashScene=new Splash();
        
        // attach game to the stage and display it
        Scene scene = SplashScene.Splash(s,s1_Scene,s2_Scene, 500, 365);

        s.setScene(scene);
        s.show();

        // setup the game's loop
        KeyFrame frame = myGame.start(NUM_FRAMES_PER_SECOND);
        Timeline animation = new Timeline();
        animation.setCycleCount(Animation.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
        
        KeyFrame frame2 = myGame_stage2.start(NUM_FRAMES_PER_SECOND);
        Timeline animation2 = new Timeline();
        animation2.setCycleCount(Animation.INDEFINITE);
        animation2.getKeyFrames().add(frame2);
        animation2.play();
    }

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
