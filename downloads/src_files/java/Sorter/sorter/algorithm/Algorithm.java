/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sorter.algorithm;

import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.Timer;
import sorter.UserWindow;
import sorter.core.Statistics;
import sorter.sound.ToneMaker;


/**
 *
 * @author Deahgib
 */
public abstract class Algorithm implements ActionListener {
    protected Statistics stats; 
    protected Timer looper;
    protected final int TIME_INTERVAL = 20;
    protected boolean animationUnderway = false;
    protected ToneMaker toneMaker;
    protected UserWindow userWindow;
    
    protected Algorithm(UserWindow userwindow){
        this.userWindow = userwindow;
        try {
            toneMaker = new ToneMaker();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(BubbleSort.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean isAnimationUnderway(){
        return animationUnderway;
    }
    
    public abstract void start();
    public abstract void stop();
    
    public final Statistics getStats() {
        return stats;
    }
}

