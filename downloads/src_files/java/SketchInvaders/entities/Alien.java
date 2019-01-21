/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import audio.AudioManager;
import org.newdawn.slick.opengl.Texture;
import spaceinvaders.MainLoop;
import textures.TextureManager;

import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Louis
 */
    public class Alien extends AbstractMoveableEntity {
        Texture tex1, tex2;
        public static double allAlienDX, allAlienDY, speedModifier, levelSpeed;
        
        public Alien(double x, double y, double width, double height, String alienType) {
            super(x, y, width, height);
            tex1 = TextureManager.loadTexture(alienType + "-pos1");
            tex2 = TextureManager.loadTexture(alienType + "-pos2");
        }

        public static void reset() {
           speedModifier = 1;
           levelSpeed = 1;
        }
        
        public static void incrementLevelSpeed() {
            Alien.levelSpeed += 0.2;
        }

        public static void incrementSpeed() {
            if(Alien.speedModifier < 2*Alien.levelSpeed){
                Alien.speedModifier *= 1.05;
            }
            System.out.println(Alien.speedModifier);
        }

        public static void setAllAlienDX(double allAlienDX) {
            Alien.allAlienDX = allAlienDX;
        }

        public static void setAllAlienDY(double allAlienDY) {
            Alien.allAlienDY = allAlienDY;
        }

        public static double getAllAlienDX() {
            return Alien.allAlienDX;
        }

        public static double getAllAlienDY() {
            return Alien.allAlienDY;
        }

        @Override
        public void update(int delta) {
            //System.out.println("Alien Update Called");
            if(MainLoop.State.GAME == MainLoop.gameState){
                //System.out.println("Alien Update Successfull");
                this.x += delta * Alien.allAlienDX * Alien.speedModifier * Alien.levelSpeed;
                this.y += delta * Alien.allAlienDY;
            }
        }
        
        public static boolean soundPlayed;
        private static void playBAHBOH(int BAHBOH){
            if(!soundPlayed){
            AudioManager.play(BAHBOH);
                soundPlayed = true;
            }
        }
        
        private int fpsCounter = 0;
        private boolean textChanged =false;
        @Override
        public void draw() {
            int comp = (int)(0.3 /(Alien.allAlienDX * Alien.speedModifier * Alien.levelSpeed));
            if(comp<0)comp *= -1;
            if(fpsCounter > comp){
                tex1.bind();
                
                if (fpsCounter > (comp*2)){
                    fpsCounter = 0;
                    playBAHBOH(AudioManager.BOH);
                }
            }
            else{
                tex2.bind();
                if (fpsCounter > comp-1 ){
                    playBAHBOH(AudioManager.BAH);
                }
            }
            
            glBegin(GL_QUADS);
            glTexCoord2f(0, 0);
            glVertex2f((float) x, (float) y); // Upper Left;
            glTexCoord2f(1, 0);
            glVertex2f((float) (x + width), (float) y); // Upper Right;
            glTexCoord2f(1, 1);
            glVertex2f((float) (x + width), (float) (y + height)); // Bottom Right;
            glTexCoord2f(0, 1);
            glVertex2f((float) x, (float) (y + height)); // Bottom Left;
            glEnd();
            
            fpsCounter++;
        }
    }