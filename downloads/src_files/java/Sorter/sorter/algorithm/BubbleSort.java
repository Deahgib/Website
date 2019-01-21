/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sorter.algorithm;

import java.awt.event.ActionEvent;
import javax.swing.Timer;
import sorter.UserWindow;
import sorter.color.SorterColor;
import sorter.core.Statistics;
import sorter.panels.Panels;
import sorter.sound.Note;

/**
 *
 * @author Louis
 */
public class BubbleSort extends Algorithm {
    private int inc;
    private int sortDomain;
    
    public BubbleSort(UserWindow userwindow){
        super(userwindow);
        
        stats = new Statistics("Bubble");
        looper = new Timer(TIME_INTERVAL, this);
        
        inc = 0;
        sortDomain = Panels.panelsLength - 1;
    }
    
    @Override
    public void start() {
        toneMaker.startSoundLine();
        animationUnderway = true;
        looper.start();
    }
    
    @Override
    public void stop() {
        toneMaker.stopSoundLine();
        animationUnderway = false;
        looper.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        toneMaker.play(Panels.numbersPanel[inc+1].size(), Panels.panelsLength, TIME_INTERVAL);
        
        stats.compMade(); // <-- Statictics
        if (Panels.numbersPanel[inc].size() > Panels.numbersPanel[inc + 1].size()) {
            Panels.swapPanelsAt(inc, inc+1);
            stats.swapMade(); // <-- Statictics
        }
        Panels.numbersPanel[inc].setColour(SorterColor.RED);
        Panels.numbersPanel[inc + 1].setColour(SorterColor.GREEN);

        inc++;
        stats.compMade(); // <-- Statictics
        if (inc >= sortDomain) {
            Panels.numbersPanel[inc].setColour(SorterColor.LIGHT_BLUE);
            inc = 0;
            sortDomain--;
            stats.compMade(); // <-- Statictics
            if (sortDomain < 1) {
                this.stop();
                userWindow.updatePanelArrayToBlue();
            }
        }
        Panels.updatePanelArray();
    }
}
