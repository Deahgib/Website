package battleships.engine;


import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;
/**
 *
 * @author Louis Bennette
 */
public class OpenGLManager {
    public static void initOpenGL(){
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, Window.getWidth(), Window.getHeight(), 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GameLogger.print("OpenGL initialised and ready.");
    }
    
    public static void clearBuffers(){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
    public static void destroyOpenGl(){
        
    }
    
    public static void drawLine(Point startPoint, Point endPoint) {
    glBegin(GL_LINE_STRIP);
        glVertex2f((float)startPoint.getX(), (float)startPoint.getY());
        glVertex2f((float)endPoint.getX(), (float)endPoint.getY());
    glEnd();
}
    
    public static void drawSquare(float posX, float posY, float width, float height, Color color){
        glColor3f(color.r, color.g, color.b);
        glBegin(GL_QUADS);
            glVertex2f(posX, posY); // Upper Left;
            glVertex2f(posX + width, posY); // Upper Right;
            glVertex2f(posX + width, posY + height); // Bottom Right;
            glVertex2f(posX, posY + height); // Bottom Left;
        glEnd();
        glColor3f(0,0,0);
    }
    
    public static void drawSquareWithTex(float posX, float posY, float width, float height, Texture tex){
        tex.bind();
        glEnable(GL_BLEND);
        glBegin(GL_QUADS);
            glTexCoord2f(0, 0);
            glVertex2f(posX, posY); // Upper Left;
            glTexCoord2f(1, 0);
            glVertex2f(posX + width, posY); // Upper Right;
            glTexCoord2f(1, 1);
            glVertex2f(posX + width, posY + height); // Bottom Right;
            glTexCoord2f(0, 1);
            glVertex2f(posX, posY + height); // Bottom Left;
        glEnd();
        glDisable(GL_BLEND);
    }
    
    public static void drawSquareWithTexAtRightAngle(float posX, float posY, float width, float height, Texture tex){
        tex.bind();
        glEnable(GL_BLEND);
        glBegin(GL_QUADS);
            glTexCoord2f(0, 1);
            glVertex2f(posX, posY); // Upper Left;
            glTexCoord2f(0, 0);
            glVertex2f(posX + width, posY); // Upper Right;
            glTexCoord2f(1, 0);
            glVertex2f(posX + width, posY + height); // Bottom Right;
            glTexCoord2f(1, 1);
            glVertex2f(posX, posY + height); // Bottom Left;
        glEnd();
        glDisable(GL_BLEND);
    }

    // Development method. I use colours to tell different components apart.
    public static void setColor(int r, int g, int b) {
        glColor3f(r,g,b);
    }
}
