/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sorter.core;

import javax.swing.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Louis
 */
public class GeneralManager {
    
    public static boolean useOrderedList;
    
    private static Timer timer;
    public static Timer getTimer() {
        return timer;
    }
    public static void setTimer(Timer aTimer) {
        timer = aTimer;
    }
    
    
    public static void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            System.err.println();
        }
    }
}
