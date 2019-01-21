package battleships.engine;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

/**
 *
 * @author Louis Bennette
 */
public class Window {
    public static void create(){
        // Future methods to work out the appropriate 
        
        try {
            //Display.setDisplayMode(new DisplayMode(750, 421));
            Display.setDisplayMode(Display.getDesktopDisplayMode());
            Display.setTitle("Battleships");
            Display.setFullscreen(true);
            Display.create();
            GameLogger.print(new String("Created a window with size " + Display.getWidth() + " : " + Display.getHeight()));
        } catch (LWJGLException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }
    
    public static void destroy(){
        Display.destroy();
        GameLogger.print("Closed main window.");
    }
    
    public static int getHeight(){
        return Display.getHeight();
    }
    
    public static int getWidth(){
        return Display.getWidth();
    }
    
    public static boolean isCloseRequested(){
        return Display.isCloseRequested();
    }
    
    public static void update(){
        Display.update();
    }
    
    public static void sync() {
        Display.sync(60);
    }
}