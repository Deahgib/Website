/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import org.newdawn.slick.opengl.Texture;
import textures.TextureManager;

import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Louis
 */
    public class Player extends AbstractMoveableEntity {
        Texture tex;
        public Player(double x, double y, double width, double height) {
            super(x, y, width, height);

            tex = TextureManager.loadTexture("player");
            
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
        
    }
