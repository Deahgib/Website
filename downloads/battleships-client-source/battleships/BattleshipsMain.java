package battleships;

import battleships.engine.GameGlobals;
import battleships.engine.GameLogger;
import battleships.engine.InputReader;
import battleships.engine.OpenGLManager;
import battleships.engine.TextureManager;
import battleships.engine.Time;
import battleships.engine.Window;
import battleships.engine.audio.AudioManager;
import battleships.scene.components.image.Image;

/**
 * @author Louis Bennette
 */
public class BattleshipsMain {
    
    public static void main(String[] args) {
        // Start up the game.
        // Create a window.
        Window.create();

        // Initialise openGL for 2D orthographic view.
        OpenGLManager.initOpenGL();
        
        createLoadingBackground(); // Used for the the user to see the game is loading.
        
        GameGlobals.initGlobals();
        
        AudioManager.initOpenAL();
        AudioManager.createSounds();
        
        GameLoop gameLoop = new GameLoop();
        gameLoop.start(); // <---- Game happens here
        
        AudioManager.destroyOpenAL();
        Window.destroy();
        GameLogger.print("GAME EXITING NORMALY");
        System.exit(0);
    }
    
    private static void createLoadingBackground(){
        // Create a background for the User to know the game is loading.
        Image bck = new Image(0, 0, Window.getWidth(), Window.getHeight(), "menu-back");
        bck.draw();
        int xLoc = (Window.getWidth()/2) - (Window.getHeight()/2);
        Image loading = new Image(xLoc, 0, Window.getHeight(), Window.getHeight(), "loading");
        loading.draw();
        Window.update();
    }
}
