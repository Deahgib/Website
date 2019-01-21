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
public class CocktailSort extends Algorithm {
    private int currentMin;
    private int x;
    private int currentMax;
    private boolean acending;
    private boolean noSwapsMade;
    
    public CocktailSort(UserWindow userwindow){
        super(userwindow);
        
        stats = new Statistics("Cocktail");
        looper = new Timer(TIME_INTERVAL, this);
        
        currentMin = 0;
        x = 0;
        currentMax = Panels.panelsLength - 1;
        acending = true;
        noSwapsMade = true;
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
        
        toneMaker.play(Panels.numbersPanel[x+1].size(), Panels.panelsLength, TIME_INTERVAL);
        
        if (acending) {
            stats.compMade(); // <-- Statictics
            if (Panels.numbersPanel[x].size() > Panels.numbersPanel[x + 1].size()) {
                Panels.swapPanelsAt(x, x+1);
                stats.swapMade(); // <-- Statictics
                //noSwapsMade = false;

                Panels.updatePanelArray();
            }
            Panels.numbersPanel[x].setColour(SorterColor.RED);
            Panels.numbersPanel[x + 1].setColour(SorterColor.GREEN);

            x++;

            stats.compMade(); // <-- Statictics
            if (x >= currentMax) {
                Panels.numbersPanel[x].setColour(SorterColor.LIGHT_BLUE);
                currentMax--;
                x = currentMax;
                acending = !acending;

                //statsComps++; // <-- Statictics
                //if(noSwapsMade){
                //    timer.stop();
                //    changeArrayToColour(LIGHT_BLUE);
                //    sleep(50);
                //    updatePanelArrayToBlue();
                //}else
                //{
                //    noSwapsMade = true;
                //}

                stats.compMade(); // <-- Statictics
                if (currentMax <= currentMin) {
                    this.stop();
                    userWindow.updatePanelArrayToBlue();

                }
            }
        } else {
            stats.compMade(); // <-- Statictics
            if (Panels.numbersPanel[x].size() < Panels.numbersPanel[x - 1].size()) {
                Panels.swapPanelsAt(x, x - 1);
                stats.swapMade(); // <-- Statictics
                //noSwapsMade = false;

                Panels.updatePanelArray();
            }
            Panels.numbersPanel[x].setColour(SorterColor.RED);
            Panels.numbersPanel[x - 1].setColour(SorterColor.GREEN);

            x--;
            stats.compMade(); // <-- Statictics
            if (x <= currentMin) {
                Panels.numbersPanel[x].setColour(SorterColor.LIGHT_BLUE);
                currentMin++;
                x = currentMin;
                acending = !acending;
                //// Attempt to 
                //statsComps++; // <-- Statictics
                //if(noSwapsMade){
                //    timer.stop();
                //    changeArrayToColour(LIGHT_BLUE);
                //    sleep(50);
                //    updatePanelArrayToBlue();
                //}else
                //{
                //    noSwapsMade = true;
                //}
                stats.compMade(); // <-- Statictics
                if (currentMax <= currentMin) {
                    this.stop();
                    userWindow.updatePanelArrayToBlue();
                }
            }
        }
    }
}
