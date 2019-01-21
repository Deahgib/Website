package battleships;

import battleships.engine.*;
import battleships.engine.events.TimeEventList;
import battleships.scene.SceneState;
import battleships.scene.view.IView;
import battleships.scene.view.credits.Credits;
import battleships.scene.view.menu.Menu;
import battleships.scene.view.multiplayer.MultiplayerGame;
import battleships.scene.view.options.Options;
import battleships.scene.view.singleplayer.SingleplayerGame;

/**
 *
 * @author Louis Bennette
 */
public class GameLoop {
    private static boolean closeRequested = false;
    private static boolean restartCurrentView = false;

    public static boolean isCloseRequested() {
        return closeRequested;
    }
    public static void setCloseRequested(boolean aCloseRequested) {
        closeRequested = aCloseRequested;
    }

    public static boolean isRestartCurrentView() {
        return restartCurrentView;
    }
    public static void setRestartCurrentView(boolean aRestartCurrentView) {
        restartCurrentView = aRestartCurrentView;
    }
    
    
    
    public GameLoop(){
        // Set game state.
        InputReader.initValidKeys();
        
        Time.initTime();
        
        SceneState.setGeneralState(SceneState.General.MENU);
    }
    
    
    private IView currentSceneView;
    
    /*
     * This statrs off the game loop. The game loop is made up of scene views.
     * There can only be one scene view active at any one time.
     * The sceneview is updated every loop.
     * One loop represents one frame in the game.
     */
    public void start(){
        while(!Window.isCloseRequested()){
            OpenGLManager.clearBuffers();
            
            if(GameLoop.isRestartCurrentView()){
                restartCurrentView();
            }
            
            switch(SceneState.getGeneralState()){
                case MENU:
                    if(currentSceneView == null || !(currentSceneView instanceof Menu)){
                        TextureManager.flushTextures();
                        currentSceneView = new Menu();
                    }
                    break;
                case SINGLEPLAYER:
                    if(currentSceneView == null || !(currentSceneView instanceof SingleplayerGame)){
                        TextureManager.flushTextures();
                        currentSceneView = new SingleplayerGame();
                    }
                    break;
                case MULTIPLAYER:
                    if(currentSceneView == null || !(currentSceneView instanceof MultiplayerGame)){
                        TextureManager.flushTextures();
                        currentSceneView = new MultiplayerGame();
                    }
                    break;
                case OPTIONS:
                    if(currentSceneView == null || !(currentSceneView instanceof Options)){
                        TextureManager.flushTextures();
                        currentSceneView = new Options();
                    }
                    break;
                case CREDITS:
                    if(currentSceneView == null || !(currentSceneView instanceof Credits)){
                        TextureManager.flushTextures();
                        currentSceneView = new Credits();
                    }
                    break;
            }
            
            currentSceneView.update();
            currentSceneView.draw();
            
            Input.update();
            
            //All code conserning the game must be above the window update
            Window.update();
            Window.sync();
            
            // Handle any time based events.
            if(!TimeEventList.isEmpty()){
                TimeEventList.handleEvent();
                TimeEventList.cleanup();
            }
            
            Time.setDelta();
            
            // FPS TEST
            
            double timeSinceLastFrame = Time.getDelta();
            double fps = 60 / timeSinceLastFrame;
            //System.out.println(fps);
            
            
            if(GameGlobals.DEBUG_MODE){
                if(Input.getKey(Input.KEY_DEL)){
                    GameLoop.setCloseRequested(true);
                }
            }
            
            if(GameLoop.isCloseRequested()){
                break;
            }
        }
    }
    
    private void restartCurrentView() {
        switch (SceneState.getGeneralState()) {
            case MENU:
                currentSceneView = new Menu();
                break;
            case SINGLEPLAYER:
                currentSceneView = new SingleplayerGame();
                break;
            case MULTIPLAYER:
                currentSceneView = new MultiplayerGame();
                break;
        }
        GameLoop.setRestartCurrentView(false);
    }
}
