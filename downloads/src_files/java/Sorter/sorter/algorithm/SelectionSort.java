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

/**
 *
 * @author Deahgib
 */
public class SelectionSort extends Algorithm {
    private int indexOfBase = 0;
    private int pointer = indexOfBase + 1;
    private int indexOfSmallest = indexOfBase;

    
    public SelectionSort(UserWindow userwindow) {
        super(userwindow);
        
        stats = new Statistics("Selection");
        looper = new Timer(TIME_INTERVAL, this);
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
        toneMaker.play(Panels.numbersPanel[pointer].size(), Panels.panelsLength, TIME_INTERVAL);
        
        stats.compMade(); // <-- Statictics
        if (Panels.numbersPanel[indexOfSmallest].size() > Panels.numbersPanel[pointer].size()) {
            Panels.numbersPanel[indexOfSmallest].setColour(SorterColor.RED);

            indexOfSmallest = pointer;

            Panels.numbersPanel[indexOfSmallest].setColour(SorterColor.DARK_GREEN);
        }
        
        if(pointer!=indexOfSmallest){
        Panels.numbersPanel[pointer].setColour(SorterColor.RED);
        }
        pointer++;
        
        
        
        stats.compMade(); // <-- Statictics
        if (pointer >= Panels.panelsLength) {
            stats.compMade(); // <-- Statictics
            if (indexOfSmallest != indexOfBase) {
                Panels.swapPanelsAt(indexOfSmallest, indexOfBase);
                stats.swapMade(); // <-- Statictics
                
                Panels.numbersPanel[indexOfSmallest].setColour(SorterColor.RED);
                Panels.updatePanelArray();
            }
            Panels.numbersPanel[indexOfBase].setColour(SorterColor.LIGHT_BLUE);
            indexOfBase++;
            pointer = indexOfBase + 1;
            indexOfSmallest = indexOfBase;
            stats.compMade(); // <-- Statictics
            if (indexOfBase >= Panels.panelsLength - 1) {
                Panels.numbersPanel[indexOfBase].setColour(SorterColor.LIGHT_BLUE);
                this.stop();
                userWindow.updatePanelArrayToBlue();
            }
        }
        if(animationUnderway){
            Panels.numbersPanel[pointer].setColour(SorterColor.GREEN);
        }
    }
}
