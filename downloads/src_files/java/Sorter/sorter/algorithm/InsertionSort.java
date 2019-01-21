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
public class InsertionSort extends Algorithm {
    private int sizeOfSortedPortion;
    private int pointer;


    public InsertionSort(UserWindow userwindow){
        super(userwindow);
        
        stats = new Statistics("Insertion");
        looper = new Timer(TIME_INTERVAL, this);
        
        sizeOfSortedPortion = 2;
        pointer = sizeOfSortedPortion - 1;
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
        toneMaker.play(Panels.numbersPanel[pointer - 1].size(), Panels.panelsLength, TIME_INTERVAL);
        
        stats.compMade(); // <-- Statictics
        if (Panels.numbersPanel[pointer].size() < Panels.numbersPanel[(pointer - 1)].size()) {

            Panels.numbersPanel[pointer].setColour(SorterColor.GREEN);
            Panels.numbersPanel[pointer - 1].setColour(SorterColor.LIGHT_BLUE);

            Panels.swapPanelsAt(pointer, pointer-1);
            stats.swapMade(); // <-- Statictics
            
            Panels.numbersPanel[pointer - 1].setColour(SorterColor.GREEN);
            Panels.numbersPanel[pointer].setColour(SorterColor.LIGHT_BLUE);
            Panels.updatePanelArray();

            pointer--;
        } else {
            Panels.numbersPanel[pointer].setColour(SorterColor.LIGHT_BLUE);
            sizeOfSortedPortion++;
            pointer = sizeOfSortedPortion - 1;
        }

        if (pointer <= 0) {
            Panels.numbersPanel[pointer].setColour(SorterColor.LIGHT_BLUE);
            sizeOfSortedPortion++;
            pointer = sizeOfSortedPortion - 1;
        }

        if (sizeOfSortedPortion > Panels.panelsLength) {
            
            this.stop();
            userWindow.updatePanelArrayToBlue();
        }
    }
}
