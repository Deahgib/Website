/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import entities.AbstractEntity;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

import textures.TextureManager;

import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Louis
 */
public class IntroTitle {
    
    private IntroTitleTexture itt;
    public IntroTitle(){
    }
    
    public void initEntities() {
        itt = new IntroTitleTexture(144, 44, 512, 512);
    }
    
    public void render() {
        itt.draw();
    }
    
    public void destroyEntities() {
        itt = null;
    }
    
    private static class IntroTitleTexture extends AbstractEntity{
        Texture tex;
        public IntroTitleTexture(double x, double y, double width, double height){
            super(x, y, width, height);
            tex = TextureManager.loadTexture("intro-title");
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
