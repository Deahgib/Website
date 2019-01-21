package battleships.engine;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 *
 * @author Louis Bennette
 */
public class GameGlobals {
    // Read from file on load up. 
    // When optons load, perfrom a read to variables local to the options view
    // when apply button is clickedin options view write to the variables in this class.
    // update the file that options read from on load up to save settings.
    
    
    public static final String VERSION = "Alpha 7";
    
    public static final boolean DEBUG_MODE = false;
    public static String serverURL;
    public static int SERVER_PORT;
    
    public static boolean gameMusicOn = true;
    public static boolean gameEffectSoundsOn = true;
    
    private static String onlineUserName;

    public static void initGlobals() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("config/server.txt"));
            serverURL = br.readLine();
            SERVER_PORT = Integer.parseInt(br.readLine());
            br.close();
            
            br = new BufferedReader(new FileReader("config/username.txt"));
            onlineUserName = br.readLine();
            br.close();
            
            br = new BufferedReader(new FileReader("config/sound.txt"));
            String s = br.readLine();
            if(s.equals("T")){
                gameMusicOn = true;
            }
            else{
                gameMusicOn = false;
            }
            s = br.readLine();
            if(s.equals("T")){
                gameEffectSoundsOn = true;
            }
            else{
                gameEffectSoundsOn = false;
            }
            br.close();
        } catch (Exception e) {}
        
        GameLogger.print("Using: " + serverURL + ":" + SERVER_PORT);
        GameLogger.print("Username: " + onlineUserName);
    }
    
    public static String getOnlineUserName(){
        return onlineUserName;
    }

    public static void setOnlineUserName(String name) {
        if (name.length() >= 3 && name.length() <= 16){
            GameGlobals.onlineUserName = name;
            try {
                PrintWriter writer = new PrintWriter("config/username.txt", "UTF-8");
                writer.write(name);
                writer.close();
            } catch (Exception e) {
            }
        }
    }
    public static void setServerAddress(String url) {
        GameGlobals.serverURL = url;
        try {
            PrintWriter writer = new PrintWriter("config/server.txt", "UTF-8");
            writer.print(url + "\r\n");
            writer.print(SERVER_PORT);
            writer.close();
        } catch (Exception e) {
        }
    }
    public static void setSoundSettings(boolean music, boolean effects){
        GameGlobals.gameMusicOn = music;
        GameGlobals.gameEffectSoundsOn = effects;
        try {
            PrintWriter writer = new PrintWriter("config/sound.txt", "UTF-8");
            
            if(music){
                writer.print("T" + "\r\n");
            }else{
                writer.print("F" + "\r\n");
            }
            if(effects){
                writer.print("T" + "\r\n");
            }else{
                writer.print("F" + "\r\n");
            }
            
            writer.close();
        } catch (Exception e) {
        }
    }
}