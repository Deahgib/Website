package battleships.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 *
 * @author Louis Bennette
 */
public class TextureManager {
    private static ArrayList<String> loadedTextures = new ArrayList<String>();
    private static Map<String, Texture> textureHolder = new Hashtable();
    private static final String path =  "assets/textures/";
    private static final String extention = ".png";
    
    /**
     * Returns the texture with name given by the variable texName, returns 
     * a pointer to the texture clones are not made.
     * @param texName the unique identifier of the texture e.g. “button.png” is 
     * identified by the string “button”
     * @return Texture pointer used to draw the screen
     */
    public static Texture loadTexture(String texName){
        try {
            Texture tex;
            if(isLoaded(texName)){
                // If texture is already in memory then return the pointer to it
                tex = textureHolder.get(texName);
            }
            else{
                // Load the new texture.
                tex = TextureLoader.getTexture("PNG", new FileInputStream(new File(path + texName + extention)));
                textureHolder.put(texName, tex);
                loadedTextures.add(texName);
            }
            return tex;
            
        } catch (IOException ex) {
            GameLogger.warning("TextureManager ERROR, texture " + texName + extention + " failed to load.");
            return TextureManager.getDefaultTexture();
        }
    }

    /**
     * This is the default texture that is returned if a texture is not found.
     * If this texture is not found either the game will log a severe error and 
     * crash.
     */
    private static Texture defaultTexture;
    /**
     * If the original texture is not found this method act as a fail safe so 
     * that the game does not crash immediately.
     * @return the pointer to the default texture.
     */
    private static Texture getDefaultTexture(){
        if(TextureManager.defaultTexture == null){
            try {
                TextureManager.defaultTexture = TextureLoader.getTexture("PNG", new FileInputStream(new File("assets/textures/default.png")));
                GameLogger.warning("Loaded default texture.");
            } catch (IOException ex) {
                Logger.getLogger(TextureManager.class.getName()).log(Level.SEVERE, null, ex);
                GameLogger.severe("Could not find/load texture or default texture.\nI is possible that the \"" + path + "\" path is incorrect.\n"+ex.toString());
                ex.printStackTrace();
                System.exit(1);
            }
        }
        return TextureManager.defaultTexture;
    }

    /**
     * flushTextures is called to purge the current textures in memory, The 
     * pointers are removed and the JVM will eventually remove the textures from
     * memory.
     * Note this does not affect the default texture.
     */
    public static void flushTextures(){
        loadedTextures = new ArrayList<String>();
        textureHolder = new Hashtable();
    }
    
    /**
     * Debug method used to return the names of the currently loaded textures.
     * @return the names of the currently loaded textures as a string.
     */
    public static String getListOfLoadedTextures(){
        String list = "";
        for(String item : loadedTextures){
            list = list + item + "\n";
        }
        return list;
    }
    
    /**
     * Checks if the texture is currently loaded in memory. 
     * @param texName the name of the texture we are looking for
     * @return true if the texture is in memory and false if it is not.
     */
    private static boolean isLoaded(String texName){
        for(String key : loadedTextures){
            if(key.equals(texName)){
                return true;
            }
        }
        return false;
    }
}
