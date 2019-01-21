/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import entities.AbstractEntity;
import org.newdawn.slick.opengl.Texture;

import textures.TextureManager;

import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Louis
 */
public class GameOver {
    private GameOverTexture got;
    public GameOver(){}
    
    public void initEntities() {
        got = new GameOverTexture(144, 44, 512, 512);
    }
    
    public void render() {
        got.draw();
    }
    
    public void destroyEntities() {
        got = null;
    }
    
    private static class GameOverTexture extends AbstractEntity{
        Texture tex;
        public GameOverTexture(double x, double y, double width, double height){
            super(x, y, width, height);
             tex = TextureManager.loadTexture("game-over");
        }
        
        @Override
        public void draw() {
            tex.bind();
            glBegin(GL_QUADS);
                glTexCoord2f(0, 0);
                glVertex2f((float)x, (float)y); // Upper Left;
                glTexCoord2f(1, 0);
                glVertex2f((float)(x + width), (float)y); // Upper Right;
                glTexCoord2f(1, 1);
                glVertex2f((float)(x + width), (float)(y + height)); // Bottom Right;
                glTexCoord2f(0, 1);
                glVertex2f((float)x, (float)(y + height)); // Bottom Left;
            glEnd();
        }

        @Override
        public void update(int delta) {
            
        }
        
    }
}
