/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import audio.AudioManager;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import score.ScoreKeeper;
import static org.lwjgl.opengl.GL11.*;

/**
 * @author Louis
 */

public class MainLoop {
    
    public static enum State{
        INTRO, START_WINDOW, GAME, GAME_OVER;
    }
    private IntroTitle introTitle;
    private Game game;
    private GameOver gameOver;
    private StartWindow startWindow;
    private long lastFrame;
    private int xRez, yRez, fps;
    public static State gameState = State.INTRO;
    
    public MainLoop(int xRez, int yRez){
        this.xRez = xRez;
        this.yRez = yRez;
        fps = 60;
    }
    
    public void runGame(){
        initDisplay();
        initOpenGL();
        AudioManager.setUpALSounds();
        long loadPageTimer = 0; // Sets the delay for the logo screen
        
        setTimer();
        
        while(!Display.isCloseRequested()){
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            switch(MainLoop.gameState){
                case INTRO:
                    if(introTitle == null){
                        introTitle = new IntroTitle();
                        introTitle.initEntities();
                        loadPageTimer = System.currentTimeMillis() + 1000;
                    }
                    System.out.println(MainLoop.gameState);
                    introTitle.render();
                    if (loadPageTimer < System.currentTimeMillis()) {
                        ScoreKeeper.initFonts();
                        Game.initFonts();
                        AudioManager.initSounds();
                        MainLoop.gameState = MainLoop.State.START_WINDOW;
                        introTitle.destroyEntities();
                        introTitle = null;
                    }
                    
                    break;
                case START_WINDOW:
                    if(startWindow == null){
                        startWindow = new StartWindow();
                        startWindow.initEntities();
                        System.out.println(MainLoop.gameState);
                        System.out.println("Stuff.");
                        
                        initGame();
                    }
                    startWindow.render();
                    System.out.println(MainLoop.gameState);
                    
                    // EVENT HANDLING --- (sort of)
                    if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
                        startWindow.destroyEntities();
                        startWindow = null;
                        MainLoop.gameState = MainLoop.State.GAME;
                        game.startGame();
                    }
                    break;
                case GAME:
                    game.render();
                    game.input();
                    game.logic(getDelta());
                    break;
                case GAME_OVER:

                    if(gameOver == null){
                        gameOver = new GameOver();
                        gameOver.initEntities();
                    }
                    gameOver.render();
                    if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
                        gameOver.destroyEntities();
                        gameOver = null;
                        MainLoop.gameState = MainLoop.State.GAME;
                        initGame();
                    }
                    break;
            }
            Display.update();
            Display.sync(fps);
        }
        AudioManager.destroyALSounds();
        Display.destroy();
        System.exit(0);
    }
    
    private void initOpenGL(){
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }
    
    private void initGame(){
        game = new Game();
        game.initEntities();
        setTimer();
    }
    
    private void setTimer() {
        lastFrame = getTime();
    }
    
    private long getTime(){
        return Sys.getTime()*1000/Sys.getTimerResolution();
    }
    
    private int getDelta() {
        long currentTime = getTime();
        int delta = (int) (currentTime - lastFrame);
        setTimer();
        return delta;
    }
    
    private void initDisplay() {
        try {
            Display.setDisplayMode(new DisplayMode(xRez, yRez));
            Display.setTitle("Sketch Invaders");
            Display.create();
        } catch (LWJGLException ex) {
            ex.printStackTrace();
        }
    }

}
