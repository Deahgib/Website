/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package score;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

/**
 *
 * @author Louis
 */
public class ScoreKeeper {
    
    private static UnicodeFont scoreText;
    private static UnicodeFont multiplierText;
    
    private static int score = 0;
    private static int scoreMultiplier = 1;
    private static int scoreCombo = 1;
    
    public static void bulletHit(){
        score = score + (scoreMultiplier*10);
        scoreCombo++;
        if(scoreCombo>2&&scoreCombo<=5){
            scoreMultiplier =2;
        }
        else if(scoreCombo>5&&scoreCombo<=9){
            scoreMultiplier =3;
        }
        else if(scoreCombo>10&&scoreCombo<19){
            scoreMultiplier =4;
        }
        else if(scoreCombo>20){
            scoreMultiplier =5;
        }
    }
    
    public static void bulletMissed(){
        scoreCombo = 1;
        scoreMultiplier = 1;
    }
    
    public static int getScore(){
        return score;
    }
    
    public static int getMultiplier(){
        return scoreMultiplier;
    }
    
    public static void resetScore()
    {
        score = 0;
        scoreMultiplier = 1;
        scoreCombo = 1;
    }
    
    public static void initFonts(){
        java.awt.Font awtFont = new java.awt.Font("Comic Sans", java.awt.Font.PLAIN, 18);
        scoreText = new UnicodeFont(awtFont);
        scoreText.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
        scoreText.addAsciiGlyphs();
        try{
            scoreText.loadGlyphs();
        } catch (SlickException ex){
            ex.printStackTrace();
        }
        multiplierText = new UnicodeFont(awtFont);
        multiplierText.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
        multiplierText.addAsciiGlyphs();
        try{
            multiplierText.loadGlyphs();
        } catch (SlickException ex){
            ex.printStackTrace();
        }
    }
    
    public static void draw(){
        scoreText.drawString(10, 10, "SCORE: " + score);
        multiplierText.drawString(10, 30, "X" + scoreMultiplier);
    }
}
