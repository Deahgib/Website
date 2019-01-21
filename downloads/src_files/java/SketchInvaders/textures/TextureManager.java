/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package textures;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import spaceinvaders.Game;

/**
 *
 * @author Louis
 */
public class TextureManager {
    
    public static Texture loadTexture(String texName){
        String path = "assets/Textures/";
        String extention = ".png";
        try {
                Texture tex = TextureLoader.getTexture("PNG", new FileInputStream(new File(path + texName + extention)));
                return tex;
            } catch (IOException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        return null;
    }
    
}
