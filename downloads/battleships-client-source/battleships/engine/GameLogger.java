package battleships.engine;

import battleships.BattleshipsMain;
import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Louis Bennette
 */
public class GameLogger {
    
    private static GameLogger loggingHandler;
    private FileHandler fileLocation;
    private Logger logger;
    private GameLogger(){
        try {
            fileLocation = new FileHandler("debug/lastSession.log");
            fileLocation.setFormatter(new SimpleFormatter());
            logger = Logger.getGlobal();
            logger.addHandler(fileLocation);
            logger.setUseParentHandlers(GameGlobals.DEBUG_MODE);
            
        } catch (IOException ex) {
            Logger.getLogger(GameLogger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(GameLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Logger getInstanceLogger(){
        return this.logger;
    }
    
    private static Logger getLogger(){
        if(loggingHandler == null){
            loggingHandler = new GameLogger();
        }
        return loggingHandler.getInstanceLogger();
    }
    
    public static void print(String message){
        getLogger().info(message);
    }
    
    public static void warning(String message){
        getLogger().warning(message);
    }
    
    public static void severe(String message){
        getLogger().severe(message);
    }
}
