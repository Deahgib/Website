package battleships.engine;

import java.awt.Font;
import java.io.InputStream;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

/**
 *
 * @author Louis Bennette
 * We only need one font and we only need it to be in memory when we call it.
 */
public class FontManager {
    
    private static final String FONT_PATH = "/assets/font/";
    private static final String G_FONT_NAME = "BattleshipsFont.ttf";
    private static final String T_FONT_NAME = "TitleFont.ttf";
    
    /*
     * Singleton class with it's own manager.
     */
    private static FontManager instance;
    
    /**
     * Return the main general font of the game. This is a singleton font.
     * The font is only created on the first call to the font using lazy 
     * instantiation. 
     * 
     * @return the general all purpose font of the game. 
     */
    public static Font getGeneralFont(){
        if(instance == null){
            instance = new FontManager();
        }
        return instance.gFont;
    }
    
    /**
     * This returns the game's title font. This is a singleton font.
     * @return the game's title font.
     */
    public static Font getTitleFont(){
        if(instance == null){
            instance = new FontManager();
        }
        return instance.tFont;
    }
    
    
    // General
    private Font gFont;
    // Title
    private Font tFont;
    /**
     * Private instance used as singleton class so it only instantiated once.
     */
    private FontManager(){
        try {
            InputStream inputStream = ResourceLoader.getResourceAsStream(FONT_PATH + G_FONT_NAME);
            this.gFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            
            inputStream = ResourceLoader.getResourceAsStream(FONT_PATH + T_FONT_NAME);
            this.tFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            
            GameLogger.print("Initialised game fonts.");
        } catch (Exception e) {
            GameLogger.warning("Fonts failed to initialise. Potentialy fatal.");
            e.printStackTrace();
        }
    }
    
    /**
     * This method is called to make a TrueTypeFont with specific size and Style
     * @param constructFont The font face desired
     * @param fontSize The font size desired
     * @param fontStyle The font style Font.PLAIN or Font. BOLD for example.
     * @return a newly allocated TrueTypeFont object with the given 
     * characteristics
     */
    public static TrueTypeFont makeTTFont(Font constructFont, float fontSize, int fontStyle){
        constructFont = constructFont.deriveFont(fontSize);
        constructFont = constructFont.deriveFont(fontStyle);
        return new TrueTypeFont(constructFont, true);
    }
}
