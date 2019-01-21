/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import audio.AudioManager;
import entities.Alien;
import entities.Bullet;
import entities.Player;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import score.ScoreKeeper;


/**
 *
 * @author Louis
 */
public class Game {
    private static UnicodeFont currentLevelText;
    

    private Player player;
    private final int ARRAY_X_SIZE = 8;
    private final int ARRAY_Y_SIZE = 5;
    private Alien[][] alien;
    private Bullet bullet;
    
    private int currentLevel = 1;
    
    private boolean bulletActive;
    private boolean mothershipActive;
    
    public Game(){}

    public void initEntities() {
        ScoreKeeper.resetScore();
        
        alien = new Alien[ARRAY_X_SIZE][ARRAY_Y_SIZE];
        bulletActive = false;
        mothershipActive = false;
        player = new Player(Display.getWidth() / 20, Display.getHeight() - (Display.getHeight() / 20 * 2), 64, 32);
        for (int i = 0; i < ARRAY_Y_SIZE; i++) {
            for (int j = 0; j < ARRAY_X_SIZE; j++) {
                alien[j][i] = null;
                String type;
                if (i < 1) {
                    type = "alien-big";
                } else if (i >= 1 && i < 3) {
                    type = "alien-normal";
                } else {
                    type = "alien-small";
                }
                alien[j][i] = new Alien(Display.getWidth() / 10 + 80 * j, Display.getHeight() - (Display.getHeight() - 64 * (i + 1)), 32, 32, type);
            }
        }
    }

    private void destroyEntities(){
        //System.out.println("Entities Destroyed");
        for (int i = 0; i < ARRAY_Y_SIZE; i++) {
            for (int j = 0; j < ARRAY_X_SIZE; j++) {
                if (alien[j][i] != null) {
                    alien[j][i].setY(10);
                    alien[j][i] = null;
                    //System.out.println("Removed Alien: at "+(j+1)+", "+(i+1));
                }
            }
        }
        alien = null;
        player = null;
        bullet = null;
    }
    
    public static void initFonts(){
        java.awt.Font awtFont = new java.awt.Font("Comic Sans", java.awt.Font.PLAIN, 18);
        currentLevelText = new UnicodeFont(awtFont);
        currentLevelText.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
        currentLevelText.addAsciiGlyphs();
        try{
            currentLevelText.loadGlyphs();
        } catch (SlickException ex){
            ex.printStackTrace();
        }
    }
    
    public void startGame(){
        Alien.allAlienDX = 0.01;
        Alien.levelSpeed = 1;
        Alien.speedModifier = 1;
    }
    
    public void render() {
        currentLevelText.drawString(Display.getWidth()-70, 10, "Level "+currentLevel);
        ScoreKeeper.draw();
        
        // Update the player.
        player.draw();

        if (bulletActive) {
            bullet.draw();
        }
        if (bulletActive) {
            bullet.draw();
        }
        for (int i = 0; i < ARRAY_Y_SIZE; i++) {
            for (int j = 0; j < ARRAY_X_SIZE; j++) {
                if (alien[j][i] != null) {
                    alien[j][i].draw();
                }
            }
        }
        Alien.soundPlayed = false;
        
    }
    
    public void logic(int delta){
        player.update(delta);
        if(bulletActive){
            bullet.update(delta);
            if(bullet.getY() < 0){
                bullet = null;
                bulletActive = false;
                ScoreKeeper.bulletMissed();
            }
        }
        for (int i = 0; i < ARRAY_Y_SIZE; i++) {
            for (int j = 0; j < ARRAY_X_SIZE; j++) {
                if (alien[j][i] != null) {
                    alien[j][i].update(delta);
                }
            }
        }
        for (int i = 0; i < ARRAY_Y_SIZE; i++) {
            for (int j = 0; j < ARRAY_X_SIZE; j++) {
                if (alien[j][i] != null) {

                    if (alien[j][i].getX() <= (Display.getWidth() / 20)) {
                        Alien.setAllAlienDX(0.01);
                        Alien.incrementSpeed();
                        lowerAllAliens();
                    } else if (alien[j][i].getX() + alien[j][i].getWidth() > (Display.getWidth() / 20 * 19)) {
                        Alien.setAllAlienDX(-0.01);
                        Alien.incrementSpeed();
                        lowerAllAliens();

                    }
                    if(alien[j][i].getY() + alien[j][i].getHeight() >= 500){
                        gameOver();
                        return;
                    }
                    
                    if (bulletActive) {
                        if (alien[j][i].intersects(bullet)) {
                            ScoreKeeper.bulletHit();
                            AudioManager.play(AudioManager.DEATH);
                            alien[j][i] = null;
                            //System.out.println("Removed Alien: at "+(j+1)+", "+(i+1));
                            bullet = null;
                            bulletActive = false;
                            if(isAlienArrayEmpty()){
                                currentLevel++;
                                AudioManager.play(AudioManager.WIN);
                                resetAliens();
                                Alien.incrementLevelSpeed();
                            }
                        }
                    }
                }
            }
        }  
    }
    
    private void gameOver() {
        //System.out.println("------ GAME OVER --------");
        destroyEntities();
        Alien.reset();
        AudioManager.play(AudioManager.LOOSE);
        MainLoop.gameState = MainLoop.State.GAME_OVER;
    }
    
    private void lowerAllAliens() {
        for (int i = 0; i < ARRAY_Y_SIZE; i++) {
            for (int j = 0; j < ARRAY_X_SIZE; j++) {
                if (alien[j][i] != null) {
                    alien[j][i].setY(alien[j][i].getY()+3);
                }
            }
        }
    }
    
    private void resetAliens(){
        for(int i = 0; i < ARRAY_Y_SIZE; i++){
            for(int j = 0; j < ARRAY_X_SIZE; j++){
                String type;
                if(i<1){
                    type = "alien-big";
                }else if(i >= 1 && i < 3){
                    type = "alien-normal";
                }
                else{
                    type = "alien-small";
                }
                alien[j][i] = new Alien(Display.getWidth()/10 + 80*j, Display.getHeight()-(Display.getHeight()-64*(i+1)), 32, 32, type);
            }
        }
        Alien.speedModifier = 1;
        Alien.allAlienDX = 0.01;
    }
    
    public boolean isAlienArrayEmpty(){
        for(int i = 0; i < ARRAY_Y_SIZE; i++){
            for (int j = 0; j < ARRAY_X_SIZE; j++) {
                if (alien[j][i] != null) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public void input() {
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            if (player.getX() + player.getWidth() < Display.getWidth()) {
                player.setDX(0.5);
            } else {
                player.setDX(0);
            }
        } else if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            if (player.getX() > 0) {
                player.setDX(-0.5);
            } else {
                player.setDX(0);
            }
        } else {
            player.setDX(0);
        }
        if(!bulletActive){
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
                AudioManager.play(AudioManager.PEW);
                bulletActive = true;
                bullet = new Bullet(player.getX()+(player.getWidth()/2)-4, player.getY()-16, 8, 16);
                bullet.setDY(-0.5);
            }
        }
    }
}